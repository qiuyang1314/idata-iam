package com.zyaud.idata.iam.biz.strategy.impl;

import cn.hutool.core.util.ObjectUtil;
import com.zyaud.fzhx.cache.service.CacheService;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.idata.iam.biz.model.vo.AuthParam;
import com.zyaud.idata.iam.biz.model.vo.LoginPageConfigRspVO;
import com.zyaud.idata.iam.biz.model.vo.LoginRspVO;
import com.zyaud.idata.iam.biz.service.IConfigService;
import com.zyaud.idata.iam.biz.strategy.LoginStrategy;
import com.zyaud.idata.iam.common.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class LoginCheckCodeStrategy implements LoginStrategy {

    @Resource
    private IConfigService configService;
    @Resource
    CacheService cacheService;


    @Override
    public void login(LoginRspVO loginRspVO, AuthParam authParam) {
        // 先检测是否有开启验证码
        LoginPageConfigRspVO configRspVO = configService.getLoginPageConfig();
        if (ObjectUtil.isNotNull(configRspVO) && Constants.NO.equals(configRspVO.getEnableCaptcha())) {
            return;
        }

        //执行结果
        if (loginRspVO.isSuccess()) {
            loginRspVO.setSuccess(false);
            String key = authParam.getKey();
            String captcha = authParam.getCaptcha();
            if (StringUtils.isEmpty(captcha)) {
                BizAssert.fail("图形验证码不能为空！");
            }
            String text = cacheService.get(Constants.IAM_LOGIN, Constants.CAPTCHA_SESSION_KEY + key);
            cacheService.del(Constants.IAM_LOGIN, Constants.CAPTCHA_SESSION_KEY + key);
            // TODO 使用错误码方式
            if (!captcha.equalsIgnoreCase(text)) {
                BizAssert.fail("图形验证码错误");
            } else {
                loginRspVO.setSuccess(true);
            }
        }
    }


    @Override
    public boolean isSupport(LoginRspVO loginRspVO, AuthParam authParam) {
        return true;
    }
}
