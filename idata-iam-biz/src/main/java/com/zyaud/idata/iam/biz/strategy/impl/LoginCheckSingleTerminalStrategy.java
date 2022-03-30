package com.zyaud.idata.iam.biz.strategy.impl;

import cn.hutool.extra.servlet.ServletUtil;
import com.zyaud.idata.iam.biz.model.vo.AuthParam;
import com.zyaud.idata.iam.biz.model.vo.LoginRspVO;
import com.zyaud.idata.iam.biz.service.IAsyncService;
import com.zyaud.idata.iam.biz.service.IConfigService;
import com.zyaud.idata.iam.biz.strategy.LoginStrategy;
import com.zyaud.idata.iam.common.utils.Constants;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
public class LoginCheckSingleTerminalStrategy implements LoginStrategy {
    @Resource
    private IConfigService configService;
    @Resource
    private IAsyncService asyncService;

    @Override
    public void login(LoginRspVO loginRspVO, AuthParam authParam) {
        if (loginRspVO.isSuccess()) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ip = ServletUtil.getClientIP(request);
            asyncService.singleTerminal(authParam.getLoginName(), loginRspVO.getUserId(), ip, loginRspVO.getAccessToken(), loginRspVO.getRefreshToken());
        }
    }

    @Override
    public boolean isSupport(LoginRspVO loginRspVO, AuthParam authParam) {
        //获取配置
        String oneWayLogin = configService.getHDConfig().getIsOneWayLogin();
        //非单终端
        if (oneWayLogin.equals(Constants.DEFAULT_ONE_WAY_LOGIN)) {
            return false;
        }
        return true;
    }
}
