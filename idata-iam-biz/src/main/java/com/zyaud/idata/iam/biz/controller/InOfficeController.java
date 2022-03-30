package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.idata.foundation.common.model.vo.PublicIdReqVO;
import com.zyaud.idata.iam.client.dto.OfficeInDTO;
import com.zyaud.idata.iam.client.dto.OfficeTreeInDTO;
import com.zyaud.idata.iam.biz.model.dto.in.OfficeTypeInDTO;
import com.zyaud.idata.iam.biz.model.entity.Office;
import com.zyaud.idata.iam.biz.model.vo.OfficeTreeVO;
import com.zyaud.idata.iam.biz.service.IInOfficeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 内部系统调用机构控制器
 */
@Slf4j
@RestController
@RequestMapping("/system/inOffice")
@Api(value = "InOfficeController", tags = "内部系统调用机构模块")
public class InOfficeController extends SuperController {

    @Resource
    private IInOfficeService crudService;

    @ApiOperation(value = "其他内部系统后端调用单条机构查询")
    @PostMapping(value = "/get")
    public OfficeInDTO get(PublicIdReqVO reqVO) {
        log.info("单条机构查询");
        Office office = crudService.getById(reqVO.getId());
        if (ObjectUtil.isEmpty(office)) {
            return new OfficeInDTO();
        }
        return BeanUtil.toBean(office, OfficeInDTO.class);

    }


    @ApiOperation(value = "其他内部系统后端调用机构下拉列表查询")
    @PostMapping(value = "/getList")
    public List<OfficeInDTO> getList() {
        log.info("机构下拉列表查询");
        List<Office> offices = crudService.list();
        List<OfficeInDTO> officeInDTOS = new ArrayList<>();
        offices.forEach(t -> officeInDTOS.add(BeanUtil.toBean(t, OfficeInDTO.class)));
        return officeInDTOS;
    }


    @ApiOperation(value = "其他内部系统后端调用机构下拉树形列表")
    @PostMapping(value = "/getAllOfficeTree")
    public List<OfficeTreeInDTO> getAllOfficeTree() {
        log.info("机构下拉树形列表");
        List<OfficeTreeVO> officeTreeVOS = crudService.getOfficeTree(crudService.list());
        List<OfficeTreeInDTO> officeTreeInDTOS = new ArrayList<>();
        officeTreeVOS.forEach(t -> officeTreeInDTOS.add(BeanUtil.toBean(t, OfficeTreeInDTO.class)));
        return officeTreeInDTOS;
    }

    @ApiOperation(value = "其他内部系统后端调用-通过机构类型获取机构列表")
    @PostMapping(value = "/getOfficeListByType")
    public List<OfficeInDTO> getOfficeListByType(@RequestBody OfficeTypeInDTO officeTypeDTO) {
        log.info("通过机构类型获取机构列表");
        return crudService.getOfficeListByType(officeTypeDTO.getOrgType());
    }
}
