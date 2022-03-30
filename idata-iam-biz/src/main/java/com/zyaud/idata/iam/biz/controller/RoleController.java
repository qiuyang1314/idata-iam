package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyaud.fzhx.binder.annotation.BindResult;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.core.mybatis.QueryBuilder;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.iam.annotation.PreAuthorize;
import com.zyaud.fzhx.log.annotation.SysLog;
import com.zyaud.fzhx.log.enums.OptypeEnum;
import com.zyaud.idata.iam.biz.model.dto.RoleCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.RolePageDTO;
import com.zyaud.idata.iam.biz.model.dto.RoleUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.Role;
import com.zyaud.idata.iam.biz.model.vo.RoleVO;
import com.zyaud.idata.iam.biz.service.IRoleService;
import com.zyaud.idata.iam.common.errorcode.RoleMngErrorEnum;
import com.zyaud.idata.iam.common.utils.Constants;
import com.zyaud.idata.iam.common.vo.PublicAppIdReqVO;
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
import java.util.stream.Collectors;


/**
 * <p>
 * 系统应用角色 前端控制器
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
@Slf4j
@RestController
@Api(tags = "角色模块")
@RequestMapping("/system/role")
public class RoleController extends SuperController {

    @Resource
    private IRoleService crudService;

    @ApiOperation(value = "新增角色")
    @PostMapping(value = "/create")
    @SysLog(value = "'新增角色,角色名:' + #createDTO?.roleName + ',应用id:' +#createDTO?.appId", optype = OptypeEnum.INSERT)
    @PreAuthorize(value = "hasPermission('system:role:create')", msg = "您没有‘新增角色’的权限")
    public Result<Boolean> create(@Validated @RequestBody RoleCreateDTO createDTO) {
        Long roleNameInt = crudService.verifyRoleName(null, createDTO.getAppId(), createDTO.getRoleName());
        BizAssert.isTrue(roleNameInt <= 0, RoleMngErrorEnum.ROLE_NAME_IS_EXIST);

        Long roleCodeInt = crudService.verifyRoleCode(null, createDTO.getAppId(), createDTO.getRoleCode());
        BizAssert.isTrue(roleCodeInt <= 0, RoleMngErrorEnum.ROLE_CODE_IS_EXIST);
        Role entity = BeanUtil.toBean(createDTO, Role.class);
        return ok(crudService.save(entity));
    }

    @ApiOperation("删除角色")
    @PostMapping(value = {"/delete"})
    @SysLog(value = "'删除角色,角色id:' + #reqVO?.ids", optype = OptypeEnum.DELETE)
    @PreAuthorize(value = "hasPermission('system:role:delete')", msg = "您没有‘删除角色’的权限")
    public Result<Boolean> delete(@Validated @RequestBody PublicIdListReqVO reqVO) {
        List<Role> roles = this.crudService.listByIds(reqVO.getIds());
        BizAssert.isTrue(!(roles.size() == 0), RoleMngErrorEnum.ROLE_IS_NOT_EXIST);

        List<String> collect = roles.stream()
                .filter(t -> t.getRoleType().equals(Constants.BUILT_IN))
                .map(t -> t.getRoleName()).collect(Collectors.toList());
        if (collect.size() > 0) {
            String roleName = "";
            for (String s : collect) {
                roleName += s + ",";
            }
            return fail(RoleMngErrorEnum.BUILT_IN_ROLE_CANNOT_DELETE.getCode(),roleName + "，不允许删除");
        }
        return this.ok(this.crudService.removeByIds(reqVO.getIds()));
    }

    @ApiOperation("修改角色")
    @PostMapping(value = {"/update"})
    @SysLog(value = "'修改角色,角色id:' + #updateDTO?.id + ',角色名:' + #updateDTO?.roleName + ',应用id:' + #updateDTO?.appId", optype = OptypeEnum.UPDATE)
    @PreAuthorize(value = "hasPermission('system:role:get')", msg = "您没有‘编辑角色’的权限")
    public Result<Boolean> update(@Validated @RequestBody RoleUpdateDTO updateDTO) {

        Long roleNameInt = crudService.verifyRoleName(updateDTO.getId(), updateDTO.getAppId(), updateDTO.getRoleName());
        BizAssert.isTrue(roleNameInt <= 0, RoleMngErrorEnum.ROLE_NAME_IS_EXIST);

        Long roleCodeInt = crudService.verifyRoleCode(updateDTO.getId(), updateDTO.getAppId(), updateDTO.getRoleCode());
        BizAssert.isTrue(roleCodeInt <= 0, RoleMngErrorEnum.ROLE_CODE_IS_EXIST);

        Role role = this.crudService.getById(updateDTO.getId());
        BizAssert.isTrue(ObjectUtil.isNotEmpty(role), RoleMngErrorEnum.ROLE_IS_NOT_EXIST);

        BizAssert.isTrue(!(role.getRoleType().equals(Constants.BUILT_IN)), RoleMngErrorEnum.BUILT_IN_ROLE_CANNOT_UPDATE);

        Role entity = BeanUtil.toBean(updateDTO, Role.class);
        return this.ok(this.crudService.updateById(entity));
    }

    @ApiOperation(value = "查询角色")
    @PostMapping(value = "/get")
    @SysLog(value = "'查询角色,角色id:' + #reqVO?.id", optype = OptypeEnum.SELECT)
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<Role> get(@Validated @RequestBody PublicIdReqVO reqVO) {
        return ok(crudService.getById(reqVO.getId()));
    }

    @ApiOperation("分页列表查询")
    @PostMapping({"/page"})
    @BindResult
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<IPage<Role>> page(@RequestBody RolePageDTO pageDTO) {
        QueryWrapper<Role> query = QueryBuilder.queryWrapper(pageDTO);
        IPage<Role> page = this.crudService.page(pageDTO.getPage(), query);
        IPage iPage = new Page(page.getCurrent(), page.getSize(), page.getTotal());
        List<RoleVO> roleVOS = new ArrayList<>();
        if (page.getRecords().size() > 0) {
            page.getRecords().forEach(t -> roleVOS.add(BeanUtil.toBean(t, RoleVO.class)
                    .setIsuseable(Constants.ART_USING.equals(t.getUseable()) ? true : false)));
        }
        iPage.setRecords(roleVOS);
        return this.ok(iPage);
    }

    @ApiOperation("角色下拉列表")
    @PostMapping({"/getRoleList"})
    @BindResult
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<List<Role>> getRoleList(@Validated @RequestBody PublicAppIdReqVO reqVO) {
        List<Role> roleList = crudService.findRole(reqVO.getAppId(), null, null,
                Constants.USER_DEFINED, Constants.ART_USING);
        return this.ok(roleList);
    }

    @ApiOperation("角色停用/启用")
    @PostMapping({"/isUseable"})
    @SysLog(value = "'停用/启用角色,角色id:' + #reqVO?.id", optype = OptypeEnum.UPDATE)
    @Transactional
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result isUseable(@Validated @RequestBody PublicIdReqVO reqVO) {
        Role role = this.crudService.getById(reqVO.getId());
        if (ObjectUtil.isEmpty(role)) {
            return fail(RoleMngErrorEnum.ROLE_IS_NOT_EXIST);
        }
        if (Constants.BLOCK_UP.equals(role.getUseable())) {
            role.setUseable(Constants.ART_USING);
        } else {
            role.setUseable(Constants.BLOCK_UP);
        }
        this.crudService.updateById(role);
        return this.ok();
    }

}
