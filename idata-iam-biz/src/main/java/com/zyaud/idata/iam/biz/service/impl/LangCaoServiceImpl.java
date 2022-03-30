package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyaud.fzhx.cache.service.CacheService;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.iam.core.FzhxIamUser;
import com.zyaud.fzhx.iam.token.Token;
import com.zyaud.fzhx.iam.token.TokenStore;
import com.zyaud.fzhx.iam.token.TokenUtils;
import com.zyaud.idata.iam.api.client.IExternalLangCaoClient;
import com.zyaud.idata.iam.api.req.DTO.RemoteLoginReqDTO;
import com.zyaud.idata.iam.api.req.DTO.RemoteOrgRqeDTO;
import com.zyaud.idata.iam.api.req.DTO.RemoteUserRqeDTO;
import com.zyaud.idata.iam.api.req.FindAppsReqVO;
import com.zyaud.idata.iam.api.req.LoginReqVO;
import com.zyaud.idata.iam.api.req.TokenAnalysisReqVO;
import com.zyaud.idata.iam.api.resp.*;
import com.zyaud.idata.iam.biz.mapper.RoleUserCodeMapper;
import com.zyaud.idata.iam.biz.model.entity.*;
import com.zyaud.idata.iam.biz.service.*;
import com.zyaud.idata.iam.biz.strategy.UserLoginStrategy;
import com.zyaud.idata.iam.biz.utils.SpringUtil;
import com.zyaud.idata.iam.common.errorcode.AccountMngErrorEnum;
import com.zyaud.idata.iam.common.errorcode.AppMngErrorEnum;
import com.zyaud.idata.iam.common.errorcode.ExternalLangCaoErrorEnum;
import com.zyaud.idata.iam.common.errorcode.OfficeMngErrorEnum;
import com.zyaud.idata.iam.common.utils.Constants;
import com.zyaud.idata.iam.common.utils.PasswordEncryptor;
import com.zyaud.idata.iam.common.utils.RSAUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LangCaoServiceImpl implements ILangCaoService {
    @Autowired
    private UserLoginStrategy userLoginStrategy;
    @Autowired
    private IAppService appService;
    @Value("${fzhx.iam.token.secret}")
    private String secret;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserCodeService userCodeService;
    @Autowired
    private IOfficeService officeService;
    @Resource
    private RoleUserCodeMapper roleUserCodeMapper;

    @Resource
    private CacheService cacheService;

    @Resource
    private TokenStore tokenStore;

    @Value("${appcode:tyqxxt}")
    private String appCode;

    @Override
    public LoginRespVO login(LoginReqVO requestVO) {
        verification(requestVO, requestVO.getAppId(), requestVO.getSign());
        // TODO userLoginStrategy->LoginCheckPassowordStrategy
        com.zyaud.idata.iam.biz.model.vo.LoginRespVO login = this.userLoginStrategy.login(requestVO.getLoginName(), requestVO.getPasswd(), null);
        LoginRespVO loginRespVO = BeanUtil.toBean(login, LoginRespVO.class);
        loginRespVO.setToken(login.getAccessToken().getValue());
        return loginRespVO;
    }

    @Override
    public TokenAnalysisRespVO tokenAnalysis(TokenAnalysisReqVO requestVO) {
        verification(requestVO, requestVO.getAppId(), requestVO.getSign(), requestVO.getToken());
        Claims claims = TokenUtils.parserJwt(requestVO.getToken(), this.secret);
        TokenAnalysisRespVO resp = new TokenAnalysisRespVO();
        Object exp = claims.get("exp");
        if (ObjectUtil.isNotEmpty(exp)) {
            LocalDateTime expirationTime = LocalDateTime.ofEpochSecond(new Long(exp.toString()), 0, ZoneOffset.ofHours(8));
            resp.setExpirationTime(expirationTime);
        }
        Object sub = claims.get("sub");
        if (ObjectUtil.isNotEmpty(sub)) {
            resp.setLoginName(sub.toString());
        }
        UserCode userCode = userCodeService.getByLoginName(resp.getLoginName());
        if (ObjectUtil.isEmpty(userCode)) {
            BizAssert.fail(AccountMngErrorEnum.USER_CODE_IS_NOT_EXIST);
        }
        if (StrUtil.isNotEmpty(userCode.getUserId())) {
            User user = userService.getById(userCode.getUserId());
            if ("0".equals(user.getSex())) {
                resp.setUserSex("男");
            }
            if ("1".equals(user.getSex())) {
                resp.setUserSex("女");
            }
            if (ObjectUtil.isNotEmpty(user)) {
                resp.setUserName(user.getName());
                if (StrUtil.isNotEmpty(user.getOfficeId())) {
                    Office office = officeService.getById(user.getOfficeId());
                    if (ObjectUtil.isNotEmpty(office)) {
                        resp.setOfficeName(office.getName())
                                .setOfficeCode(office.getCode());
                    }
                }
            }
        }
        return resp;
    }

    @Override
    public List<FindAppsRespVO> findApps(FindAppsReqVO requestVO) {
        List<FindAppsRespVO> findAppsRespVOS = new ArrayList<>();
        //验证
        verification(requestVO, requestVO.getAppId(), requestVO.getSign(), requestVO.getToken());
        Claims claims = TokenUtils.parserJwt(requestVO.getToken(), this.secret);
        Object userId = claims.get("userId");
        if (ObjectUtil.isNotEmpty(userId)) {
            List<RoleUserCode> roleUserCodeList = roleUserCodeMapper.listRoleUserCodeByUserCodeIds(Arrays.asList((String) userId));
            List<String> appIds = roleUserCodeList.stream().map(t -> t.getAppId()).collect(Collectors.toList());
            if (appIds.size() > 0) {
                List<App> apps = appService.listByIds(appIds);
                findAppsRespVOS = apps.stream().map(t -> BeanUtil.toBean(t, FindAppsRespVO.class)).collect(Collectors.toList());
            }
        } else {
            BizAssert.fail(AccountMngErrorEnum.USER_IS_NOT_EXIST);
        }
        return findAppsRespVOS;
    }

    /**
     * @param remoteOrgRqeDTO
     * @Description: 新增机构
     * @Author: dong
     * @return: void
     * @Date: 2022/1/7 15:00
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrg(RemoteOrgRqeDTO remoteOrgRqeDTO) {
        log.info("新增浪潮用户:{}", remoteOrgRqeDTO);

        BizAssert.isTrue(ObjectUtil.isEmpty(officeService.getById(remoteOrgRqeDTO.getOrganId())), "当前机构id已存在");
        Office iamOffice = new Office();
        iamOffice.setName(remoteOrgRqeDTO.getOrganName())
                .setCode(remoteOrgRqeDTO.getOrganCode())
                .setOrderIndex(remoteOrgRqeDTO.getSort())
                .setOtype(remoteOrgRqeDTO.getOrganTypeName())
                .setId(remoteOrgRqeDTO.getOrganId());
        if (StrUtil.isEmpty(remoteOrgRqeDTO.getParentId())) {
            iamOffice.setParentId("-1");
            iamOffice.setPaths(iamOffice.getCode() + StrUtil.SLASH);
        } else {
            Office parentOffice = officeService.getById(remoteOrgRqeDTO.getParentId());
            BizAssert.isTrue(ObjectUtil.isNotEmpty(parentOffice), OfficeMngErrorEnum.PARENT_OFFICE_IS_NULL);
            iamOffice.setParentId(remoteOrgRqeDTO.getParentId());
            iamOffice.setPaths(parentOffice.getPaths() + iamOffice.getCode() + StrUtil.SLASH);
        }
        if (StrUtil.isEmpty(remoteOrgRqeDTO.getOrganType())) {
            iamOffice.setOtype("0");
        } else {
            iamOffice.setOtype(remoteOrgRqeDTO.getOrganType());
        }
        iamOffice.setLevels("0");
        iamOffice.setDistrictId("370000");//山东省区划   //todo 暂时写死
        //启用
        iamOffice.setUseable(remoteOrgRqeDTO.getInUse());

        officeService.save(iamOffice);
        log.info("新增浪潮用户完成");

    }

    /**
     * @param remoteOrgRqeDTO
     * @Description: 删除机构
     * @Author: dong
     * @return: void
     * @Date: 2022/1/7 15:00
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrg(RemoteOrgRqeDTO remoteOrgRqeDTO) {
        officeService.removeById(remoteOrgRqeDTO.getOrganId());
    }

    /**
     * @param remoteOrgRqeDTO
     * @Description: 修改机构
     * @Author: dong
     * @return: void
     * @Date: 2022/1/7 15:00
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrg(RemoteOrgRqeDTO remoteOrgRqeDTO) {
        Office iamOffice = new Office();
        iamOffice.setName(remoteOrgRqeDTO.getOrganName())
                .setCode(remoteOrgRqeDTO.getOrganCode())
                .setOrderIndex(remoteOrgRqeDTO.getSort())
                .setOtype(remoteOrgRqeDTO.getOrganTypeName())
                .setId(remoteOrgRqeDTO.getOrganId());
        if (StrUtil.isEmpty(remoteOrgRqeDTO.getOrganType())) {
            iamOffice.setOtype("0");
        } else {
            iamOffice.setOtype(remoteOrgRqeDTO.getOrganType());
        }
        iamOffice.setUseable(remoteOrgRqeDTO.getInUse());
        officeService.updateById(iamOffice);
    }

    /**
     * @param remoteUserRqeDTO
     * @Description: 新增用户
     * @Author: dong
     * @return: void
     * @Date: 2022/1/7 16:16
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(RemoteUserRqeDTO remoteUserRqeDTO) {
        BizAssert.isTrue(ObjectUtil.isEmpty(userCodeService.getById(remoteUserRqeDTO.getUserId())),"当前用户id已存在");
        UserCode userCode = userCodeService.getByLoginName(remoteUserRqeDTO.getLoginName());
        BizAssert.isTrue(ObjectUtil.isEmpty(userCode),"当前登录名已存在已存在");

        User user = new User();
        user.setName(remoteUserRqeDTO.getUserName())
                .setUserPost(remoteUserRqeDTO.getPost())
                .setUserLevel(remoteUserRqeDTO.getRank())
                .setIdCard(remoteUserRqeDTO.getIdCard())
                .setPhone(remoteUserRqeDTO.getPhoneNumber())
                .setSecirityFlag("0")
                .setOfficeId(remoteUserRqeDTO.getCorporationId())
                .setId(remoteUserRqeDTO.getUserId());
                 user.setSex(remoteUserRqeDTO.getSex());
         userCode = new UserCode();
        //盐值
        String saltValue = RandomUtil.randomNumbers(Constants.USERCODE_RANDOM_NUMBER);
        PasswordEncryptor encoderMd51 = new PasswordEncryptor(saltValue, "sha-256");
        userCode.setUserId(remoteUserRqeDTO.getUserId())
                .setLoginName(remoteUserRqeDTO.getLoginName())
                .setPasswd(encoderMd51.encode(DigestUtils.md5DigestAsHex("123456".getBytes())))//默认密码123456
                .setSalt(saltValue)
                .setType("")
                .setId(remoteUserRqeDTO.getUserId());
               userCode.setStatus(remoteUserRqeDTO.getUserStatus());
        userService.save(user);
        userCodeService.save(userCode);
    }

    /**
     * @param remoteUserRqeDTO
     * @Description: 删除用户
     * @Author: dong
     * @return: void
     * @Date: 2022/1/7 16:16
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(RemoteUserRqeDTO remoteUserRqeDTO) {
        userService.removeById(remoteUserRqeDTO.getUserId());
        userCodeService.removeById(remoteUserRqeDTO.getUserId());
    }

    /**
     * @param remoteUserRqeDTO
     * @Description: 更新用户
     * @Author: dong
     * @return: void
     * @Date: 2022/1/7 16:16
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(RemoteUserRqeDTO remoteUserRqeDTO) {

        if (StrUtil.isNotEmpty(remoteUserRqeDTO.getLoginName())){
            UserCode userCode = userCodeService.getByLoginName(remoteUserRqeDTO.getLoginName());
            BizAssert.isTrue(ObjectUtil.isEmpty(userCode),"当前登录名已存在已存在");
        }
        User user = new User();
        user.setName(remoteUserRqeDTO.getUserName())
                .setUserPost(remoteUserRqeDTO.getPost())
                .setUserLevel(remoteUserRqeDTO.getRank())
                .setIdCard(remoteUserRqeDTO.getIdCard())
                .setPhone(remoteUserRqeDTO.getPhoneNumber())
                .setSecirityFlag("0")
                .setOfficeId(remoteUserRqeDTO.getCorporationId())
                .setId(remoteUserRqeDTO.getUserId());
                user.setSex(remoteUserRqeDTO.getSex());


        UserCode userCode = new UserCode();
        userCode.setLoginName(remoteUserRqeDTO.getLoginName())
                .setId(remoteUserRqeDTO.getUserId());
          userCode.setStatus(remoteUserRqeDTO.getUserStatus());
        userService.updateById(user);
        userCodeService.updateById(userCode);
    }

    /**
     * @param remoteLoginReqDTO
     * @Description: 登录iam
     * @Author: dong
     * @return: com.zyaud.idata.iam.api.resp.RemoteLoginRspVO
     * @Date: 2022/1/8 16:13
     */
    @Override
    public RemoteLoginRspVO loginIam(RemoteLoginReqDTO remoteLoginReqDTO) {

        RemoteLoginRspVO loginRspVO = new RemoteLoginRspVO();
        UserCode userCode = userCodeService.getUserCodeByUserId(remoteLoginReqDTO.getUserId());
        //不存在
        BizAssert.isNotEmpty(userCode, "账号或密码错误！");
        String loginName = userCode.getLoginName();

        BizAssert.isTrue(!userCode.getStatus().equals(Constants.USERCODE_LOCK), "账号已锁定，请联系管理员");

        BizAssert.isTrue(!userCode.getStatus().equals(Constants.USERCODE_STOP), "账号已停用");

        //盐值校验
        PasswordEncryptor encoderMd51 = new PasswordEncryptor(userCode.getSalt(), "sha-256");

        //添加浪潮用户角色
        try {
            App appByAppCode = appService.getAppByAppCode(appCode);
            addUserRole(remoteLoginReqDTO.getUserId(),appByAppCode.getId());
          } catch (Exception e) {
           log.error("同步浪潮角色失败",e);
        }


        FzhxIamUser fzhxIamUser = userCodeService.getFzhxIamUserByLoginName(userCode);
        cacheService.setExpire(Constants.IAM_LOGIN,
                Constants.USER_KEY + loginName, fzhxIamUser, Constants.ACCESS_TOKEN_EXPIRE);

        //清空权限信息避免生成token过长
        FzhxIamUser tokenIamUser = BeanUtil.toBean(fzhxIamUser, FzhxIamUser.class);
        tokenIamUser.setScopes(null).setRoles(null).setPermissions(null);
        //access_token
        Token accessToken = getToken(tokenIamUser, Constants.ACCESS_TOKEN_EXPIRE);

        //refresh_token
        Token refreshToken = getToken(tokenIamUser, Constants.REFRESH_TOKEN_EXPIRE);

        tokenStore.storeToken(accessToken);
        tokenStore.storeToken(refreshToken);

        if (ObjectUtil.isNotEmpty(userCode.getUserId())) {
            User user = userService.getById(userCode.getUserId());
            if (ObjectUtil.isNotEmpty(user)) {
                loginRspVO.setUserName(user.getName());
                //机构
                if (StrUtil.isNotEmpty(user.getOfficeId())) {
                    Office office = officeService.getById(user.getOfficeId());
                    if (ObjectUtil.isNotEmpty(office)) {
                        loginRspVO.setDomainName(office.getName());
                    }
                }
            }
        }
        Map<String, Object> ext = fzhxIamUser.getExt();
        Object tno = ext.get("tno");
        loginRspVO.setLoginName(loginName)
                .setAccessToken(accessToken.getValue())
                .setRefreshToken(refreshToken.getValue())
                .setPassword(encoderMd51.isPasswordValid(userCode.getPasswd(), Constants.DEFAULT_PASSWORD))
                .setExpirationTime(DateUtil.formatDateTime(accessToken.getExpireTime()))
                .setUserId(userCode.getId())
                .setExt(ext)
                .setTno(ObjectUtil.isNotEmpty(tno) ? tno.toString() : "")
                .setSuccess(true);

        return loginRspVO;
    }
 /**
 * @Description: 同步浪潮角色数据
 * @Author: dong
 * @param userId 用户id
* @param appId      appid
 * @return: void
 * @Date: 2022/1/18 14:56
 */
    private void addUserRole(String  userId ,String appId) throws Exception {
        new Thread(new Runnable() {

            @Override
            public void run() {
                IExternalLangCaoClient iExternalLangCaoClient = null;
                IRoleUserCodeService  roleUserCodeService = null;
                try {
                    iExternalLangCaoClient = SpringUtil.getBean(IExternalLangCaoClient.class);
                    roleUserCodeService = SpringUtil.getBean(RoleUserCodeServiceImpl.class);
                } catch (Exception e) {
                    log.error("获取服务bean失败",e);
                    BizAssert.fail(ExternalLangCaoErrorEnum.SYNCHRONIZE_LANG_CAO_ROLE_ERROR.getCode(),
                            ExternalLangCaoErrorEnum.SYNCHRONIZE_LANG_CAO_ROLE_ERROR.getMessage() + "获取服务bean失败");
                }
                Map<String, Object> roleinfo =null;
                try {
                    roleinfo = iExternalLangCaoClient.getRoleSbyUserId(userId);
                } catch (Exception e) {
                    log.error("调用浪潮角色失败",e);
                    BizAssert.fail(ExternalLangCaoErrorEnum.SYNCHRONIZE_LANG_CAO_ROLE_ERROR.getCode(),
                            ExternalLangCaoErrorEnum.SYNCHRONIZE_LANG_CAO_ROLE_ERROR.getMessage() + "调用浪潮角色失败");
                }
                log.info("获取浪潮角色用户id:{}",userId);
                log.info("浪潮用户角色信息:{}",roleinfo);
                if (CollectionUtil.isNotEmpty(roleinfo)){
                    //先删除用户机构关联关系
                    QueryWrapper<RoleUserCode> roleUserCodeQueryWrapper  = new QueryWrapper<>();
                    roleUserCodeQueryWrapper.eq(RoleUserCode.USERCODEID,userId);
                    roleUserCodeService.remove(roleUserCodeQueryWrapper);

                    if (ObjectUtil.isNotEmpty(roleinfo.get("code")) || ((Integer)roleinfo.get("code") ==1000)) {
                        List<LangCaoRole> roles = Convert.toList(LangCaoRole.class, roleinfo.get("data"));
                        if (CollectionUtil.isNotEmpty(roles)){
                            List<RoleUserCode> roleUserCodeList = new ArrayList<>();
                            for (LangCaoRole role : roles) {
                                RoleUserCode  roleUserCode  = new RoleUserCode();
                                roleUserCode.setAppId(appId);
                                roleUserCode.setRoleId(role.getRoleId());
                                roleUserCode.setUserCodeId(userId);
                                roleUserCodeList.add(roleUserCode) ;
                            }
                            roleUserCodeService.saveBatch(roleUserCodeList);
                        }
                    }
                }

            }
        }).start();
    }

    private <T> void verification(T t, String appId, String sign) {
        if (ObjectUtil.isEmpty(sign)) {
            BizAssert.fail(AppMngErrorEnum.SIGN_CANNOT_BE_NULL);
        }
        if (ObjectUtil.isEmpty(appId)) {
            BizAssert.fail(AppMngErrorEnum.APP_ID_CANNOT_BE_NULL);
        }

        App app = this.appService.getById(appId);
        if (ObjectUtil.isEmpty(app)) {
            BizAssert.fail(AppMngErrorEnum.APP_IS_NULL.getCode(),"获取应用信息失败");
        }

        String publicKeyString = app.getAppKey();
        String privateKeyString = app.getAppSecret();
        if (ObjectUtil.isEmpty(privateKeyString)) {
            BizAssert.fail(AppMngErrorEnum.PRIVATE_KEY_IS_NOT_EXIST);
        }

        if (ObjectUtil.isEmpty(publicKeyString)) {
            BizAssert.fail(AppMngErrorEnum.PUBLIC_KEY_IS_NOT_EXIST);
        }

        Map<String, Object> map = BeanUtil.beanToMap(t);
        Iterator iter = map.keySet().iterator();

        String key;
        while (iter.hasNext()) {
            key = (String) iter.next();
            if ("sign".equals(key)) {
                iter.remove();
            }

            if ("token".equals(key)) {
                iter.remove();
            }
        }

        key = RSAUtils.sign(map, privateKeyString);
        System.err.println(key);
        if (!RSAUtils.verify(map, publicKeyString, sign)) {
            BizAssert.fail(AppMngErrorEnum.VERIFY_SIGN_FAIL);
        }

    }

    private <T> void verification(T t, String appId, String sign, String token) {
        if (ObjectUtil.isEmpty(token)) {
            BizAssert.fail(AppMngErrorEnum.TOKEN_CANNOT_BE_NULL);
        }
        //token校验
        TokenUtils.isTokenExpired(token, secret);
        this.verification(t, appId, sign);
    }


    public Token getToken(FzhxIamUser fzhxIamUser, Long expire) {
        return TokenUtils.buildToken(fzhxIamUser.getName(), BeanUtil.beanToMap(fzhxIamUser), expire);
    }
}
