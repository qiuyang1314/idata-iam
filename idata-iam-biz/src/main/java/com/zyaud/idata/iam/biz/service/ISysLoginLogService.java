package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.fzhx.log.model.OptLogDTO;
import com.zyaud.idata.iam.biz.model.entity.SysLoginLog;
import eu.bitwalker.useragentutils.UserAgent;

import java.util.List;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface ISysLoginLogService extends IService<SysLoginLog> {

    List<SysLoginLog> findSysLoginLogByLoginName(String loginName);

    void saveLoginLog(OptLogDTO optLogDTO, UserAgent userAgent);
}
