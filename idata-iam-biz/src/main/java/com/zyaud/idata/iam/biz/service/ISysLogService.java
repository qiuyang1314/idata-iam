package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.biz.model.entity.SysLog;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface ISysLogService extends IService<SysLog> {
    /**
     * @param appCode     应用编码
     * @param ua          浏览器
     * @param params      请求参数
     * @param result      返回参数
     * @param optype      操作类型
     * @param module      模块
     * @param userName    登录名
     * @param description 操作描述
     * @param requestIp   操作ip
     * @param type        日志类型
     * @param exDesc      异常信息
     */
    void saveLog(String appCode,
                 String ua,
                 String params,
                 String result,
                 String optype,
                 String module,
                 String userName,
                 String description,
                 String requestIp,
                 String type,
                 String exDesc);
}
