package com.zyaud.idata.iam.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.idata.iam.biz.model.dto.in.AppInDTO;
import com.zyaud.idata.iam.biz.model.entity.App;
import com.zyaud.idata.iam.biz.service.IAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "内部业务系统调用应用模块")
@RequestMapping("/system/inApp")
public class InAppController extends SuperController {
    @Resource
    private IAppService appService;

    @ApiOperation("获取所有应用")
    @PostMapping(value = {"/list"})
    public List<AppInDTO> list() {
        List<App> appList = appService.list();
        List<AppInDTO> appInDTOS = new ArrayList<>();
        appList.forEach(a -> appInDTOS.add(BeanUtil.toBean(a, AppInDTO.class)));
        return appInDTOS;
    }
}
