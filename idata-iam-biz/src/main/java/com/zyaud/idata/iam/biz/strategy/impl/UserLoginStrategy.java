package com.zyaud.idata.iam.biz.strategy.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.zyaud.fzhx.cache.service.CacheService;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.common.utils.JsonUtils;
import com.zyaud.fzhx.iam.core.FzhxIamUser;
import com.zyaud.fzhx.iam.token.Token;
import com.zyaud.fzhx.iam.token.TokenStore;
import com.zyaud.fzhx.iam.token.TokenUtils;
import com.zyaud.idata.iam.biz.model.entity.*;
import com.zyaud.idata.iam.biz.model.vo.LoginRespVO;
import com.zyaud.idata.iam.biz.model.vo.SysLogMsgVO;
import com.zyaud.idata.iam.biz.service.*;
import com.zyaud.idata.iam.biz.strategy.LoginStrategy;
import com.zyaud.idata.iam.common.utils.Constants;
import com.zyaud.idata.iam.common.utils.PasswordEncryptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 免验证登录
 */
@Component
public class UserLoginStrategy implements com.zyaud.idata.iam.biz.strategy.UserLoginStrategy {
    @Resource
    private CacheService cacheService;
    @Resource
    private IUserCodeService userCodeService;
    @Resource
    private IRoleUserCodeService roleUserCodeService;
    @Resource
    private IRoleMenuService roleMenuService;
    @Resource
    private IMenuService menuService;
    @Resource
    private TokenStore tokenStore;
    @Resource
    private IUserService userService;
    @Resource
    private IOfficeService officeService;
    @Resource
    private IConfigService configService;
    @Resource
    private IAsyncService asyncService;

