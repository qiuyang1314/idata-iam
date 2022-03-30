package com.zyaud.idata.iam.biz.service.impl;

import com.zyaud.idata.iam.biz.model.vo.AuthParam;
import com.zyaud.idata.iam.biz.model.vo.LoginRspVO;
import com.zyaud.idata.iam.biz.service.ILoginService;
import com.zyaud.idata.iam.biz.strategy.LoginStrategy;
import com.zyaud.idata.iam.biz.strategy.impl.LoginCheckCodeStrategy;
import com.zyaud.idata.iam.biz.strategy.impl.LoginCheckNumStrategy;
import com.zyaud.idata.iam.biz.strategy.impl.LoginCheckPasswordStrategy;
import com.zyaud.idata.iam.biz.strategy.impl.LoginCheckSingleTerminalStrategy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoginServiceImpl implements ILoginService {

    @Resource
    private LoginCheckCodeStrategy loginCheckCodeStrategy;
    @Resource
    private LoginCheckPasswordStrategy loginCheckPasswordStrategy;
    @Resource
    private LoginCheckNumStrategy loginCheckNumStrategy;
    @Resource
    private LoginCheckSingleTerminalStrategy loginCheckSingleTerminalStrategy;

    /**
     * 策略列表
     *
     * @return
     */
    public List<LoginStrategy> loginStrategys() {
        List<LoginStrategy> loginStrategies = new ArrayList<>();
        //验证码
        loginStrategies.add(loginCheckCodeStrategy);
        //密码
        loginStrategies.add(loginCheckPasswordStrategy);
        //错误次数
        loginStrategies.add(loginCheckNumStrategy);
        //单终端
        loginStrategies.add(loginCheckSingleTerminalStrategy);
        return loginStrategies;
    }

    @Override
    public LoginRspVO login(AuthParam authParam) {
        LoginRspVO rspVO = new LoginRspVO();
        rspVO.setSuccess(true);
        for (LoginStrategy loginStrategy : loginStrategys()) {
            //配置支持
            if (loginStrategy.isSupport(rspVO, authParam)) {
                loginStrategy.login(rspVO, authParam);
            } else {
                rspVO.setSuccess(false);
                 return rspVO;
            }
        }
        return rspVO;
    }
}
