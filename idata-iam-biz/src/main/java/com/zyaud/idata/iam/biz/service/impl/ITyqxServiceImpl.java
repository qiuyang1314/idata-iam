package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjsasc.drap.auth.IdentityToken;
import com.bjsasc.drap.pt.context.ContextUser;
import com.bjsasc.drap.pt.context.ThreadLocalUtil;
import com.bjsasc.drap.sso.SSOService;
import com.bjsasc.drap.sso.SimpleClientHttpRequestFactory4Https;
import com.zyaud.fzhx.common.enums.ResultStatus;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.common.utils.JsonUtils;
import com.zyaud.idata.iam.api.req.DTO.OnlyTokenReqDto;
import com.zyaud.idata.iam.biz.mapper.*;
import com.zyaud.idata.iam.biz.model.entity.*;
import com.zyaud.idata.iam.biz.model.vo.TyqxOrgRspVO;
import com.zyaud.idata.iam.biz.model.vo.TyqxUserRspVO;
import com.zyaud.idata.iam.biz.service.*;
import com.zyaud.idata.iam.common.errorcode.DictMngErrorEnum;
import com.zyaud.idata.iam.common.errorcode.LoginServiceEnum;
import com.zyaud.idata.iam.common.errorcode.OfficeMngErrorEnum;
import com.zyaud.idata.iam.common.utils.Constants;
import com.zyaud.idata.iam.common.utils.PasswordEncryptor;
import com.zyaud.idata.iam.common.utils.PoolExecutorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.bjsasc.drap.auth.AuthUtils.createContextUser;

/**
 * @program: idata-iam
 * @description: 神软统一权限实现类
 * @author: qiuyang
 * @create: 2022-02-18 11:40
 **/
@Slf4j
@Service
public class ITyqxServiceImpl implements ITyqxService {

    private RestTemplate restTemplate;
    @Value("${tyqx.app_id}")
    private String app_id;
    //获取用户
    @Value("${drap_platform.sso_server.rootpath}/context/ptaa_context/cntContextResourcePrivilege")
    private String cntContextResourcePrivilege_url;
    @Value("${drap_platform.sso_server.rootpath}/dpds/ptaa_user/qryUserByID?user_id={user_id}")
    private String qryUser_url;
    @Value("${drap_platform.sso_server.rootpath}/dpds/ptaa_org/qryOrgByID?org_id={org_id}")
    private String qryOrg_url;
    @Value("${drap_platform.sso_server.rootpath}/dpds/ptaa_org/qryOrgs")
    private String qryOrgs_url;
    @Value("${drap_platform.sso_server.rootpath}/dpds/ptaa_user/qryUsersWithPrincipal")
    private String qryUsersWithPrincipal_url;
    @Value("${drap_platform.parentId}")
    private String parentId;
    @Value("${idata-iam.shenRuanIamInitRole}")
    private String roleCode;
    @Resource
    private IUserCodeService userCodeService;
    @Resource
    private SSOService ssoService;
    @Resource
    private IRoleUserCodeService roleUserCodeService;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private IOfficeService officeService;
    @Resource
    private StdTypeMapper stdTypeMapper;
    @Resource
    private StdCodeMapper stdCodeMapper;
    @Resource
    private IStdCodeService stdCodeService;
    @Resource
    private IUserService userService;
    @Resource
    private RoleUserCodeMapper roleUserCodeMapper;
    @Resource
    private UserCodeMapper userCodeMapper;

    @PostConstruct
    public void init(){
        SimpleClientHttpRequestFactory4Https requestFactory = new SimpleClientHttpRequestFactory4Https();
        requestFactory.setReadTimeout(120 * 1000);
        this.restTemplate = new RestTemplate(requestFactory);
    }

