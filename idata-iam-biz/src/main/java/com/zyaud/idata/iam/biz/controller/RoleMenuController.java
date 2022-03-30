package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.core.model.IdEntity;
import com.zyaud.fzhx.core.mybatis.QueryBuilder;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.iam.annotation.PreAuthorize;
import com.zyaud.idata.iam.biz.model.dto.RoleMenuCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.RoleMenuPageDTO;
import com.zyaud.idata.iam.biz.model.dto.RoleMenuUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.RoleMenu;
import com.zyaud.idata.iam.biz.service.IRoleMenuService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;


/**
 * <p>
 * 角色菜单 前端控制器
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
@Slf4j
@RestController
@RequestMapping("/system/role-menu")
public class RoleMenuController extends SuperController {

    @Resource
    private IRoleMenuService crudService;

    @ApiOperation(value = "新增一条记录")
    @PostMapping(value="/create")
    //@SysLog(value = "新增", request = false)
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<Boolean> create(@Validated RoleMenuCreateDTO createDTO) {
        RoleMenu entity = BeanUtil.toBean(createDTO, RoleMenu.class);
        return ok(crudService.save(entity));
    }

    @ApiOperation(value = "删除记录")
    @PostMapping(value="/delete")
    //@SysLog("'删除:' + #ids")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<Boolean> delete(@RequestBody List<? extends Serializable> ids) {
        return ok(crudService.removeByIds(ids));
    }

    @ApiOperation(value = "修改某条记录")
    @PostMapping(value="/update")
    //@SysLog(value = "'修改:' + #updateDTO?.id", request = false)
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<Boolean> update(@Validated(IdEntity.Update.class) RoleMenuUpdateDTO updateDTO) {
        RoleMenu entity = BeanUtil.toBean(updateDTO, RoleMenu.class);
        return ok(crudService.updateById(entity));
    }

    @ApiOperation(value = "单条记录查询")
    @PostMapping(value="/get")
    //@SysLog("'查询:' + #id")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<RoleMenu> get(@RequestParam Serializable id) {
        return ok(crudService.getById(id));
    }

    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    //@SysLog(value = "'分页列表查询:第' + #pageDTO?.current + '页, 显示' + #pageDTO?.size + '行'", response = false)
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<IPage<RoleMenu>> page(@Validated RoleMenuPageDTO pageDTO) {
        IPage<RoleMenu> page = pageDTO.getPage();
        Wrapper<RoleMenu> query = QueryBuilder.queryWrapper(pageDTO);
        return ok(crudService.page(page, query));
    }





}
