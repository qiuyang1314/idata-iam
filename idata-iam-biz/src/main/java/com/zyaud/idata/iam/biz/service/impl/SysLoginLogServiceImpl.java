package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.common.utils.JsonUtils;
import com.zyaud.fzhx.log.model.OptLogDTO;
import com.zyaud.idata.iam.biz.mapper.SysLoginLogMapper;
import com.zyaud.idata.iam.biz.model.entity.SysLoginLog;
import com.zyaud.idata.iam.biz.model.vo.LoginVO;
import com.zyaud.idata.iam.biz.service.ISysLoginLogService;
import com.zyaud.idata.iam.common.utils.Constants;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements ISysLoginLogService {

    @Resource
    private SysLoginLogMapper sysLoginLogMapper;

    @Override
    public List<SysLoginLog> findSysLoginLogByLoginName(String loginName) {
        return sysLoginLogMapper.findSysLoginLogByLoginName(loginName);
    }


    @Override
    public void saveLoginLog(OptLogDTO optLogDTO, UserAgent userAgent) {

        SysLoginLog sysLoginLog = new SysLoginLog();
        //获取浏览器信息
        // 获取客户端操作系统
        String system = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browserName = userAgent.getBrowser().getName();
        List<LoginVO> loginVOS = JsonUtils.parse(optLogDTO.getParams(), new TypeReference<List<LoginVO>>() {
        });

        sysLoginLog.setOs(system)
                .setLoginName(loginVOS.get(0).getLoginName())
                .setRemoteIp(optLogDTO.getRequestIp())
                .setBrowser(browserName)
                .setMsg("登录成功")
                .setStatus(Constants.LOGIN_STATUS_SUCCEED);
        if (ObjectUtil.isNotEmpty(optLogDTO.getResult())) {
            Result result = JsonUtils.parse(optLogDTO.getResult(), Result.class);
            if (result.getCode() != 0) {
                sysLoginLog.setMsg(result.getMsg())
                        .setStatus(Constants.LOGIN_STATUS_ERROR);
            }
        }
        sysLoginLogMapper.insert(sysLoginLog);

    }
}