    @Override
    public LoginRespVO login(String loginName, String passwd, String returnURL) {
        if (ObjectUtil.isEmpty(loginName) || ObjectUtil.isEmpty(passwd)) {
            BizAssert.fail("请填写账号或密码信息！");
        }
        UserCode userCode = userCodeService.getByLoginName(loginName);
        //不存在
        if (ObjectUtil.isEmpty(userCode)) {
            BizAssert.fail("账号或密码错误！");
        }
        if (Constants.USERCODE_LOCK.equals(userCode.getStatus())) {
            BizAssert.fail("账号已锁定，请联系管理员");
        }
        if (Constants.USERCODE_STOP.equals(userCode.getStatus())) {
            BizAssert.fail("账号已停用");
        }
        //盐值校验
        PasswordEncryptor encoderMd51 = new PasswordEncryptor(userCode.getSalt(), "sha-256");
        //密码错误
        if (!encoderMd51.isPasswordValid(userCode.getPasswd(), passwd)) {
            //登录错误3次验证
            pwsErr(userCode);
            BizAssert.fail("账号或密码错误！");
        }
        //在3次内登录成功删除之前的记录
        //TODO 放到验证3次
        Integer loginNums = cacheService.get(Constants.IAM_LOGIN, Constants.LOGINERR + userCode.getId());
        if (ObjectUtil.isNotEmpty(loginNums)) {
            cacheService.del(Constants.LOGINERR + userCode.getId());
        }

        FzhxIamUser fzhxIamUser = userCodeService.getFzhxIamUserByLoginName(userCode.getLoginName());

        //生成tno
        //TODO DateUtil.current();
        String tno = String.valueOf(DateUtil.current());
        fzhxIamUser.getExt().put("tno", tno);
        FzhxIamUser tokenUser = BeanUtil.toBean(fzhxIamUser, FzhxIamUser.class);

        //清空权限信息避免生成token过长
        tokenUser.setScopes(null).setRoles(null).setPermissions(null);

        //access_token
        Token accessToken = getToken(tokenUser, Constants.ACCESS_TOKEN_EXPIRE);

        //refresh_token
        Token refreshToken = getToken(tokenUser, Constants.REFRESH_TOKEN_EXPIRE);
        tokenStore.storeToken(accessToken);
        tokenStore.storeToken(refreshToken);

        LoginRespVO loginRespVO = new LoginRespVO();
        loginRespVO.setLoginName(loginName);
        if (ObjectUtil.isNotEmpty(userCode.getUserId())) {
            User user = userService.getById(userCode.getUserId());
            if (ObjectUtil.isNotEmpty(user)) {
                loginRespVO.setUserName(user.getName());
                loginRespVO.setLoginName(user.getName());
                //机构
                if (StrUtil.isNotEmpty(user.getOfficeId())) {
                    Office office = officeService.getById(user.getOfficeId());
                    if (ObjectUtil.isNotEmpty(office)) {
                        loginRespVO.setDomainName(office.getName());
                    }
                }
            }
        }
        loginRespVO
//                .setAccessToken()
                .setExpirationTime(DateUtil.formatDateTime(accessToken.getExpireTime()))
                .setUserId(userCode.getId())
                .setTno(tno)
                .setReturnURL(returnURL)
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken);
        return loginRespVO;
    }

    @Override
    public LoginRespVO oneTerminalLogin(String loginName, String passwd, String returnURL) {
        LoginRespVO login = login(loginName, passwd, returnURL);
        //验证单终端登录并处理
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = ServletUtil.getClientIP(request);
        asyncService.verifyOneTerminal(loginName, ip, login.getAccessToken(), login.getRefreshToken());
        return login;
    }

    @Override
    public Token getToken(FzhxIamUser fzhxIamUser, Long expire) {
        return TokenUtils.buildToken(fzhxIamUser.getName(), BeanUtil.beanToMap(fzhxIamUser), expire);
    }

    @Override
    public void verifyCaptcha(String key, String captcha) {
        // 图形验证码校验
        // TODO 异常信息调整
        if (StringUtils.isEmpty(captcha)) {
            throw new RuntimeException("图形验证码不能为空！");
        }
        String text = cacheService.get(Constants.IAM_LOGIN, Constants.CAPTCHA_SESSION_KEY + key);
        cacheService.del(Constants.CAPTCHA_SESSION_KEY + key);
        // TODO 使用错误码方式
        if (!captcha.equalsIgnoreCase(text)) {
            BizAssert.fail("图形验证码错误");
        }
    }

    //登录三次校验
    //TODO 依赖密码是否正确验证
    public void pwsErr(UserCode userCode) {
        //推送日志
        pushSyslog(userCode.getLoginName(), userCode.getId());
        if (ObjectUtil.isEmpty(cacheService.get(Constants.IAM_LOGIN, Constants.LOGINERR + userCode.getId()))) {
            cacheService.set(Constants.IAM_LOGIN, Constants.LOGINERR + userCode.getId(), 1);
        } else {
            Integer i = cacheService.get(Constants.IAM_LOGIN, Constants.LOGINERR + userCode.getId());
            if (i >= 3) {
                // TODO  常量
                userCode.setStatus("1");
                userCodeService.updateById(userCode);
                cacheService.del(Constants.LOGINERR + userCode.getId());
                BizAssert.fail("账号或密码错误超过3次，账号已锁定");
            } else {
                cacheService.set(Constants.IAM_LOGIN, Constants.LOGINERR + userCode.getId(), i + 1);
                int a = 3 - i;
                BizAssert.fail("账号或密码错误！还剩" + a + "次锁定账户");
            }
        }
    }

    public void pushSyslog(String loginName, String userCodeId) {
        if (Constants.CONFIG_SEND_SYSLOG.equals(configService.getHDConfig().getIsSendSyslog())) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ip = ServletUtil.getClientIP(request);
            SysLogMsgVO sysLogMsgVO = new SysLogMsgVO();
            sysLogMsgVO.setIp(ip)
                    .setLoginTime(LocalDateTime.now())
                    .setSysName(Constants.CDI_IAM)
                    .setSuccess("失败")
                    .setLoginName(loginName)
                    .setLoginCount(cacheService.get(Constants.IAM_LOGIN, Constants.LOGINERR + userCodeId));
            asyncService.sysLog(JsonUtils.toJson(sysLogMsgVO));
        }
    }

    public List<Menu> getMenuByUserCodeId(String userCodeId, String appId) {
        if (ObjectUtil.isEmpty(appId)) {
            BizAssert.fail("应用不存在");
        }
        //获取角色
        List<RoleUserCode> roleUserCodes = roleUserCodeService.findRoleUserCode(Arrays.asList(userCodeId), Arrays.asList(appId), null);
        if (roleUserCodes.size() > 0) {
            Set<String> roleIds = roleUserCodes.stream().map(t -> t.getRoleId()).collect(Collectors.toSet());
            //获取菜单
            List<RoleMenu> roleMenus = roleMenuService.findRoleMenuListByRoleIds(roleIds);
            if (roleMenus.size() > 0) {
                Set<String> menuIds = roleMenus.stream().map(t -> t.getMenuId()).collect(Collectors.toSet());
                List<Menu> menus = menuService.listByIds(menuIds);
                return menus;
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<Menu> findMenusByUserCodeAndRoleForSpecialApp(String userCodeId, String roleId, App app) {
        Set<String> roleIdSet = new HashSet<>();
        // 若请求时指定角色
        if (StrUtil.isNotBlank(roleId)) {
            roleIdSet.add(roleId);
        } else {
            UserCode userCode = userCodeService.getById(userCodeId);
            if (StrUtil.isNotBlank(userCode.getDefaultRoles())) {
                HashMap<String, String> roleMap = JsonUtils.parse(userCode.getDefaultRoles(), HashMap.class);
                if (StrUtil.isNotBlank(roleMap.get(app.getAppCode()))) {
                    roleIdSet.add(roleMap.get(app.getAppCode()));
                }
            }

            if (ObjectUtil.isEmpty(roleIdSet)) {
                //获取角色
                List<RoleUserCode> roleUserCodes = roleUserCodeService.findRoleUserCode(Collections.singletonList(userCodeId),
                        Collections.singletonList(app.getId()), Collections.singletonList(roleId));
                if (ObjectUtil.isEmpty(roleUserCodes)) {
                    return new ArrayList<>();
                }
                // 没有默认则取首个
                roleIdSet.add(roleUserCodes.get(0).getRoleId());
            }
        }

        List<RoleMenu> roleMenus = roleMenuService.findRoleMenuListByRoleIds(roleIdSet);
        if (ObjectUtil.isNotEmpty(roleMenus)) {
            Set<String> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toSet());
            return menuService.listByIds(menuIds);
        }

        return new ArrayList<>();
    }

    public static void main(String[] args) {
        List<LoginStrategy> loginStrategies = getAnimalStrategys();
        for (LoginStrategy loginStrategy : loginStrategies) {
//            System.err.println(loginStrategy.getMsg());
        }
    }

    public static List<LoginStrategy> getAnimalStrategys() {
        List<LoginStrategy> animalStrategies = new ArrayList<>();
        animalStrategies.add(new LoginCheckCodeStrategy());
//        animalStrategies.add(new LoginPasswordStrategy());
//        animalStrategies.add(new LoginPhoneShortMessageStrategy());
        return animalStrategies;
    }

}
