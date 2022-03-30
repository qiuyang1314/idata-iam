package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyaud.idata.iam.biz.mapper.SysLogMapper;
import com.zyaud.idata.iam.biz.model.entity.SysLog;
import com.zyaud.idata.iam.biz.service.ISysLogService;
import com.zyaud.idata.iam.common.utils.Constants;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Override
    public void saveLog(String appCode,
                        String ua,
                        String params,
                        String result,
                        String optype,
                        String module,
                        String userName,
                        String description,
                        String requestIp,
                        String type,
                        String exDesc) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
        //保存日志
        SysLog sysLog = new SysLog();
        // 获取客户端操作系统
        String system = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browserName = userAgent.getBrowser().getName();
        sysLog.setOs(system)
                .setAppCode(appCode)
                .setParams(params)
                .setResult(result)
                .setOperateType(optype)
                .setModule(module)
                .setBrowser(browserName)
                .setOperator(userName)
                .setInfos(description)
                .setRemoteIp(requestIp)
                .setLogType(Constants.LOG_TYPE_OPERATE);
        if ("EX".equals(type) && StrUtil.isNotEmpty(exDesc)) {
            sysLog.setLogType(Constants.LOG_TYPE_ERROR)
                    .setErrMsg(exDesc);
        }
        this.save(sysLog);
    }
}