    @Override
    public ContextUser getUserInfo(String sso_code) {
        Object[] token = null;
        ContextUser user;
        log.info("----------SSO_CODE--------------"+sso_code);
        //获取token
        if (ObjectUtil.isNotEmpty(sso_code)) {
            token = ssoService.getIdentityTokenByCode(sso_code, app_id);
            log.info("-------------token0------------"+token[0]);
            log.info("-------------token1------------"+token[1]);
        }
        if (token == null) {
            BizAssert.fail(ResultStatus.ILLEGALA_ARGUMENT_EX.getCode(),"sso_code or sso_token is required.");
        }
        //组装请求参数
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(LoginServiceEnum.SSOTOKEN.getName(), (String) token[0]);
        Map<String, String> data = new HashMap<>();
        data.put(LoginServiceEnum.RESOURCE_TYPE.getName(), LoginServiceEnum.PT_APP.getName());
        data.put(LoginServiceEnum.PRIVILEGE_CODE.getName(), LoginServiceEnum.ACCESS.getName());
        data.put(LoginServiceEnum.RESOURCE_ID.getName(), app_id);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(data, requestHeaders);
        //获取用户
        List rest_result = restTemplate.postForObject(cntContextResourcePrivilege_url, requestEntity, List.class);
        BizAssert.isNotEmpty(rest_result,"用户信息为空!");
        for (int i = 0; i < rest_result.size(); i++) {
            log.info("-------------rest_result---------------"+rest_result.get(i));
        }
        user = createContextUser((String) token[0], (IdentityToken) token[1]);
        ThreadLocalUtil.setContextUser(user);
        BizAssert.isNotEmpty(user,"用户信息为空!");
        log.info("-------------userId-----------"+user.getUserID());
        log.info("-------------userLoginName-----------"+user.getUserLoginName());
        log.info("-------------userName-----------"+user.getUserName());
        log.info("------------userToken----------"+user.getToken());
        //addUserRole(user.getUserID(),roleCode);
        registerUser(user,user.getUserLoginName());
        try {
            if (rest_result.size() < 0) {
                BizAssert.fail("The user[" + user.getUserLoginName() + "] does not have access.");
            }
            String total = String.valueOf(((Map) rest_result.get(0)).get(LoginServiceEnum.TOTAL.getName()));
            if (ObjectUtil.isEmpty(total) || Integer.parseInt(total) <= 0) {
                BizAssert.fail("The user[" + user.getUserLoginName() + "] does not have access.");
            }
        } catch (Exception e) {
            BizAssert.fail("The user[" + user.getUserLoginName() + "] does not have access.");
        }
        return user;
    }

    @Override
    public ContextUser registerUser(ContextUser user, String passWord) {
        String token = user.getToken();
        log.info("-------------userId-----------"+user.getUserID());
        log.info("-------------userLoginName-----------"+user.getUserLoginName());
        log.info("-------------userName-----------"+user.getUserName());
        log.info("------------userToken----------"+user.getToken());
        //获取token
        if (ObjectUtil.isEmpty(token) || ObjectUtil.isEmpty(passWord)) {
            BizAssert.fail("sso_token or passWord is required.");
        }
        //获取神软用户信息
        ResponseEntity<String> userEntity = httpExchange(token, LoginServiceEnum.USER_ID.getName(), user.getUserID(), qryUser_url);
        if(ObjectUtil.isEmpty(userEntity.getBody())){
            BizAssert.fail("无相关用户信息。");
        }
        //String js = JsonUtils.toJson(userEntity.getBody(),true);
        List<TyqxUserRspVO> userVos = JsonUtils.toList(userEntity.getBody(), TyqxUserRspVO.class);
        if(CollectionUtil.isEmpty(userVos)){
            BizAssert.fail("无相关用户信息。");
        }
        TyqxUserRspVO userVo = userVos.get(0);
        String orgId = userVo.getOrg_id();
        //机构存在跳过
        if (ObjectUtil.isNotEmpty(orgId)&&ObjectUtil.isEmpty(officeService.getById(orgId))) {
            saveOrgThread(token, LoginServiceEnum.ORG_ID.getName(), orgId, qryOrg_url);
        }
        //账号存在则跳过
        if(ObjectUtil.isEmpty(userCodeService.getById(userVo.getId()))){
            //新增用户
            User iamUser = new User();
            UserCode userCode = new UserCode();
            addIamUser(userVo, passWord,iamUser,userCode,0);
            List<String> logins = new ArrayList<>();
            logins.add(userCode.getLoginName());
            copyJurisdiction(logins);
            userService.save(iamUser);
            userCodeService.save(userCode);
            //新增用户角色
            addUserRole(userVo.getId(),roleCode);
        }
        return user;
    }

