package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.core.model.IdEntity;
import com.zyaud.fzhx.core.mybatis.QueryBuilder;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.iam.annotation.PreAuthorize;
import com.zyaud.idata.iam.biz.model.dto.ProblemPhotoCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.ProblemPhotoPageDTO;
import com.zyaud.idata.iam.biz.model.dto.ProblemPhotoUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.ProblemPhoto;
import com.zyaud.idata.iam.biz.service.IProblemPhotoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;


/**
 * <p>
 * 问题反馈图片关联表 前端控制器
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
@Slf4j
@RestController
@RequestMapping("/system/problem-photo")
public class ProblemPhotoController extends SuperController {

    @Resource
    private IProblemPhotoService crudService;

    @ApiOperation(value = "新增一条记录")
    @RequestMapping(value="/create", method = RequestMethod.POST)
    //@SysLog(value = "新增", request = false)
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<Boolean> create(@Validated ProblemPhotoCreateDTO createDTO) {
        ProblemPhoto entity = BeanUtil.toBean(createDTO, ProblemPhoto.class);
        return ok(crudService.save(entity));
    }

    @ApiOperation(value = "删除记录")
    @RequestMapping(value="/delete", method = RequestMethod.POST)
    //@SysLog("'删除:' + #ids")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<Boolean> delete(@RequestBody List<? extends Serializable> ids) {
        return ok(crudService.removeByIds(ids));
    }

    @ApiOperation(value = "修改某条记录")
    @RequestMapping(value="/update", method = RequestMethod.POST)
    //@SysLog(value = "'修改:' + #updateDTO?.id", request = false)
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<Boolean> update(@Validated(IdEntity.Update.class) ProblemPhotoUpdateDTO updateDTO) {
        ProblemPhoto entity = BeanUtil.toBean(updateDTO, ProblemPhoto.class);
        return ok(crudService.updateById(entity));
    }

    @ApiOperation(value = "单条记录查询")
    @RequestMapping(value="/get")
    //@SysLog("'查询:' + #id")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<ProblemPhoto> get(@RequestParam Serializable id) {
        return ok(crudService.getById(id));
    }

    @ApiOperation(value = "分页列表查询")
    @RequestMapping(value = "/page")
    //@SysLog(value = "'分页列表查询:第' + #pageDTO?.current + '页, 显示' + #pageDTO?.size + '行'", response = false)
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<IPage<ProblemPhoto>> page(@Validated ProblemPhotoPageDTO pageDTO) {
        IPage<ProblemPhoto> page = pageDTO.getPage();
        Wrapper<ProblemPhoto> query = QueryBuilder.queryWrapper(pageDTO);
        return ok(crudService.page(page, query));
    }





}
