package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.common.utils.JsonUtils;
import com.zyaud.idata.iam.api.client.IExternalLangCaoClient;
import com.zyaud.idata.iam.api.resp.ExternalAppRespVO;
import com.zyaud.idata.iam.api.resp.ExternalLoginRespVO;
import com.zyaud.idata.iam.api.resp.LangCaoResult;
import com.zyaud.idata.iam.biz.mapper.*;
import com.zyaud.idata.iam.biz.model.entity.*;
import com.zyaud.idata.iam.biz.service.*;
import com.zyaud.idata.iam.common.errorcode.AppMngErrorEnum;
import com.zyaud.idata.iam.common.errorcode.DictMngErrorEnum;
import com.zyaud.idata.iam.common.errorcode.ExternalLangCaoErrorEnum;
import com.zyaud.idata.iam.common.utils.Constants;
import com.zyaud.idata.iam.common.utils.PasswordEncryptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExternalLangServiceImpl implements IExternalLangService {

    @Value("${spring.feign.url.lc:localhost}")
    public String externalLangCaoLogin;
    @Value("${spring.feign.username:TEST}")
    private String username;
    @Value("${spring.feign.password:TEST}")
    private String password;
    @Value("${spring.feign.appCode:XSXC}")
    private String appCode;
    @Autowired
    private IExternalLangCaoClient externalLangCaoClient;
    @Autowired
    private IUserCodeService userCodeService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IOfficeService officeService;
    @Resource
    private StdTypeMapper stdTypeMapper;
    @Autowired
    private IStdCodeService stdCodeService;
    @Resource
    private StdCodeMapper stdCodeMapper;
    @Autowired
    private IAppService appService;
    @Autowired
    private IRoleService roleService;
    @Resource
    private RoleMapper roleMapper;
    @Autowired
    private IRoleUserCodeService roleUserCodeService;
    @Resource
    private RoleUserCodeMapper roleUserCodeMapper;
    @Resource
    private UserCodeMapper userCodeMapper;

    @Override
    public String login() throws IOException {
        URL url = new URL(externalLangCaoLogin+"/common-auth/login" + "?username=" + username + "&password=" + password);
        log.info("请求获取token:----->{}",url.toString());
        HttpURLConnection connection = (HttpURLConnection) url
                .openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.connect();
        //读取响应
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String lines;
        StringBuffer sb = new StringBuffer("");
        while ((lines = reader.readLine()) != null) {
            lines = new String(lines.getBytes(), "utf-8");
            sb.append(lines);
        }
        System.err.println("-" + sb.toString() + "-");
        LangCaoResult<ExternalLoginRespVO> langCaoResult = JsonUtils.parse(sb.toString(), LangCaoResult.class);
        System.err.println(externalLangCaoLogin + "/login?username=" + username + "&password=" + password);
        System.err.println(langCaoResult);
        if (!isSuccess(langCaoResult.getCode())) {
            BizAssert.fail(ExternalLangCaoErrorEnum.LOGIN_LANG_CAO_SYSTEM_ERROR.getCode(),
                    ExternalLangCaoErrorEnum.LOGIN_LANG_CAO_SYSTEM_ERROR.getMessage() + langCaoResult.getMessage());
        }
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        reader.close();
        connection.disconnect();
        if (CollectionUtil.isEmpty(headerFields)){
            BizAssert.fail(ExternalLangCaoErrorEnum.LOGIN_LANG_CAO_SYSTEM_ERROR.getCode(),
                    ExternalLangCaoErrorEnum.LOGIN_LANG_CAO_SYSTEM_ERROR.getMessage() + "获取token失败");
        }
        return headerFields.get("Authorization").get(0);
    }

    @Override
    public List<String> app(String authorization) throws IOException {
        Map<String, Object> app = externalLangCaoClient.app(appCode, authorization);

        log.info("获取浪潮数据集:======>{}",app);
        System.err.println(app);
        LangCaoResult langCaoResult = BeanUtil.toBean(app, LangCaoResult.class);
        if (!isSuccess(langCaoResult.getCode())) {
            BizAssert.fail(ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getCode(),
                    ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getMessage() + langCaoResult.getLogMsg());
        }
        ExternalAppRespVO externalAppRespVO = BeanUtil.toBean(langCaoResult.getData(), ExternalAppRespVO.class);
        // log.info("浪潮机构用户数据=====>{}",externalAppRespVO);
        List<String> userIds = new ArrayList<>();
        try {
            //账号信息
            log.info("浪潮同步用户数量=====>{}个",externalAppRespVO.getStaff().size());
            log.info("浪潮同步数据=====>{}",externalAppRespVO.getStaff());
            userIds = saveUserCode(externalAppRespVO.getStaff());
        } catch (Exception e) {
            e.printStackTrace();
            BizAssert.fail(ExternalLangCaoErrorEnum.SYNCHRONIZE_LANG_CAO_USER_INFO_ERROR.getCode(),
                    ExternalLangCaoErrorEnum.SYNCHRONIZE_LANG_CAO_USER_INFO_ERROR.getMessage() + e.getMessage());
        }
        try {
            //机构信息
            log.info("浪潮同步机构数量=====>{}个",externalAppRespVO.getDepartment().size());
            log.info("浪潮同步机构数据=====>{}",externalAppRespVO.getDepartment());
            saveOffice(externalAppRespVO.getCorporation(), externalAppRespVO.getDepartment());
        } catch (Exception e) {
            e.printStackTrace();
            BizAssert.fail(ExternalLangCaoErrorEnum.SYNCHRONIZE_LANG_CAO_CORPORATION_ERROR.getCode(),
                    ExternalLangCaoErrorEnum.SYNCHRONIZE_LANG_CAO_CORPORATION_ERROR.getMessage() + e.getMessage());
        }
        log.info("浪潮推送的用户ids[{}]",userIds);
        log.info("浪潮推送的用户ids 数量[{}]",userIds.size());
        return userIds;
    }

    @Override
    public void relevanceRole(List<String> userrIds, String appId) {
        if (StrUtil.isEmpty(appId)) {
            BizAssert.fail(AppMngErrorEnum.APP_ID_CANNOT_BE_NULL);
        }
        App app = appService.getById(appId);
        if (ObjectUtil.isEmpty(app)) {
            BizAssert.fail(AppMngErrorEnum.APP_IS_NULL);
        }
        if (!"lczgdczxt".equals(app.getAppCode())) {
            BizAssert.fail(AppMngErrorEnum.APP_CODE_IS_NOT_MATCH);
        }
        //新建角色
        Role role = roleMapper.getByAppIdAndRoleName(appId, "浪潮整改督察子系统角色");
        if (ObjectUtil.isEmpty(role)) {
            role = new Role();
            role.setAppId(appId)
                    .setRoleName("浪潮整改督察子系统角色")
                    .setRoleCode("lczgdczxtjs")
                    .setRoleType("1")
                    .setUseable("0");
            roleService.save(role);
        }
        //删除关联账号
        roleUserCodeMapper.deleteByAppIdsAndRoleIds(Arrays.asList(appId), Arrays.asList(role.getId()));
        //重新关联
        List<RoleUserCode> roleUserCodeList = new ArrayList<>();
        for (String userrId : userrIds) {
            RoleUserCode roleUserCode = new RoleUserCode();
            roleUserCode.setAppId(appId)
                    .setRoleId(role.getId())
                    .setUserCodeId(userrId);
            roleUserCodeList.add(roleUserCode);
        }
        roleUserCodeService.saveBatch(roleUserCodeList);
    }

    /**
     * @param ticket
     * @Description: 通过票据浪潮cas单点登录返回浪潮登录用户
     * @Author: dong
     * @return: java.lang.String
     * @Date: 2022/1/13 15:36
     */
    @Override
    public String casLogin(String ticket) {


        Map<String, Object> userInfo = null;
        try {
            userInfo = externalLangCaoClient.casLogin(ticket);
        } catch (Exception e) {
            log.error("调用浪潮接口失败");
            BizAssert.fail(ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getCode(),
                    ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getMessage() + e.getMessage());
        }

        log.info("通过票据获取浪潮用户信息===>{}",userInfo);

        if (CollectionUtil.isEmpty(userInfo)){
            BizAssert.fail(ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getCode(),
                    ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getMessage() + "调用浪潮接口失败!");
        }

        if (ObjectUtil.isEmpty(userInfo.get("code")) || ((Integer)userInfo.get("code") !=1000)){
            BizAssert.fail(ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getCode(),
                    ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getMessage() + "通过浪潮登录票据获取数据失败!");
        }
        if ( ObjectUtil.isEmpty(userInfo.get("content"))){
            BizAssert.fail(ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getCode(),
                    ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getMessage() + "浪潮登录用户信息为空!");
        }
        JSONObject user = JSONUtil.parseObj(userInfo.get("content"));

        log.info("浪潮登录用户信息=========>{}",user);

        return user.getStr("userId");
    }

    /**
     * 写入账号信息
     *
     * @param staffs
     */
    public List<String> saveUserCode(List<ExternalAppRespVO.Staff> staffs) {
        //List<UserCode> userCodeList = userCodeService.list();
        //账号存在走过滤
        /*if (userCodeList.size() > 0) {
            List<String> collect = userCodeList.stream().map(t -> t.getId()).collect(Collectors.toList());
            staffs = staffs.stream().filter(t -> !collect.contains(t.getUserId())).collect(Collectors.toList());
        }*/
        //if (staffs.size() > 0) {
            List<User> users = new ArrayList<>();
            List<UserCode> userCodes = new ArrayList<>();
            for (ExternalAppRespVO.Staff staff : staffs) {
                User user = new User();
                user.setName(staff.getUserName())
                        .setUserPost(staff.getPost())
                        .setUserLevel(staff.getRank())
                        .setIdCard(staff.getIdCard())
                        .setPhone(staff.getPhoneNumber())
                        .setSecirityFlag("0")
                        .setOfficeId(staff.getDepartmentId())
                        .setId(staff.getUserId()); // TODO 此处使用userId为Id，再次同步时若删除再添加，则会导致重复主键不能添加
                //性别
                if (staff.getGender().equals("1")) {
                    user.setSex("0");
                } else if (staff.getGender().equals("2")) {
                    user.setSex("1");
                } else {
                    user.setSex("");
                }

                Set<String> codeNames = new HashSet<>();
                codeNames.add("浪潮账号");

                Map<String, String> accountType = getStdCode(codeNames, "accountType");

                UserCode userCode = new UserCode();
                //盐值
                String saltValue = RandomUtil.randomNumbers(Constants.USERCODE_RANDOM_NUMBER);
                PasswordEncryptor encoderMd51 = new PasswordEncryptor(saltValue, "sha-256");
                userCode.setUserId(staff.getUserId())
                        .setLoginName(staff.getUserId())
                        .setPasswd(encoderMd51.encode(DigestUtils.md5DigestAsHex("123456".getBytes())))
                        .setSalt(saltValue)
                        .setType(accountType.get("浪潮账号"))
                        .setId(staff.getUserId()); // TODO 此处使用userId为Id，再次同步时若删除再添加，则会导致重复主键不能添加
                //正常
                if (staff.getUserStatus().equals("11")) {
                    userCode.setStatus("0");
                } else if (staff.getUserStatus().equals("12")) {
                    userCode.setStatus("2");
                } else {
                    userCode.setStatus("2");
                }
                users.add(user);
                userCodes.add(userCode);
            }

            List<String> userIds = staffs.stream().map(t -> t.getUserId()).collect(Collectors.toList());
            if (userIds.size() > 0) {
                copyJurisdiction(userIds);
            }

            userService.saveOrUpdateBatch(users,users.size());//.saveBatch(users);
            userCodeService.saveOrUpdateBatch(userCodes,userCodes.size());//.saveBatch(userCodes);
            return userIds;
        /*} else {
            return new ArrayList<>();
        }*/
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

    /**
     * 写入机构信息
     *
     * @param corporations
     * @param departments
     */
    public void saveOffice(List<ExternalAppRespVO.Corporation> corporations, List<ExternalAppRespVO.Department> departments) {
        List<Office> officeList = officeService.list();
        //机构存在走过滤
        if (officeList.size() > 0) {
            List<String> collect = officeList.stream().map(t -> t.getId()).collect(Collectors.toList());
            corporations = corporations.stream().filter(t -> !collect.contains(t.getOrganId())).collect(Collectors.toList());
            departments = departments.stream().filter(t -> !collect.contains(t.getOrganId())).collect(Collectors.toList());
        }
        List<Office> offices = new ArrayList<>();
        //转换机构
        if (corporations.size() > 0) {
            for (ExternalAppRespVO.Corporation corporation : corporations) {
                Office office = new Office();
                office.setName(corporation.getOrganName())
                        .setCode(corporation.getOrganCode())
                        .setOrderIndex(corporation.getSort())
                        .setOtype(corporation.getOrganTypeName())
                        .setId(corporation.getOrganId());
                if (StrUtil.isEmpty(corporation.getParentId())) {
                    office.setParentId("-1");
                } else {
                    office.setParentId(corporation.getParentId());
                }
                if (corporation.getInUse().equals("1")) {
                    office.setUseable("0");
                } else {
                    office.setUseable("1");
                }
                offices.add(office);
            }
        }
        //转换部门
        if (departments.size() > 0) {
            for (ExternalAppRespVO.Department department : departments) {
                Office office = new Office();
                office.setName(department.getOrganName())
                        .setCode(department.getOrganCode())
                        .setParentId(department.getParentId())
                        .setOrderIndex(department.getSort())
                        .setOtype(department.getOrganTypeName())
                        .setId(department.getOrganId());
                if (StrUtil.isEmpty(department.getCorporationId())) {
                    office.setParentId("-1");
                } else {
                    office.setParentId(department.getCorporationId());
                }
                if (department.getInUse().equals("1")) {
                    office.setUseable("0");
                } else {
                    office.setUseable("1");
                }
                offices.add(office);
            }
        }
        if (offices.size() > 0) {
            //字典数据处理
            Set<String> collect = offices.stream().map(t -> t.getOtype()).collect(Collectors.toSet());
            Map<String, String> otypeName = getStdCode(collect, "orgType");
            //写入paths
            offices.forEach(t -> t.setDistrictId("")
                    .setOtype(otypeName.get(t.getOtype()) == null ? "" : otypeName.get(t.getOtype()))
                    .setLevels("")
                    .setPaths(getPaths(offices, t.getCode())));
            officeService.saveBatch(offices);
        }
    }

    /**
     * 返回机构paths
     *
     * @param offices
     * @param code
     * @return
     */
    public String getPaths(List<Office> offices, String code) {
        for (Office office : offices) {
            if (office.getCode().equals(code)) {
                if ("-1".equals(office.getParentId())) {
                    return office.getCode() + "/";
                } else {
                    Office officeParent = offices.stream().filter(t -> office.getParentId().equals(t.getId())).collect(Collectors.toList()).get(0);
                    return getPaths(offices, officeParent.getCode()) + office.getCode() + "/";
                }
            }
        }
        return "";
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

    public boolean isSuccess(Integer code) {
        if (code == 1000) {
            return true;
        } else {
            return false;
        }
    }
}
