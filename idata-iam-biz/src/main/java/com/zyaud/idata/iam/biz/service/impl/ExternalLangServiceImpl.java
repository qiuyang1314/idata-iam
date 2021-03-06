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
        log.info("????????????token:----->{}",url.toString());
        HttpURLConnection connection = (HttpURLConnection) url
                .openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.connect();
        //????????????
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
                    ExternalLangCaoErrorEnum.LOGIN_LANG_CAO_SYSTEM_ERROR.getMessage() + "??????token??????");
        }
        return headerFields.get("Authorization").get(0);
    }

    @Override
    public List<String> app(String authorization) throws IOException {
        Map<String, Object> app = externalLangCaoClient.app(appCode, authorization);

        log.info("?????????????????????:======>{}",app);
        System.err.println(app);
        LangCaoResult langCaoResult = BeanUtil.toBean(app, LangCaoResult.class);
        if (!isSuccess(langCaoResult.getCode())) {
            BizAssert.fail(ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getCode(),
                    ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getMessage() + langCaoResult.getLogMsg());
        }
        ExternalAppRespVO externalAppRespVO = BeanUtil.toBean(langCaoResult.getData(), ExternalAppRespVO.class);
        // log.info("????????????????????????=====>{}",externalAppRespVO);
        List<String> userIds = new ArrayList<>();
        try {
            //????????????
            log.info("????????????????????????=====>{}???",externalAppRespVO.getStaff().size());
            log.info("??????????????????=====>{}",externalAppRespVO.getStaff());
            userIds = saveUserCode(externalAppRespVO.getStaff());
        } catch (Exception e) {
            e.printStackTrace();
            BizAssert.fail(ExternalLangCaoErrorEnum.SYNCHRONIZE_LANG_CAO_USER_INFO_ERROR.getCode(),
                    ExternalLangCaoErrorEnum.SYNCHRONIZE_LANG_CAO_USER_INFO_ERROR.getMessage() + e.getMessage());
        }
        try {
            //????????????
            log.info("????????????????????????=====>{}???",externalAppRespVO.getDepartment().size());
            log.info("????????????????????????=====>{}",externalAppRespVO.getDepartment());
            saveOffice(externalAppRespVO.getCorporation(), externalAppRespVO.getDepartment());
        } catch (Exception e) {
            e.printStackTrace();
            BizAssert.fail(ExternalLangCaoErrorEnum.SYNCHRONIZE_LANG_CAO_CORPORATION_ERROR.getCode(),
                    ExternalLangCaoErrorEnum.SYNCHRONIZE_LANG_CAO_CORPORATION_ERROR.getMessage() + e.getMessage());
        }
        log.info("?????????????????????ids[{}]",userIds);
        log.info("?????????????????????ids ??????[{}]",userIds.size());
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
        //????????????
        Role role = roleMapper.getByAppIdAndRoleName(appId, "?????????????????????????????????");
        if (ObjectUtil.isEmpty(role)) {
            role = new Role();
            role.setAppId(appId)
                    .setRoleName("?????????????????????????????????")
                    .setRoleCode("lczgdczxtjs")
                    .setRoleType("1")
                    .setUseable("0");
            roleService.save(role);
        }
        //??????????????????
        roleUserCodeMapper.deleteByAppIdsAndRoleIds(Arrays.asList(appId), Arrays.asList(role.getId()));
        //????????????
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
     * @Description: ??????????????????cas????????????????????????????????????
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
            log.error("????????????????????????");
            BizAssert.fail(ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getCode(),
                    ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getMessage() + e.getMessage());
        }

        log.info("????????????????????????????????????===>{}",userInfo);

        if (CollectionUtil.isEmpty(userInfo)){
            BizAssert.fail(ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getCode(),
                    ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getMessage() + "????????????????????????!");
        }

        if (ObjectUtil.isEmpty(userInfo.get("code")) || ((Integer)userInfo.get("code") !=1000)){
            BizAssert.fail(ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getCode(),
                    ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getMessage() + "??????????????????????????????????????????!");
        }
        if ( ObjectUtil.isEmpty(userInfo.get("content"))){
            BizAssert.fail(ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getCode(),
                    ExternalLangCaoErrorEnum.GET_LANG_CAO_USER_INFO_ERROR.getMessage() + "??????????????????????????????!");
        }
        JSONObject user = JSONUtil.parseObj(userInfo.get("content"));

        log.info("????????????????????????=========>{}",user);

        return user.getStr("userId");
    }

    /**
     * ??????????????????
     *
     * @param staffs
     */
    public List<String> saveUserCode(List<ExternalAppRespVO.Staff> staffs) {
        //List<UserCode> userCodeList = userCodeService.list();
        //?????????????????????
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
                        .setId(staff.getUserId()); // TODO ????????????userId???Id???????????????????????????????????????????????????????????????????????????
                //??????
                if (staff.getGender().equals("1")) {
                    user.setSex("0");
                } else if (staff.getGender().equals("2")) {
                    user.setSex("1");
                } else {
                    user.setSex("");
                }

                Set<String> codeNames = new HashSet<>();
                codeNames.add("????????????");

                Map<String, String> accountType = getStdCode(codeNames, "accountType");

                UserCode userCode = new UserCode();
                //??????
                String saltValue = RandomUtil.randomNumbers(Constants.USERCODE_RANDOM_NUMBER);
                PasswordEncryptor encoderMd51 = new PasswordEncryptor(saltValue, "sha-256");
                userCode.setUserId(staff.getUserId())
                        .setLoginName(staff.getUserId())
                        .setPasswd(encoderMd51.encode(DigestUtils.md5DigestAsHex("123456".getBytes())))
                        .setSalt(saltValue)
                        .setType(accountType.get("????????????"))
                        .setId(staff.getUserId()); // TODO ????????????userId???Id???????????????????????????????????????????????????????????????????????????
                //??????
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
     * ????????????
     *
     * @param loginNames
     */
    public void copyJurisdiction(List<String> loginNames) {
        //??????????????????
        List<UserCode> userCodes = userCodeMapper.findUserCodeListByLoginNames(loginNames);
        //????????????
        if (userCodes.size() > 0) {
            List<String> ids = userCodes.stream().map(t -> t.getId()).collect(Collectors.toList());
            userCodeService.removeByIds(ids);
            //????????????
            Set<String> userIds = userCodes.stream().map(t -> t.getUserId()).collect(Collectors.toSet());
            if (userIds.size() > 0) {
                List<User> users = userService.listByIds(userIds);
                if (users.size() > 0) {
                    userService.removeByIds(users.stream().map(t -> t.getId()).collect(Collectors.toList()));
                }
            }
            //??????????????????????????????
            List<RoleUserCode> roleUserCodeList = roleUserCodeMapper.listRoleUserCodeByUserCodeIds(ids);
            //????????????
            roleUserCodeMapper.deleteByUserCodeIds(ids);
            //????????????
            for (RoleUserCode roleUserCode : roleUserCodeList) {
                Optional<UserCode> first = userCodes.stream().filter(t -> t.getId().equals(roleUserCode.getUserCodeId())).findFirst();
                first.ifPresent(t -> roleUserCode.setUserCodeId(t.getLoginName()));
            }
            //????????????
            roleUserCodeService.saveBatch(roleUserCodeList);
        }
    }

    /**
     * ??????????????????
     *
     * @param corporations
     * @param departments
     */
    public void saveOffice(List<ExternalAppRespVO.Corporation> corporations, List<ExternalAppRespVO.Department> departments) {
        List<Office> officeList = officeService.list();
        //?????????????????????
        if (officeList.size() > 0) {
            List<String> collect = officeList.stream().map(t -> t.getId()).collect(Collectors.toList());
            corporations = corporations.stream().filter(t -> !collect.contains(t.getOrganId())).collect(Collectors.toList());
            departments = departments.stream().filter(t -> !collect.contains(t.getOrganId())).collect(Collectors.toList());
        }
        List<Office> offices = new ArrayList<>();
        //????????????
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
        //????????????
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
            //??????????????????
            Set<String> collect = offices.stream().map(t -> t.getOtype()).collect(Collectors.toSet());
            Map<String, String> otypeName = getStdCode(collect, "orgType");
            //??????paths
            offices.forEach(t -> t.setDistrictId("")
                    .setOtype(otypeName.get(t.getOtype()) == null ? "" : otypeName.get(t.getOtype()))
                    .setLevels("")
                    .setPaths(getPaths(offices, t.getCode())));
            officeService.saveBatch(offices);
        }
    }

    /**
     * ????????????paths
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
                        "????????????:" + stdNum + "??????????????????!");

            }
            List<StdCode> stdCodes = stdCodeMapper.findStdCodeByStdTypesAndCodeNameAndCodeValues(
                    Arrays.asList(stdNum), null, null);

            //?????????????????????
            List<StdCode> codeValues = stdCodes.stream().sorted(Comparator.comparing(StdCode::getCodeValue)
                    .reversed()).collect(Collectors.toList());
            //???????????????
            Integer value = null;
            if (codeValues.size() > 0) {
                StdCode stdCode = codeValues.get(0);
                if (NumberUtil.isInteger(stdCode.getCodeValue())) {
                    value = Integer.parseInt(stdCode.getCodeValue()) + 1;
                }
                //??????????????????????????????
                else {
                    BizAssert.fail(DictMngErrorEnum.STD_CODE_VALUE_EXIST_NON_INTEGER);
                }
            } else {
                value = 1;
            }
            //????????????
            //???????????????????????????
            Map<String, String> map = codeValues.stream()
                    .filter(t -> codeNames.contains(t.getCodeName()))
                    .collect(Collectors.toList())
                    .stream()
                    .collect(Collectors.toMap(t -> t.getCodeName(), t -> t.getCodeValue()));

            //?????????????????????
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
            //??????
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
