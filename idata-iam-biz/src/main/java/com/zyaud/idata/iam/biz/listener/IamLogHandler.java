package com.zyaud.idata.iam.biz.listener;

import com.zyaud.fzhx.log.handler.LogHandler;
import com.zyaud.fzhx.log.model.OptLogDTO;
import com.zyaud.idata.iam.biz.service.ISysLogService;
import com.zyaud.idata.iam.biz.service.ISysLoginLogService;
import com.zyaud.idata.iam.common.utils.Constants;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class IamLogHandler implements LogHandler {

    @Resource
    private ISysLogService sysLogService;

    @Resource
    private ISysLoginLogService sysLoginLogService;

    @Override
    public void handleLog(OptLogDTO optLogDTO) {
        UserAgent userAgent = UserAgent.parseUserAgentString(optLogDTO.getUa());
        //登录
        if (optLogDTO.getActionMethod().equals("login")) {
            sysLoginLogService.saveLoginLog(optLogDTO, userAgent);
        } else {
            sysLogService.saveLog(Constants.IAM_CODE,
                    optLogDTO.getUa(),
                    optLogDTO.getParams(),
                    optLogDTO.getResult(),
                    optLogDTO.getOptype(),
                    optLogDTO.getModule(),
                    optLogDTO.getUserName(),
                    optLogDTO.getDescription(),
                    optLogDTO.getRequestIp(),
                    optLogDTO.getType(),
                    optLogDTO.getExDesc());
        }
    }
}
