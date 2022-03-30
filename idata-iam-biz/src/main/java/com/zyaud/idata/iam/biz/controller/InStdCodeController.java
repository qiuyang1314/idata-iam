package com.zyaud.idata.iam.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.idata.iam.biz.model.dto.in.StdCodeInDTO;
import com.zyaud.idata.iam.biz.model.dto.in.StdCodeTreeDTO;
import com.zyaud.idata.iam.biz.model.entity.StdCode;
import com.zyaud.idata.iam.biz.model.vo.PublicStringVO;
import com.zyaud.idata.iam.biz.model.vo.StdCodeListVO;
import com.zyaud.idata.iam.biz.service.IInStdCodeService;
import com.zyaud.idata.iam.client.dto.StdCodeListReqDTO;
import com.zyaud.idata.iam.common.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 内部系统调用字典项控制器
 */
@Slf4j
@RestController
@RequestMapping("/system/inStdCode")
@Api(value = "InStdCodeController", tags = "内部系统调用字典值模块")
public class InStdCodeController extends SuperController {

    @Resource
    private IInStdCodeService crudService;

    @ApiOperation(value = "字典类型下拉列表")
    @PostMapping(value = "/getByStdType")
    public Result<List<StdCode>> getByStdType(@Validated @RequestBody PublicStringVO publicStringVO) {
        List<StdCode> stdCodeList = crudService.getStdCodeListByStdType(publicStringVO.getString());
        return ok(stdCodeList);
    }

    @ApiOperation(value = "根据字典类型获取字典值列表")
    @PostMapping(value = "/getCodeListByStdType")
    public List<StdCodeInDTO> getCodeListByStdType(@Validated @RequestBody StdCodeListReqDTO stdCodeListReqDTO) {
        log.info("根据字典类型获取字典值列表");
        List<StdCode> stdCodes = crudService.getStdCodeListByStdType(stdCodeListReqDTO.getStdType());
        List<StdCodeInDTO> stdCodeInDTOS = new ArrayList<>();
        stdCodes.forEach(t -> stdCodeInDTOS.add(BeanUtil.toBean(t, StdCodeInDTO.class)));
        return stdCodeInDTOS;
    }

    @ApiOperation(value = "根据字典类型和字典项值获取字典值")
    @PostMapping(value = "/getByStdTypeAndValue")
    public StdCodeInDTO getByStdTypeAndValue(@Validated @RequestBody StdCodeListVO codeListVO) {
        log.info("根据字典类型和字典项值获取字典值");
        StdCode stdCode = crudService.getStdCodeByStdTypeAndValue(codeListVO.getStdType(), codeListVO.getValue());
        StdCodeInDTO stdCodeInDTO = BeanUtil.toBean(stdCode, StdCodeInDTO.class);
        return stdCodeInDTO;
    }

    @ApiOperation(value = "获取所有字典项")
    @PostMapping(value = "/findAllStdCodeList")
    public List<StdCodeInDTO> findAllStdCodeList() {
        log.info("获取所有字典项");
        List<StdCode> stdCodeList = crudService.list();
        List<StdCodeInDTO> stdCodeInDTOS = stdCodeList.stream().map(t -> BeanUtil.toBean(t, StdCodeInDTO.class))
                .collect(Collectors.toList());
        return stdCodeInDTOS;
    }


    @ApiOperation(value = "字典类型树下拉列表")
    @PostMapping(value = "/getByStdTypeTree")
    public List<StdCodeTreeDTO> getByStdTypeTree(@Validated @RequestBody StdCodeListReqDTO stdCodeListReqDTO) {
        List<StdCodeTreeDTO> stdCodeTree = new ArrayList<>();
        QueryWrapper<StdCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StdCode.STDTYPE, stdCodeListReqDTO.getStdType())
                .orderByAsc(StdCode.ORDERINDEX)
                .eq(StdCode.USEABLE, Constants.USEABLE_NORMAL);
        List<StdCode> stdCodes = this.crudService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(stdCodes)) {
            for (StdCode stdCode : stdCodes) {
                StdCodeTreeDTO stdCodeTreeDTO = new StdCodeTreeDTO();
                stdCodeTreeDTO.setName(stdCode.getCodeName());
                stdCodeTreeDTO.setId(stdCode.getCodeValue());
                stdCodeTreeDTO.setChildren(Lists.newArrayList());
                stdCodeTree.add(stdCodeTreeDTO);
            }
        }

        return stdCodeTree;
    }


}
