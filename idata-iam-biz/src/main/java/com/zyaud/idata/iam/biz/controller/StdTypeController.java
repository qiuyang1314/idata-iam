package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.iam.annotation.PreAuthorize;
import com.zyaud.fzhx.log.annotation.SysLog;
import com.zyaud.fzhx.log.enums.OptypeEnum;
import com.zyaud.idata.iam.biz.model.dto.StdTypeCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.StdTypeUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.StdType;
import com.zyaud.idata.iam.biz.service.IStdTypeService;
import com.zyaud.idata.iam.common.errorcode.DictMngErrorEnum;
import com.zyaud.idata.iam.common.utils.Constants;
import com.zyaud.idata.iam.common.vo.PublicIdListReqVO;
import com.zyaud.idata.iam.common.vo.PublicIdReqVO;
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
import java.util.Set;
import java.util.stream.Collectors;


/**
 * <p>
 * 字典管理 前端控制器
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
@Slf4j
@RestController
@Api(tags = "字典类型模块")
@RequestMapping("/system/std-type")
public class StdTypeController extends SuperController {

    @Resource
    private IStdTypeService crudService;

    @ApiOperation(value = "新增字典类型")
    @PostMapping(value = "/create")
    @SysLog(value = "'新增字典类型,字典类型名:' + #createDTO.stdName", optype = OptypeEnum.INSERT)
    @PreAuthorize(value = "hasPermission('system:std-type:create')", msg = "您没有‘新增码表’的权限")
    public Result<Boolean> create(@Validated @RequestBody StdTypeCreateDTO createDTO) {
        StdType entity = BeanUtil.toBean(createDTO, StdType.class);
        return ok(crudService.save(entity));
    }

    @ApiOperation(value = "删除字典类型")
    @PostMapping(value = "/delete")
    @SysLog(value = "'删除字典类型,字典类型id:' + #ids", optype = OptypeEnum.DELETE)
    @PreAuthorize(value = "hasPermission('system:std-type:delete')", msg = "您没有‘删除字典类型’的权限")
    public Result<Boolean> delete(@Validated @RequestBody PublicIdListReqVO reqVO) {
        List<StdType> dictTypeList = this.crudService.getBaseMapper().selectBatchIds(reqVO.getIds());
        Set<StdType> collect1 = dictTypeList.stream().filter(t -> Constants.STD_TYPE_IN.equals(t.getStdType())).collect(Collectors.toSet());
        if (ObjectUtil.isNotEmpty(collect1)) {
            String std = "";
            for (StdType stdType : collect1) {
                std += stdType.getStdName() + ",";
            }
            return fail(DictMngErrorEnum.STD_TYPE_IS_ZERO_DELETE_FAIL.getCode(),std + "为内置字典,不可删除");
        }
        return ok(crudService.removeByIds(reqVO.getIds()));
    }

    @ApiOperation(value = "修改字典类型")
    @PostMapping(value = "/update")
    @SysLog(value = "'修改字典类型,字典类型id:' + #updateDTO.id + ',字典类型名:' + #updateDTO.stdName", optype = OptypeEnum.UPDATE)
    @PreAuthorize(value = "hasPermission('system:std-type:update')", msg = "您没有‘编辑字典类型’的权限")
    public Result<Boolean> update(@Validated @RequestBody StdTypeUpdateDTO updateDTO) {
        StdType stdType = this.crudService.getById(updateDTO.getId());
        if (ObjectUtil.isEmpty(stdType)) {
            return fail(DictMngErrorEnum.STD_CODE_IS_NULL.getCode(),"码表不存在");
        }
        if (Constants.STD_TYPE_IN.equals(stdType.getStdType())) {
            return fail(DictMngErrorEnum.STD_TYPE_IS_ZERO_UPDATE_FAIL.getCode(),"为内置字典,不可编辑");
        }
        StdType entity = BeanUtil.toBean(updateDTO, StdType.class);
        return ok(crudService.updateById(entity));
    }

    @ApiOperation(value = "查询字典类型")
    @PostMapping(value = "/get")
    @SysLog(value = "'查询字典类型,字典类型id:' + #id", optype = OptypeEnum.SELECT)
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<StdType> get(@Validated @RequestBody PublicIdReqVO reqVO) {
        return ok(crudService.getById(reqVO.getId()));
    }

    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<List<StdType>> page() {
        QueryWrapper<StdType> query = new QueryWrapper<>();
        query.orderByAsc(StdType.ORDERINDEX);
        return ok(crudService.list(query));
    }

    @ApiOperation("字典类型下拉列表")
    @PostMapping({"/getAllDictType"})
    @PreAuthorize(value = "permitAll()")
    public Result<List<StdType>> getAllDictType() {
        return this.ok(this.crudService.list());
    }


}
