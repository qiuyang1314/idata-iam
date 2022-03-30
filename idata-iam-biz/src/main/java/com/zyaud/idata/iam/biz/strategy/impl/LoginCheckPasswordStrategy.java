package com.zyaud.idata.iam.biz.strategy.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.zyaud.fzhx.cache.service.CacheService;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.iam.core.FzhxIamUser;
import com.zyaud.fzhx.iam.token.Token;
import com.zyaud.fzhx.iam.token.TokenStore;
import com.zyaud.fzhx.iam.token.TokenUtils;
import com.zyaud.idata.iam.biz.model.entity.Office;
import com.zyaud.idata.iam.biz.model.entity.User;
import com.zyaud.idata.iam.biz.model.entity.UserCode;
import com.zyaud.idata.iam.biz.model.vo.AuthParam;
import com.zyaud.idata.iam.biz.model.vo.LoginRspVO;
import com.zyaud.idata.iam.biz.service.IOfficeService;
import com.zyaud.idata.iam.biz.service.IUserCodeService;
import com.zyaud.idata.iam.biz.service.IUserService;
import com.zyaud.idata.iam.biz.strategy.LoginStrategy;
import com.zyaud.idata.iam.common.utils.Constants;
import com.zyaud.idata.iam.common.utils.PasswordEncryptor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class LoginCheckPasswordStrategy implements LoginStrategy {

    @Resource
    private IUserCodeService userCodeService;
    @Resource
    private TokenStore tokenStore;
    @Resource
    private IUserService userService;
    @Resource
    private IOfficeService officeService;

    @Resource
    CacheService cacheService;


    @Override
    public void login(LoginRspVO loginRspVO, AuthParam authParam) {
        if (loginRspVO.isSuccess()) {
            loginRspVO.setSuccess(false);
            String loginName = authParam.getLoginName();
            String passwd = authParam.getPasswd();
            if (ObjectUtil.isEmpty(loginName) || ObjectUtil.isEmpty(passwd)) {
                BizAssert.fail("请填写账号或密码信息！");
            }
            UserCode userCode = userCodeService.getByLoginName(loginName);
            //不存在
            BizAssert.isNotEmpty(userCode, "账号或密码错误！");

            BizAssert.isTrue(!userCode.getStatus().equals(Constants.USERCODE_LOCK), "账号已锁定，请联系管理员");

            BizAssert.isTrue(!userCode.getStatus().equals(Constants.USERCODE_STOP), "账号已停用");

            //盐值校验
            PasswordEncryptor encoderMd51 = new PasswordEncryptor(userCode.getSalt(), "sha-256");
            //密码错误
            if (!encoderMd51.isPasswordValid(userCode.getPasswd(), passwd)) {
                loginRspVO.setSuccess(false);
                return;
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
                    .setReturnURL(authParam.getReturnURL())
                    .setSuccess(true);
        }
    }

    public Token getToken(FzhxIamUser fzhxIamUser, Long expire) {
        return TokenUtils.buildToken(fzhxIamUser.getName(), BeanUtil.beanToMap(fzhxIamUser), expire);
    }

    @Override
    public boolean isSupport(LoginRspVO loginRspVO, AuthParam authParam) {
        return true;
    }
}
