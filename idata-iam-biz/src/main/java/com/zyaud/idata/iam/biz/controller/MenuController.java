package com.zyaud.idata.iam.biz.controller;


import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.iam.annotation.PreAuthorize;
import com.zyaud.fzhx.log.annotation.SysLog;
import com.zyaud.fzhx.log.enums.OptypeEnum;
import com.zyaud.idata.iam.biz.model.dto.MenuCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.MenuPageDTO;
import com.zyaud.idata.iam.biz.model.dto.MenuUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.Menu;
import com.zyaud.idata.iam.biz.model.vo.MenuTreeVO;
import com.zyaud.idata.iam.biz.service.IMenuService;
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


/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
@Slf4j
@RestController
@Api(tags = "菜单模块")
@RequestMapping("/system/menu")
public class MenuController extends SuperController {

    @Resource
    private IMenuService crudService;

    @ApiOperation("新增菜单")
    @PostMapping(value = {"/create"})
    @SysLog(value = "'新增菜单,菜单名:' + #createDTO?.name + ',应用id:' + #createDTO?.appId", optype = OptypeEnum.INSERT)
//    @PreAuthorize(value = "hasPermission('system:menu:create')", msg = "您没有‘新增菜单’的权限")
    public Result<Boolean> create(@Validated @RequestBody MenuCreateDTO createDTO) {
        return this.ok(this.crudService.createMenu(createDTO));
    }

    @ApiOperation(value = "删除菜单")
    @PostMapping(value = "/delete")
    @SysLog(value = "'删除菜单,菜单id:' + #reqVO?.ids", optype = OptypeEnum.DELETE)
//    @PreAuthorize(value = "hasPermission('system:menu:delete')", msg = "您没有‘删除菜单’的权限")
    public Result<Boolean> delete(@Validated @RequestBody PublicIdListReqVO reqVO) {
        return ok(crudService.removeByIds(reqVO.getIds()));
    }

    @ApiOperation(value = "修改菜单")
    @PostMapping(value = "/update")
    @SysLog(value = "'修改菜单,菜单id:' + #updateDTO?.id + ',菜单名：' + #updateDTO?.name + ',应用id:' + #updateDTO?.appId", optype = OptypeEnum.UPDATE)
//    @PreAuthorize(value = "hasPermission('system:menu:get')", msg = "您没有‘编辑菜单’的权限")
    public Result<Boolean> update(@Validated @RequestBody MenuUpdateDTO updateDTO) {
        return ok(crudService.updateMenu(updateDTO));
    }

    @ApiOperation(value = "查询菜单")
    @PostMapping(value = "/get")
    @SysLog(value = "'查询菜单,菜单id:' + #id", optype = OptypeEnum.SELECT)
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<Menu> get(@Validated @RequestBody PublicIdReqVO reqVO) {
        return ok(crudService.getById(reqVO.getId()));
    }

    @ApiOperation(value = "菜单下拉树形列表(不包含按钮)")
    @PostMapping(value = "/getMenuTreeNeButton")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<List<MenuTreeVO>> getMenuTreeNeButton(@Validated @RequestBody MenuPageDTO menuPageDTO) {
        return ok(crudService.getMenuTreeNeButton(menuPageDTO));
    }

    @ApiOperation(value = "根据父级菜单获取按钮")
    @PostMapping(value = "/getButtonList")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<List<Menu>> getButtonList(@Validated @RequestBody MenuPageDTO menuPageDTO) {
        return ok(crudService.getButtonList(menuPageDTO));
    }

    @ApiOperation(value = "菜单下拉树形列表")
    @PostMapping(value = "/getMenuList")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<List<MenuTreeVO>> getMenuTree(@Validated @RequestBody MenuPageDTO menuPageDTO) {
        return ok(crudService.getMenuTree(menuPageDTO));
    }
}
