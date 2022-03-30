package com.zyaud.idata.iam.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zyaud.fzhx.binder.annotation.BindResult;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.core.mybatis.QueryBuilder;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.iam.annotation.PreAuthorize;
import com.zyaud.fzhx.log.annotation.SysLog;
import com.zyaud.fzhx.log.enums.OptypeEnum;
import com.zyaud.idata.iam.biz.model.dto.StdCodeCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.StdCodePageDTO;
import com.zyaud.idata.iam.biz.model.dto.StdCodeUpdateDTO;
import com.zyaud.idata.iam.biz.model.dto.in.StdCodeTreeDTO;
import com.zyaud.idata.iam.biz.model.entity.StdCode;
import com.zyaud.idata.iam.biz.model.vo.PublicStringVO;
import com.zyaud.idata.iam.biz.model.vo.StdCodeVO;
import com.zyaud.idata.iam.biz.service.IStdCodeService;
import com.zyaud.idata.iam.common.errorcode.DictMngErrorEnum;
import com.zyaud.idata.iam.common.utils.Constants;
import com.zyaud.idata.iam.common.vo.PublicIdListReqVO;
import com.zyaud.idata.iam.common.vo.PublicIdReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * <p>
 * 字典编码 前端控制器
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
@Slf4j
@RestController
@Api(tags = "字典值模块")
@RequestMapping("/system/std-code")
public class StdCodeController extends SuperController {

    @Resource
    private IStdCodeService crudService;

    @ApiOperation(value = "新增字典值")
    @PostMapping(value = "/create")
    @SysLog(value = "'新增字典值,字典值名:' + #createDTO.codeName + ',所属字典类型:' +#createDTO.stdType", optype = OptypeEnum.INSERT)
    @PreAuthorize(value = "hasPermission('system:std-code:create')", msg = "您没有‘新增字典项’的权限")
    public Result<Boolean> create(@Validated @RequestBody StdCodeCreateDTO createDTO) {
        StdCode entity = BeanUtil.toBean(createDTO, StdCode.class);
        return ok(crudService.save(entity));
    }

    @ApiOperation(value = "删除字典值")
    @PostMapping(value = "/delete")
    @SysLog(value = "'删除字典值,字典值id:' + #reqVO.ids", optype = OptypeEnum.DELETE)
    @PreAuthorize(value = "hasPermission('system:std-code:delete')", msg = "您没有‘删除字典项’的权限")
    public Result<Boolean> delete(@Validated @RequestBody PublicIdListReqVO reqVO) {
        List<StdCode> dictDatas = this.crudService.listByIds(reqVO.getIds());
        Set<String> collect = dictDatas.stream().map(t -> t.getStdType()).collect(Collectors.toSet());
        //取系统默认交集
        collect.retainAll(Constants.getList());
        if (ObjectUtil.isNotEmpty(collect)) {
            List<StdCode> dictDataList = new ArrayList<>();
            for (String s : collect) {
                Optional<StdCode> first = dictDataList.stream().filter(t -> t.getStdType().equals(s)).findFirst();
                first.ifPresent(t -> dictDataList.add(t));
            }
            if (ObjectUtil.isNotEmpty(dictDataList)) {
                String string = "";
                for (StdCode dictData : dictDataList) {
                    string += "字典值：" + dictData.getCodeName() + " 是系统数据，不许删除！";
                }
                return fail(DictMngErrorEnum.STD_CODE_NAME_IS_SYS_DATA_CANNOT_DELETE.getCode(), string);
            }
        }
        return ok(crudService.removeByIds(reqVO.getIds()));
    }

    @ApiOperation(value = "修改字典值")
    @PostMapping(value = "/update")
    @SysLog(value = "'修改字典值,字典值id:' + #updateDTO?.id + ',字典值名:' + #updateDTO?.codeName + ',所属字典类型:' + #updateDTO?.stdType", optype = OptypeEnum.UPDATE)
    @PreAuthorize(value = "hasPermission('system:std-code:update')", msg = "您没有‘编辑字典项’的权限")
    public Result<Boolean> update(@Validated @RequestBody StdCodeUpdateDTO updateDTO) {
        StdCode entity = BeanUtil.toBean(updateDTO, StdCode.class);
        return ok(crudService.updateById(entity));
    }

