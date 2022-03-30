package com.zyaud.idata.iam.biz.controller;

import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.core.web.SuperController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 版本信息 前端控制器
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-12-28
 */
@Slf4j
@RestController
@RequestMapping(value = {"/system/version"})
@Api(tags = "版本信息模块")
public class IamSysVersionController extends SuperController {
    @Value("${spring.application.version}")
    private String version;


    @ApiOperation(value = "查看版本信息")
    @PostMapping(value = "/getSysVer")
    public Result getSysVer() {
        Map<String, String> retMap = new HashedMap<>();
        retMap.put("version", version);
        return ok(retMap);
    }
}