    public static void main(String[] args) {
        String jss = "{\"last_updated\":\"2018-10-10T02:10:10.000+0000\",\"key_status_zw\":\"NONE\",\"gender\":\"M\",\"signature\":\"870bcdb97f08f0f2569d843d966d88ce578b0cca4f44ae5671ccd85651ce6ad1\",\"date_created\":\"2018-10-10T02:10:10.000+0000\",\"ca_e\":\"admin@cnao.gov.cn\",\"sort\":1,\"classification\":\"CF20\",\"type\":\"adminRole_admin\",\"domain_id\":\"drapRootDomain\",\"zhiji\":\"zhiji1000\",\"domain_name\":\"\",\"cengci\":\"cengci1100\",\"login_name\":\"administrator\",\"org_id\":\"orgRootDomain\",\"idcard\":\"\",\"name\":\"系统管理员\",\"id\":\"rootadmin0001\",\"org_name\":\"审计署\",\"email\":\"admin@sjs.com\",\"secret_code\":\"10\",\"key_status_fxw\":\"NONE\",\"status\":\"ACTIVE\"}";
        String js = JsonUtils.toJson(jss,true);
        TyqxUserRspVO userVos = JsonUtils.parse(js.replace("\"","'"), TyqxUserRspVO.class);
        System.out.println(userVos);
    }
    @Override
    public String registerUserTotal(OnlyTokenReqDto tokenDto) {
        PoolExecutorUtil.getThreadPool().execute(() -> {
            String token = tokenDto.getToken();
            List<Office> orgList = new ArrayList<>();
            Set<User> userSet = new HashSet<>();
            Set<UserCode> userCodeSet = new HashSet<>();
            Set<String> userIds = new HashSet<>();
            getAllOrg(token,orgList,userSet,userCodeSet, LoginServiceEnum.NULL.getName(),userIds);
            if (CollectionUtil.isEmpty(orgList)) {
                log.error("无机构信息可同步,或token超时");
                System.err.println("-----------------无机构信息可同步,或token超时");
            }
            //过滤机构
            if(CollectionUtil.isNotEmpty(orgList)){
                filterOrg(orgList);
                if(CollectionUtil.isNotEmpty(orgList)){
                    officeService.saveBatch(orgList);
                }

            }
            List<User> userList = new ArrayList<>(userSet);
            //过滤用户
            if(CollectionUtil.isNotEmpty(userSet)){
                filterUser(userList);
                if(CollectionUtil.isNotEmpty(userList)){
                    userService.saveBatch(userList);
                }
            }
            //过滤账号
            if(CollectionUtil.isNotEmpty(userCodeSet)){
                List<UserCode> userCodeList = new ArrayList<>(userCodeSet);
                filterUserCode(userCodeList);
                if(CollectionUtil.isNotEmpty(userCodeList)){
                    userCodeService.saveBatch(userCodeList);
                }
            }
            //将同步的用户全部初始化为数据分析人员角色
            if(CollectionUtil.isNotEmpty(userList)){
                List<String> userCodes = userList.stream().map(t -> t.getId()).collect(Collectors.toList());
                for (String uc: userCodes) {
                    addUserRole(uc,roleCode);
                }
            }
        });
        return "后台用户信息正在同步中。。。。。。";
    }

    public boolean addUserRole(String userId,String roleCode){
        log.info("-------------开始新增用户角色方法----------------");
        //判断用户是否存在角色
        QueryWrapper<RoleUserCode> roleUserCodeQueryWrapper  = new QueryWrapper<>();
        roleUserCodeQueryWrapper.eq(RoleUserCode.USERCODEID,userId);
        long counts = roleUserCodeService.count(roleUserCodeQueryWrapper);
        if(counts<=0){
            log.info("---------当前用户没角色---------");
            //获取配置初始化角色
            List<String> roleCodes = Arrays.asList(roleCode.split(","));
            List<Role> iamRole = roleMapper.getByRoleCodes(roleCodes);
            if(CollectionUtil.isNotEmpty(iamRole)){
                for (Role iRole : iamRole) {
                    RoleUserCode roleUserCode  = new RoleUserCode();
                    roleUserCode.setAppId(iRole.getAppId());
                    roleUserCode.setRoleId(iRole.getId());
                    roleUserCode.setUserCodeId(userId);
                    roleUserCodeService.save(roleUserCode);
                }
            }
        }
        return true;
    }

