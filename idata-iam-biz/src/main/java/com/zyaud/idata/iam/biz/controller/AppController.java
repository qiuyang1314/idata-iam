package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.iam.annotation.PreAuthorize;
import com.zyaud.fzhx.log.annotation.SysLog;
import com.zyaud.fzhx.log.enums.OptypeEnum;
import com.zyaud.idata.iam.biz.model.dto.AppCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.AppPageDTO;
import com.zyaud.idata.iam.biz.model.dto.AppUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.App;
import com.zyaud.idata.iam.biz.service.IAppService;
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


/**
 * <p>
 * 系统应用 前端控制器
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
@Slf4j
@RestController
@Api(tags = {"应用模块"})
@RequestMapping("/system/app")
public class AppController extends SuperController {

    @Resource
    private IAppService crudService;

    @ApiOperation(value = "新增一条记录")
    @PostMapping(value = "/create")
    @SysLog(value = "'新增应用,应用名:' + #createDTO?.appName + ',应用编码:'+ #createDTO?.appCode", optype = OptypeEnum.INSERT)
    @PreAuthorize(value = "hasPermission('system:app:create')", msg = "您没有‘新增应用’的权限")
    public Result<Boolean> create(@Validated @RequestBody AppCreateDTO createDTO) {
        return this.ok(this.crudService.createApp(createDTO));
    }

    @ApiOperation(value = "删除记录")
    @PostMapping(value = "/delete")
    @SysLog(value = "'删除应用,应用id:' + #reqVO?.ids", optype = OptypeEnum.DELETE)
    @PreAuthorize(value = "hasPermission('system:app:delete')", msg = "您没有‘删除应用’的权限")
    public Result<Boolean> delete(@Validated @RequestBody PublicIdListReqVO reqVO) {
        return ok(crudService.removeByIds(reqVO.getIds()));
    }

    @ApiOperation("修改某条记录")
    @PostMapping(value = "/update")
    @SysLog(value = "'修改应用,应用id:' + #updateDTO?.id +',应用名:' + #updateDTO?.appName", optype = OptypeEnum.UPDATE)
    @PreAuthorize(value = "hasPermission('system:app:get')", msg = "您没有‘编辑应用’的权限")
    public Result<Boolean> update(@Validated @RequestBody AppUpdateDTO updateDTO) {
        return this.ok(this.crudService.updateApp(updateDTO));
    }

    @ApiOperation(value = "单条记录查询")
    @PostMapping(value = "/get")
    @SysLog(value = "'查询单个应用,应用id:' + #publicIdReqVO?.id", optype = OptypeEnum.SELECT)
    @PreAuthorize(value = "hasPermission('system:app:get')", msg = "您没有‘编辑应用’的权限")
    public Result<App> get(@Validated @RequestBody PublicIdReqVO publicIdReqVO) {
        return ok(crudService.getById(publicIdReqVO.getId()));
    }

    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<IPage<App>> page(@RequestBody AppPageDTO pageDTO) {
        return this.ok(this.crudService.appPage(pageDTO));
    }

    @ApiOperation("重置公钥私钥")
    @PostMapping({"/reset"})
    @SysLog(value = "'重置公钥私钥,应用id:' + #reqVO?.id", optype = OptypeEnum.UPDATE)
    @PreAuthorize(value = "hasPermission('system:app:reset')", msg = "您没有‘重置公钥私钥’的权限")
    public Result<Boolean> reset(@RequestBody PublicIdReqVO reqVO) {
        return this.ok(this.crudService.reset(reqVO.getId()));
    }

    @ApiOperation(value = "应用下拉列表")
    @PostMapping(value = "/getAppList")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<List<App>> getAppList() {
        List<App> appList = this.crudService.list();
        if (ObjectUtil.isEmpty(appList)) {
            appList = new ArrayList<>();
        }
        return ok(appList);
    }

    @ApiOperation("应用停用/启用")
    @PostMapping({"/isUseable"})
    @SysLog(value = "'停用/启用应用,应用id:' + #reqVO?.id", optype = OptypeEnum.UPDATE)
    @Transactional
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<Boolean> isUseable(@RequestBody PublicIdReqVO reqVO) {
        return this.ok(crudService.isUseable(reqVO.getId()));
    }
}
