package com.zyaud.idata.iam.biz.controller;


import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.log.annotation.SysLog;
import com.zyaud.fzhx.log.enums.OptypeEnum;
import com.zyaud.idata.iam.biz.model.dto.in.MenuCreateInDTO;
import com.zyaud.idata.iam.biz.model.dto.in.MenuDelInDTO;
import com.zyaud.idata.iam.biz.model.vo.MenuTreeVO;
import com.zyaud.idata.iam.biz.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


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
@Api(tags = "内部业务系统调用菜单模块")
@RequestMapping("/system/inMenu")
public class InMenuController extends SuperController {

    @Resource
    private IMenuService crudService;

    @ApiOperation("新增菜单")
    @PostMapping(value = {"/{appCode}/createMenu"})
    @SysLog(value = "'新增菜单,应用编码：' + #appCode", optype = OptypeEnum.INSERT)
    public Result<Map<String /*menuCode*/, String /*menuId*/>> createMenuByBizApp(@PathVariable String appCode, @Validated @RequestBody MenuCreateInDTO createDTO) {
        return ok(crudService.createMenuByBizApp(appCode, createDTO));
    }

    @ApiOperation(value = "删除菜单")
    @PostMapping(value = "/{appCode}/deleteMenu")
    @SysLog(value = "'删除菜单,应用编码：' + #appCode", optype = OptypeEnum.UPDATE)
    public Result<String> deleteMenu(@PathVariable String appCode, @Validated @RequestBody MenuDelInDTO delInDTO) {
        return crudService.deleteMenu(appCode, delInDTO);
    }

    @ApiOperation(value = "菜单下拉树形列表")
    @PostMapping(value = "/{appCode}/findMenuTreeNodeList")
    public Result<List<MenuTreeVO>> findMenuTreeNodeList(@PathVariable String appCode) {
        return ok(crudService.findMenuTreeNodeList(appCode));
    }


}
