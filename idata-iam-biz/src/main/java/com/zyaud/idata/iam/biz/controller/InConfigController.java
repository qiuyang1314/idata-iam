package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.idata.iam.biz.model.dto.in.ConfigDTO;
import com.zyaud.idata.iam.biz.model.dto.in.ConfigReqVO;
import com.zyaud.idata.iam.biz.model.entity.Config;
import com.zyaud.idata.iam.biz.model.vo.AppCodeVO;
import com.zyaud.idata.iam.biz.model.vo.BizSysConfigVO;
import com.zyaud.idata.iam.biz.service.IInConfigService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fzhx-aicode
 * @since 2021-02-08
 */
@Slf4j
@RestController
@RequestMapping("/system/inConfig")
public class InConfigController extends SuperController {

    @Resource
    private IInConfigService crudService;

    @ApiOperation(value = "根据配置类型和配置编码获取配置项")
    @PostMapping(value = "/listConfig")
    public List<ConfigDTO> listConfig(@RequestBody ConfigReqVO configReqVO) {
        List<Config> configList = crudService.findConfigByCTypeAndCCode(configReqVO.getCType(), configReqVO.getCCode());
        List<ConfigDTO> configDTOList = configList.stream().map(t -> BeanUtil.toBean(t, ConfigDTO.class)).collect(Collectors.toList());
        return configDTOList;
    }

    @ApiOperation(value = "新增配置项")
    @PostMapping(value = "/createConfig")
    public void createConfig(@RequestBody ConfigDTO configDTO) {
        crudService.createConfig(BeanUtil.toBean(configDTO, Config.class));
    }


    @ApiOperation(value = "根据id来更新配置项，所以id不能为空")
    @PostMapping(value = "/updateConfig")
    public void updateConfig(@RequestBody ConfigDTO configDTO) {
        crudService.updateConfig(BeanUtil.toBean(configDTO, Config.class));
    }

    @ApiOperation(value = "根据应用编码获取配置")
    @PostMapping(value = "/getSysConfigByAppCode")
    public BizSysConfigVO getSysConfigByAppCode(@Validated @RequestBody AppCodeVO reqVO) {
        return crudService.getSysConfigByAppCode(reqVO.getAppCode());
    }
}