    public ResponseEntity<String> httpExchange(String token, String params, String paramsValue, String url) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(LoginServiceEnum.SSOTOKEN.getName(), token);
        Map<String, String> dataOrg = new HashMap<>();
        dataOrg.put(params, paramsValue);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class, dataOrg);
    }

    public void saveOrgThread(String token, String param, String orgId, String qryOrg_url) {
        List<Office> officeList = new ArrayList<>();
        recursionQueryOrg(token, officeList, param, orgId, qryOrg_url);
        try {
            if(CollectionUtil.isNotEmpty(officeList)){
                officeService.saveBatch(officeList);
            }
        } catch (Exception e) {
            log.error("同步用户机构失败：" + e);
            e.printStackTrace();
        }
    }

    private void recursionQueryOrg(String token, List<Office> officeList, String param, String orgId, String qryOrg_url) {
        ResponseEntity<String> orgEntity = httpExchange(token, param, orgId, qryOrg_url);
        if (null != orgEntity.getBody() && orgEntity.getBody().startsWith("[")) {
            List<TyqxOrgRspVO> orgVos = JsonUtils.toList(orgEntity.getBody(), TyqxOrgRspVO.class);
            if (ObjectUtil.isNotEmpty(orgVos)) {
                TyqxOrgRspVO org = orgVos.get(0);
                Office office = new Office();
                orgTrans(org, office);
                officeList.add(office);
                if (!StrUtil.equalsIgnoreCase(parentId, office.getParentId())) {
                    recursionQueryOrg(token, officeList, param, office.getParentId(), qryOrg_url);
                }
            }
        }
        //过滤机构
        filterOrg(officeList);
    }

    //过滤机构
    private void filterOrg(List<Office> officeList){
        List<Office> dbOfficeList = officeService.list();
        //机构存在走过滤
        if (CollectionUtil.isNotEmpty(dbOfficeList)&&CollectionUtil.isNotEmpty(officeList)) {
            List<String> collect = dbOfficeList.stream().map(t -> t.getId()).collect(Collectors.toList());
            officeList.stream().filter(t -> !collect.contains(t.getId())).collect(Collectors.toList());
        }
    }

    //过滤用户
    private void filterUser(List<User> userList){
        List<User> iuserList = userService.list();
        //机构存在走过滤
        if (CollectionUtil.isNotEmpty(iuserList)&&CollectionUtil.isNotEmpty(userList)) {
            List<String> collect = iuserList.stream().map(t -> t.getId()).collect(Collectors.toList());
            userList.stream().filter(t -> !collect.contains(t.getId())).collect(Collectors.toList());
        }
    }

    //过滤账号
    private void filterUserCode(List<UserCode> userCodeList){
        List<UserCode> iuserCodeList = userCodeService.list();
        //机构存在走过滤
        if (CollectionUtil.isNotEmpty(iuserCodeList)&&CollectionUtil.isNotEmpty(userCodeList)) {
            List<String> collect = iuserCodeList.stream().map(t -> t.getId()).collect(Collectors.toList());
            userCodeList.stream().filter(t -> !collect.contains(t.getId())).collect(Collectors.toList());
        }
    }

    public void orgTrans(TyqxOrgRspVO org, Office office) {
        office.setCode(org.getCode());
        office.setId(org.getId());
        office.setName(org.getName());
        office.setOtype(org.getType());
        office.setDistrictId("");
        office.setOrderIndex(1);
        office.setLevels("1");
        office.setOrderIndex(org.getSort());
        if (ObjectUtil.isEmpty(office.getParentId())) {
            office.setParentId(Constants.ROOTID)
                    .setPaths(office.getCode() + StrUtil.SLASH);
        } else {
            Office officeParent = officeService.getById(office.getParentId());
            BizAssert.isFalse(ObjectUtil.isEmpty(officeParent), OfficeMngErrorEnum.PARENT_OFFICE_IS_DELETE);
            office.setPaths(officeParent.getPaths() + office.getCode() + StrUtil.SLASH);
        }
        if(ObjectUtil.isEmpty(org.getParent())){
            office.setParentId("-1");
        }else{
            office.setParentId(org.getParent());
        }
        if(ObjectUtil.isNotEmpty(org.getStatus())&&LoginServiceEnum.ACTIVE.getName().equals(org.getStatus())){
            office.setUseable("0");
        }else{
            office.setUseable("1");
        }
    }

    //type:0使用统一权限密码，1登录名做密码
    public void addIamUser(TyqxUserRspVO tyqxUser, String passWord,User user,UserCode userCode,int type) {
        user.setName(tyqxUser.getName())
                .setUserPost(tyqxUser.getPost())
                .setIdCard(tyqxUser.getIdcard())
                .setPhone(tyqxUser.getMobilephone())
                .setSecirityFlag("0")
                .setOfficeId(tyqxUser.getOrg_id())
                .setId(tyqxUser.getId()); // TODO 此处使用userId为Id，再次同步时若删除再添加，则会导致重复主键不能添加
        if(ObjectUtil.isEmpty(tyqxUser.getOrg_id())){
            user.setOfficeId(Constants.NULL_OFFICE);
        }
        //性别
        if (tyqxUser.getGender().equals("M")) {
            user.setSex("0");
        } else if (tyqxUser.getGender().equals("F")||tyqxUser.getGender().equals("W")) {
            user.setSex("1");
        } else {
            user.setSex("");
        }

        Set<String> codeNames = new HashSet<>();
        codeNames.add("神软账号");

        Map<String, String> accountType = getStdCode(codeNames, "accountType");
        //盐值
        String saltValue = RandomUtil.randomNumbers(Constants.USERCODE_RANDOM_NUMBER);
        PasswordEncryptor encoderMd51 = new PasswordEncryptor(saltValue, "sha-256");
        userCode.setUserId(tyqxUser.getId())
                .setLoginName(tyqxUser.getLogin_name())
                .setSalt(saltValue)
                .setType(accountType.get("神软账号"))
                .setId(tyqxUser.getId()); // TODO 此处使用userId为Id，再次同步时若删除再添加，则会导致重复主键不能添加
        if(type == 1){
            userCode.setPasswd(encoderMd51.encode(DigestUtils.md5DigestAsHex(tyqxUser.getLogin_name().getBytes())));
        }else if(ObjectUtil.isNotEmpty(passWord)){
            userCode.setPasswd(encoderMd51.encode(DigestUtils.md5DigestAsHex(passWord.getBytes())));
        }
        //正常
        if (ObjectUtil.isNotEmpty(tyqxUser.getStatus())&&LoginServiceEnum.ACTIVE.getName().equals(tyqxUser.getStatus())) {
            userCode.setStatus("0");
        } else {
            userCode.setStatus("2");
        }
    }

    public Map<String, String> getStdCode(Set<String> codeNames, String stdNum) {
        if (codeNames.size() > 0) {
            StdType stdType = stdTypeMapper.getByStdNum(stdNum);
            if (ObjectUtil.isEmpty(stdType)) {
                BizAssert.fail(DictMngErrorEnum.STD_TYPE_IS_NOT_EXIST_BY_STD_NUM.getCode(),
                        "字典值为:" + stdNum + "的字典不存在!");

            }
            List<StdCode> stdCodes = stdCodeMapper.findStdCodeByStdTypesAndCodeNameAndCodeValues(
                    Arrays.asList(stdNum), null, null);

            //字典值排序降序
            List<StdCode> codeValues = stdCodes.stream().sorted(Comparator.comparing(StdCode::getCodeValue)
                    .reversed()).collect(Collectors.toList());
            //最大字典值
            Integer value = null;
            if (codeValues.size() > 0) {
                StdCode stdCode = codeValues.get(0);
                if (NumberUtil.isInteger(stdCode.getCodeValue())) {
                    value = Integer.parseInt(stdCode.getCodeValue()) + 1;
                }
                //处理字典值非数字情况
                else {
                    BizAssert.fail(DictMngErrorEnum.STD_CODE_VALUE_EXIST_NON_INTEGER);
                }
            } else {
                value = 1;
            }
            //待返回值
            //筛选出存在的字典值
            Map<String, String> map = codeValues.stream()
                    .filter(t -> codeNames.contains(t.getCodeName()))
                    .collect(Collectors.toList())
                    .stream()
                    .collect(Collectors.toMap(t -> t.getCodeName(), t -> t.getCodeValue()));

            //不存在的字段值
            List<String> names = new ArrayList<>();
            for (String codeName : codeNames) {
                int i = 0;
                for (StdCode codeValue : codeValues) {
                    if (!codeName.equals(codeValue.getCodeName())) {
                        i++;
                    } else {
                        break;
                    }
                }
                if (i == codeValues.size()) {
                    names.add(codeName);
                }
            }
            //入库
            if (names.size() > 0) {
                List<StdCode> stdCodeList = new ArrayList<>();
                for (String name : names) {
                    StdCode stdCode = new StdCode();
                    stdCode.setCodeName(name)
                            .setCodeValue(String.valueOf(value++))
                            .setStdType(stdNum)
                            .setOrderIndex(stdCode.getCodeValue())
                            .setUseable("0")
                            .setRemark(name);
                    stdCodeList.add(stdCode);
                    map.put(stdCode.getCodeName(), stdCode.getCodeValue());
                }
                stdCodeService.saveBatch(stdCodeList);
            }
            return map;
        } else {
            return new HashMap<>();
        }
    }

    private void getAllOrg(String token,List<Office> orgList, Set<User> userSet,Set<UserCode> userCodeSet, String parentId,Set<String> userIds) {
        Map<String, String> orgData = new HashMap<>();
        orgData.put(LoginServiceEnum.PARENT.getName(), parentId);
        List<TyqxOrgRspVO> orgJa = getFromPost(token, orgData, qryOrgs_url,TyqxOrgRspVO.class);
        log.info("---------------机构数据："+orgJa);
        if (CollectionUtil.isNotEmpty(orgJa)) {
            orgJa.forEach(o -> {
                Office afaOrg = new Office();
                orgTrans(o, afaOrg);
                orgList.add(afaOrg);
                Map<String, String> userData = new HashMap<>();
                userData.put(LoginServiceEnum.PRINCIPAL_CLASS.getName(), LoginServiceEnum.PT_ORGANIZATION.getName());
                userData.put(LoginServiceEnum.PRINCIPAL_VALUE1.getName(), o.getId());
                List<TyqxUserRspVO> userJa = getFromPost(token, userData, qryUsersWithPrincipal_url,TyqxUserRspVO.class);
                log.info("---------------"+afaOrg.getName()+"--机构的用户数据："+orgJa);
                if (CollectionUtil.isNotEmpty(userJa)) {
                    userJa.forEach(u -> {
                        User user = new User();
                        UserCode userCode = new UserCode();
                        addIamUser(u,null,user, userCode, 1);
                        userSet.add(user);
                        userCodeSet.add(userCode);
                        userIds.add(userCode.getId());
                    });
                }
                getAllOrg(token, orgList, userSet,userCodeSet, o.getId(),userIds);
            });
        }
        log.info("-----------------------目前机构数量："+orgList.size());
        log.info("-----------------------目前用户数量："+userSet.size());
    }

    public <T> List<T> getFromPost(String token, Map<String, String> data, String url,Class T) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(LoginServiceEnum.SSOTOKEN.getName(), token);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity  <>(data, requestHeaders);
        JSONArray jsonArray = restTemplate.postForObject(url, requestEntity, JSONArray.class);
        String js = JsonUtils.toJson(jsonArray);
        return JsonUtils.toList(js, T);
    }

    /**
     * 复制权限
     *
     * @param loginNames
     */
    public void copyJurisdiction(List<String> loginNames) {
        //查询重复账号
        List<UserCode> userCodes = userCodeMapper.findUserCodeListByLoginNames(loginNames);
        //删除账号
        if (userCodes.size() > 0) {
            List<String> ids = userCodes.stream().map(t -> t.getId()).collect(Collectors.toList());
            userCodeService.removeByIds(ids);
            //删除用户
            Set<String> userIds = userCodes.stream().map(t -> t.getUserId()).collect(Collectors.toSet());
            if (userIds.size() > 0) {
                List<User> users = userService.listByIds(userIds);
                if (users.size() > 0) {
                    userService.removeByIds(users.stream().map(t -> t.getId()).collect(Collectors.toList()));
                }
            }
            //获取所关联的权限信息
            List<RoleUserCode> roleUserCodeList = roleUserCodeMapper.listRoleUserCodeByUserCodeIds(ids);
            //删除权限
            roleUserCodeMapper.deleteByUserCodeIds(ids);
            //转换权限
            for (RoleUserCode roleUserCode : roleUserCodeList) {
                Optional<UserCode> first = userCodes.stream().filter(t -> t.getId().equals(roleUserCode.getUserCodeId())).findFirst();
                first.ifPresent(t -> roleUserCode.setUserCodeId(t.getLoginName()));
            }
            //新增权限
            roleUserCodeService.saveBatch(roleUserCodeList);
        }
    }
}
