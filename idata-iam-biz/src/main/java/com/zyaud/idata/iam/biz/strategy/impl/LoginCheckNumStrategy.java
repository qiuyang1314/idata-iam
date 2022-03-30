package com.zyaud.idata.iam.biz.strategy.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.zyaud.fzhx.cache.service.CacheService;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.common.utils.JsonUtils;
import com.zyaud.idata.iam.biz.model.entity.UserCode;
import com.zyaud.idata.iam.biz.model.vo.AuthParam;
import com.zyaud.idata.iam.biz.model.vo.LoginRspVO;
import com.zyaud.idata.iam.biz.model.vo.SysLogMsgVO;
import com.zyaud.idata.iam.biz.service.IAsyncService;
import com.zyaud.idata.iam.biz.service.IConfigService;
import com.zyaud.idata.iam.biz.service.IUserCodeService;
import com.zyaud.idata.iam.biz.strategy.LoginStrategy;
import com.zyaud.idata.iam.common.utils.Constants;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
public class LoginCheckNumStrategy implements LoginStrategy {
    @Resource
    private CacheService cacheService;
    @Resource
    private IUserCodeService userCodeService;
    @Resource
    private IConfigService configService;
    @Resource
    private IAsyncService asyncService;

    @Override
    public void login(LoginRspVO loginRspVO, AuthParam authParam) {
        UserCode userCode = userCodeService.getByLoginName(authParam.getLoginName());
        if (!loginRspVO.isSuccess()) {
            Integer loginNums = cacheService.get(Constants.IAM_LOGIN, Constants.LOGINERR + userCode.getId());
            //推送日志
            if (ObjectUtil.isNotNull(loginNums)) {
                loginNums = loginNums + 1;
            } else {
                loginNums = 1;
            }
            pushSyslog(userCode.getLoginName(), loginNums);
            cacheService.set(Constants.IAM_LOGIN, Constants.LOGINERR + userCode.getId(), loginNums);
            if (loginNums >= 3) {
                // TODO  常量
                userCode.setStatus("1");
                userCodeService.updateById(userCode);
                cacheService.del(Constants.LOGINERR + userCode.getId());
                BizAssert.fail("账号或密码错误超过3次，账号已锁定");
            } else {
                int a = 3 - loginNums;
                BizAssert.fail("账号或密码错误！还剩" + a + "次锁定账户");
            }
        } else {
            //登录成功删除标记
            cacheService.del(Constants.IAM_LOGIN, Constants.LOGINERR + userCode.getId());
        }
    }

    public void pushSyslog(String loginName, Integer loginNums) {
        if (Constants.CONFIG_SEND_SYSLOG.equals(configService.getHDConfig().getIsSendSyslog())) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ip = ServletUtil.getClientIP(request);
            SysLogMsgVO sysLogMsgVO = new SysLogMsgVO();
            sysLogMsgVO.setIp(ip)
                    .setLoginTime(LocalDateTime.now())
                    .setSysName(Constants.CDI_IAM)
                    .setSuccess("失败")
                    .setLoginName(loginName)
                    .setLoginCount(loginNums);
            asyncService.sysLog(JsonUtils.toJson(sysLogMsgVO));
        }
    }

    @Override
    public boolean isSupport(LoginRspVO loginRspVO, AuthParam authParam) {
        //TODO 错误三次添加验证码
        return true;
    }
}
