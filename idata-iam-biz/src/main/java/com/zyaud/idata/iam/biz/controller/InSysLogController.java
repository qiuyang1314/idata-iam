package com.zyaud.idata.iam.biz.controller;

import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.idata.iam.biz.model.dto.SysLogCreateDTO;
import com.zyaud.idata.iam.biz.service.ISysLogService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * <p>
 * 现场系统 数据对接控制器
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
@Slf4j
@RestController
@RequestMapping("/system/inSyslog")
public class InSysLogController extends SuperController {

    @Resource
    private ISysLogService crudService;

    @ApiOperation(value = "现场系统添加日志记录到统一权限系统")
    @PostMapping(value = "/add")
    public Result add(@Validated @RequestBody SysLogCreateDTO createDTO) {
        crudService.saveLog(createDTO.getAppCode(),
                createDTO.getUa(),
                createDTO.getParams(),
                createDTO.getResult(),
                createDTO.getOptype(),
                createDTO.getModule(),
                createDTO.getUserName(),
                createDTO.getDescription(),
                createDTO.getRequestIp(),
                createDTO.getType(),
                createDTO.getExDesc());
        return ok();
    }


}
