package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.core.mybatis.QueryBuilder;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.iam.annotation.PreAuthorize;
import com.zyaud.idata.iam.biz.mapper.SysLoginLogMapper;
import com.zyaud.idata.iam.biz.model.dto.SysLoginLogPageDTO;
import com.zyaud.idata.iam.biz.model.entity.SysLoginLog;
import com.zyaud.idata.iam.biz.model.vo.LastLoginVO;
import com.zyaud.idata.iam.biz.service.ISysLoginLogService;
import com.zyaud.idata.iam.common.utils.MngUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
@Slf4j
@RestController
@RequestMapping("/system/sys-login-log")
@Api(tags = "登录日志模块")
public class SysLoginLogController extends SuperController {

    @Resource
    private ISysLoginLogService crudService;

    @Resource
    private SysLoginLogMapper sysLoginLogMapper;

    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<IPage<SysLoginLog>> page(@RequestBody SysLoginLogPageDTO pageDTO) {
        IPage<SysLoginLog> page = pageDTO.getPage();
        QueryWrapper<SysLoginLog> query = QueryBuilder.queryWrapper(pageDTO);
        query.orderByDesc(SysLoginLog.CREATE_TIME);
        return ok(crudService.page(page, query));
    }

    @ApiOperation(value = "获取当前登录账号的上次登录记录")
    @PostMapping(value = "/getLastLogin")
    public Result<LastLoginVO> getLastLogin() {
        String loginName = MngUtil.getUserName();
        LastLoginVO lastLoginVO = new LastLoginVO();
        SysLoginLog sysLoginLog = new SysLoginLog();

        //1、根据创建时间倒序获取前两条历史记录（因为第一条是刚刚保存的登录历史记录）
        if (StrUtil.isNotBlank(loginName)) {
            List<SysLoginLog> sysLoginLogs = sysLoginLogMapper.getLastRegister(loginName);
            if (sysLoginLogs.size() > 1) {
                sysLoginLog = sysLoginLogs.get(1);
            }
        }
        //转成vo返回给前端
        if (StrUtil.isBlank(sysLoginLog.getRemoteIp())) {
            lastLoginVO.setIpAdress("无");
        } else {
            lastLoginVO.setIpAdress(sysLoginLog.getRemoteIp());
        }
        if (ObjectUtil.isEmpty(sysLoginLog.getCreateTime())) {
            lastLoginVO.setLoginTime("无");
        } else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            lastLoginVO.setLoginTime(df.format(sysLoginLog.getCreateTime()));
        }

        return ok(lastLoginVO);
    }

}
