package com.zyaud.idata.iam.biz.listener;

import com.zyaud.fzhx.log.event.SysLogEvent;
import com.zyaud.fzhx.log.model.OptLogDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
@AllArgsConstructor
public class Mylistener implements ApplicationListener<SysLogEvent> {
    @Resource
    private IamLogHandler iamLogHandler;

    @Override
    public void onApplicationEvent(SysLogEvent sysLogEvent) {
        OptLogDTO source = (OptLogDTO) sysLogEvent.getSource();
        iamLogHandler.handleLog(source);
    }
}
