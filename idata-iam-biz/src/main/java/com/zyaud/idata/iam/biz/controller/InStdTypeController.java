package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.util.ObjectUtil;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.core.model.IdEntity;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.log.annotation.SysLog;
import com.zyaud.fzhx.log.enums.OptypeEnum;
import com.zyaud.idata.iam.biz.mapper.StdTypeMapper;
import com.zyaud.idata.iam.biz.model.dto.StdTypeCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.StdTypeUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.StdType;
import com.zyaud.idata.iam.biz.model.vo.IdVO;
import com.zyaud.idata.iam.biz.service.IInStdTypeService;
import com.zyaud.idata.iam.common.errorcode.DictMngErrorEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 内部系统调用字典控制器
 */
@Slf4j
@RestController
@RequestMapping("/system/InStdType")
@Api(value = "InStdCodeController", tags = "内部系统调用字典类型模块")
public class InStdTypeController extends SuperController {

    @Resource
    private IInStdTypeService crudService;

    @Resource
    private StdTypeMapper stdTypeMapper;

    @ApiOperation(value = "新增一条记录")
    @PostMapping(value = "/create")
    @SysLog(value = "'新增字典类型,字典类型名:' + #createDTO.stdName", optype = OptypeEnum.INSERT)
    public Result create(@Validated @RequestBody StdTypeCreateDTO createDTO) {
        crudService.createStdType(createDTO);
        return ok();
    }

    @ApiOperation(value = "删除记录")
    @PostMapping(value = "/delete")
    @SysLog(value = "'删除字典类型,字典类型id:' + #idVO.id", optype = OptypeEnum.DELETE)
    public Result delete(@Validated @RequestBody IdVO idVO) {
        crudService.deleteStdType(idVO.getId());
        return ok();
    }

    @ApiOperation(value = "修改某条记录")
    @PostMapping(value = "/update")
    @SysLog(value = "'修改字典类型,字典类型id:' + #updateDTO.id + ',字典类型名:' + #updateDTO.stdName", optype = OptypeEnum.UPDATE)
    public Result update(@RequestBody @Validated(IdEntity.Update.class) StdTypeUpdateDTO updateDTO) {
        crudService.updateStdType(updateDTO);
        return ok();
    }

    @ApiOperation(value = "单条记录查询")
    @PostMapping(value = "/get")
    @SysLog(value = "'查询字典类型,字典类型id:' + #id.id", optype = OptypeEnum.SELECT)
    public Result<StdType> get(@Validated @RequestBody IdVO id) {
        StdType stdType = crudService.getById(id.getId());
        if (ObjectUtil.isEmpty(stdType)) {
            return Result.fail(DictMngErrorEnum.STD_CODE_IS_NULL);
        }
        return ok(stdType);
    }

    @ApiOperation("字典类型下拉列表")
    @PostMapping({"/stdTypeList"})
    public Result<List<StdType>> stdTypeList() {
        List<StdType> stdTypes = stdTypeMapper.listOrderByAsc(StdType.ORDERINDEX);

        return ok(stdTypes);
    }


}