    @ApiOperation(value = "查询字典值")
    @PostMapping(value = "/get")
    @SysLog(value = "'查询字典值:' + #reqVO.id", optype = OptypeEnum.SELECT)
    @PreAuthorize(value = "permitAll()")
    public Result<StdCode> get(@Validated @RequestBody PublicIdReqVO reqVO) {
        return ok(crudService.getById(reqVO.getId()));
    }

    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    @BindResult
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<IPage<StdCode>> page(@RequestBody StdCodePageDTO pageDTO) {
        QueryWrapper<StdCode> query = QueryBuilder.queryWrapper(pageDTO);
        query.orderByAsc(StdCode.ORDERINDEX);
        IPage<StdCode> page = crudService.page(pageDTO.getPage(), query);
        IPage iPage = new Page(page.getCurrent(), page.getSize(), page.getTotal());
        List<StdCodeVO> stdCodeVOS = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(page.getRecords())) {
            page.getRecords().forEach(t -> stdCodeVOS.add(BeanUtil.toBean(t, StdCodeVO.class)
                    .setIsuseable(Constants.USEABLE_NORMAL.equals(t.getUseable()) ? true : false)));
        }
        iPage.setRecords(stdCodeVOS);
        return ok(iPage);
    }

    @ApiOperation(value = "字典类型下拉列表")
    @PostMapping(value = "/getByStdType")
    @PreAuthorize(value = "permitAll()")
    public Result<List<StdCode>> getByStdType(@Validated @RequestBody PublicStringVO publicStringVO) {
        QueryWrapper<StdCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StdCode.STDTYPE, publicStringVO.getString())
                .orderByAsc(StdCode.ORDERINDEX)
                .eq(StdCode.USEABLE, Constants.USEABLE_NORMAL);
        return ok(this.crudService.list(queryWrapper));
    }

    @ApiOperation("字典值停用/启用")
    @PostMapping({"/isUseable"})
    @SysLog(value = "'停用/启用字典值,字典值id:' + #publicIdReqVO.id", optype = OptypeEnum.UPDATE)
    @Transactional
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<Boolean> isUseable(@RequestBody @Validated PublicIdReqVO publicIdReqVO) {
        if (StrUtil.isEmpty(publicIdReqVO.getId())) {
            return fail(DictMngErrorEnum.STD_CODE_ID_CANNOT_BE_NULL);
        }
        StdCode stdCode = this.crudService.getById(publicIdReqVO.getId());
        if (ObjectUtil.isEmpty(stdCode)) {
            return fail(DictMngErrorEnum.STD_CODE_IS_NULL.getCode(), "字典值不存在");
        }
        if (Constants.USEABLE_NORMAL.equals(stdCode.getUseable())) {
            stdCode.setUseable(Constants.USEABLE_STOP);
        } else {
            stdCode.setUseable(Constants.USEABLE_NORMAL);
        }
        return ok(this.crudService.updateById(stdCode));
    }


    /** 
    * @Description: 兼容前端同用树结构字典树
    * @Author: dong 
    * @param publicStringVO
    * @return: com.zyaud.fzhx.common.model.Result<java.util.List<com.zyaud.idata.iam.biz.model.dto.in.StdCodeTreeDTO>>
    * @Date: 2021/12/19 17:18
    */ 
    @ApiOperation(value = "字典类型树下拉列表")
    @PostMapping(value = "/getByStdTypeTree")
    @PreAuthorize(value = "permitAll()")
    public Result< List<StdCodeTreeDTO>> getByStdTypeTree(@Validated @RequestBody PublicStringVO publicStringVO) {
        List<StdCodeTreeDTO> stdCodeTree = new ArrayList<>();
        QueryWrapper<StdCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StdCode.STDTYPE, publicStringVO.getString())
                .orderByAsc(StdCode.ORDERINDEX)
                .eq(StdCode.USEABLE, Constants.USEABLE_NORMAL);
        List<StdCode> stdCodes = this.crudService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(stdCodes)){
            for (StdCode stdCode : stdCodes) {
                StdCodeTreeDTO stdCodeTreeDTO = new StdCodeTreeDTO();
                stdCodeTreeDTO.setName(stdCode.getCodeName());
                stdCodeTreeDTO.setId(stdCode.getCodeValue());
                stdCodeTreeDTO.setChildren(Lists.newArrayList());
                stdCodeTree.add(stdCodeTreeDTO);
            }
        }

        return ok(stdCodeTree);
    }

}
