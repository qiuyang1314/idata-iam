package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyaud.fzhx.binder.annotation.BindResult;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.common.utils.TreeUtils;
import com.zyaud.fzhx.core.mybatis.QueryBuilder;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.iam.annotation.PreAuthorize;
import com.zyaud.fzhx.log.annotation.SysLog;
import com.zyaud.fzhx.log.enums.OptypeEnum;
import com.zyaud.idata.iam.biz.model.dto.RoleUserCodeCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.RoleUserCodeDeleteDTO;
import com.zyaud.idata.iam.biz.model.dto.RoleUserCodePageDTO;
import com.zyaud.idata.iam.biz.model.dto.RoleUserCodeUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.*;
import com.zyaud.idata.iam.biz.model.vo.*;
import com.zyaud.idata.iam.biz.service.*;
import com.zyaud.idata.iam.common.errorcode.AccountMngErrorEnum;
import com.zyaud.idata.iam.common.errorcode.AppMngErrorEnum;
import com.zyaud.idata.iam.common.errorcode.MenuMngErrorEnum;
import com.zyaud.idata.iam.common.errorcode.RoleMngErrorEnum;
import com.zyaud.idata.iam.common.utils.Constants;
import com.zyaud.idata.iam.common.vo.PublicAppIdReqVO;
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
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * ???????????? ???????????????
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
@Slf4j
@RestController
@Api(tags = "????????????")
@RequestMapping("/system/role-user-code")
public class RoleUserCodeController extends SuperController {

    @Resource
    private IRoleUserCodeService crudService;
    @Resource
    private IUserCodeService userCodeService;
    @Resource
    private IRoleService roleService;
    @Resource
    private IAppService appService;
    @Resource
    private IMenuService menuService;
    @Resource
    private IRoleMenuService roleMenuService;

    private void validAppRole(String appId, String roleId) {
        Role role = roleService.getById(roleId);
        BizAssert.isTrue(ObjectUtil.isNotEmpty(role), RoleMngErrorEnum.ROLE_IS_NOT_EXIST);
        BizAssert.isTrue(role.getAppId().equalsIgnoreCase(appId), RoleMngErrorEnum.APP_ROLE_NOT_EXIST);
    }

    @ApiOperation(value = "?????????????????????")
    @PostMapping(value = "/create")
    @SysLog(value = "'?????????????????????,??????id:' + #createDTO.appId + ',??????id:' + #createDTO.roleId + ',??????id:' + #createDTO.userCodeId", optype = OptypeEnum.INSERT)
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<Boolean> create(@RequestBody @Validated RoleUserCodeCreateDTO createDTO) {
        validAppRole(createDTO.getAppId(), createDTO.getRoleId());
        QueryWrapper<RoleUserCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RoleUserCode.APPID, createDTO.getAppId())
                .eq(RoleUserCode.ROLEID, createDTO.getRoleId())
                .eq(RoleUserCode.USERCODEID, createDTO.getUserCodeId());
        RoleUserCode one = this.crudService.getOne(queryWrapper);
        if (ObjectUtil.isNotEmpty(one)) {
            return fail(AccountMngErrorEnum.ROLE_IS_BIND);
        }
        RoleUserCode entity = BeanUtil.toBean(createDTO, RoleUserCode.class);
        return ok(crudService.save(entity));
    }

    @ApiOperation(value = "??????????????????????????????")
    @PostMapping(value = "/delete")
    @SysLog(value = "'??????????????????????????????,??????id:' + #deleteDTO.appId + ',??????id:' + #deleteDTO.roleId + ',??????id:' + #deleteDTO.userCodeId", optype = OptypeEnum.DELETE)
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> delete(@RequestBody @Validated RoleUserCodeDeleteDTO deleteDTO) {
        Wrapper<RoleUserCode> query = QueryBuilder.queryWrapper(deleteDTO);
        RoleUserCode one = crudService.getOne(query);
        if (ObjectUtil.isEmpty(one)) {
            return fail(AccountMngErrorEnum.ROLE_IS_NOT_EXIST_IN_THIS_USER_CODE);
        }

        // ???????????????????????????????????????????????????
        if (StrUtil.isNotBlank(deleteDTO.getUserCodeId())) {
            UserCode userCode = userCodeService.getById(deleteDTO.getUserCodeId());
            String defaultRoles = userCode.getDefaultRoles();
            if (StrUtil.isNotBlank(defaultRoles)) {
                String newDefaultRoles = defaultRoles.replace(deleteDTO.getRoleId(), "");
                if (!defaultRoles.equals(newDefaultRoles)) {
                    userCode.setDefaultRoles(newDefaultRoles);
                    userCodeService.updateById(userCode);
                }
            }
        }

        return ok(crudService.remove(query));
    }

    @ApiOperation(value = "??????????????????????????????")
    @PostMapping(value = "/update")
    @SysLog(value = "'??????????????????????????????,??????id:' + #updateDTO.appId + ',??????id:' + #updateDTO.roleId + ',??????id:' + #updateDTO.userCodeId", optype = OptypeEnum.DELETE)
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<Boolean> update(@RequestBody @Validated RoleUserCodeUpdateDTO updateDTO) {
        RoleUserCode entity = BeanUtil.toBean(updateDTO, RoleUserCode.class);
        validAppRole(updateDTO.getAppId(), updateDTO.getRoleId());
        return ok(crudService.updateById(entity));
    }

    @ApiOperation(value = "??????????????????")
    @PostMapping(value = "/get")
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<RoleUserCode> get(@RequestBody @Validated PublicIdReqVO reqVO) {
        return ok(crudService.getById(reqVO.getId()));
    }

    @ApiOperation(value = "??????????????????")
    @PostMapping(value = "/page")
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<IPage<RoleUserCode>> page(@RequestBody RoleUserCodePageDTO pageDTO) {
        IPage<RoleUserCode> page = pageDTO.getPage();
        Wrapper<RoleUserCode> query = QueryBuilder.queryWrapper(pageDTO);
        return ok(crudService.page(page, query));
    }


    @ApiOperation("?????????????????????????????????????????????")
    @PostMapping({"/adminUsers"})
    @BindResult
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<List<UserCodeVO>> adminUsers() {
        //?????????????????????
        QueryWrapper<UserCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne(UserCode.USERID, null).or()
                .ne(UserCode.USERID, "");
        List<UserCode> list = userCodeService.list(queryWrapper);
        List<UserCodeVO> userCodeVOS = new ArrayList<>();
        list.forEach(t -> userCodeVOS.add(BeanUtil.toBean(t, UserCodeVO.class)));
        return ok(userCodeVOS);
    }

    @ApiOperation("?????????????????????????????????????????????")
    @PostMapping({"/adminList"})
    @BindResult
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<List<RoleUserCodeVO>> adminList(@RequestBody AdminListReqVO reqVO) {
        List<RoleUserCodeVO> userCodeVOS = this.crudService.getUserCodes(Constants.IAM_CODE, reqVO.getRoleName());
        return ok(userCodeVOS);
    }

    @ApiOperation("?????????????????????????????????????????????")
    @PostMapping({"/addAdmin"})
    @SysLog(value = "'???????????????,?????????:' + #addAdminVO.roleName + ',??????id:' + #addAdminVO.userCodeIds", optype = OptypeEnum.INSERT)
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<Boolean> addAdmin(@RequestBody @Validated AddAdminVO addAdminVO) {
        if (ObjectUtil.isEmpty(addAdminVO.getUserCodeIds())) {
            return fail(AccountMngErrorEnum.USER_CODE_IS_NOT_EXIST.getCode(), "???????????????");
        }
        App app = appService.getAppByAppCode(Constants.IAM_CODE);
        if (ObjectUtil.isEmpty(app)) {
            BizAssert.fail(AppMngErrorEnum.APP_IS_NULL);
        }
        //??????
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Role.ROLENAME, addAdminVO.getRoleName())
                .eq(Role.APPID, app.getId());
        Role role = roleService.getOne(queryWrapper);
        if (ObjectUtil.isEmpty(role)) {
            BizAssert.fail(RoleMngErrorEnum.ROLE_IS_NOT_EXIST.getCode(),
                    addAdminVO.getRoleName() + RoleMngErrorEnum.ROLE_IS_NOT_EXIST.getMessage());
        }

        //?????????????????????????????????????????????
        this.crudService.verifyRoleUserCode(app.getId(), addAdminVO.getUserCodeIds());

        List<RoleUserCode> roleUserCodes = new ArrayList<>();
        for (String userCodeId : addAdminVO.getUserCodeIds()) {
            RoleUserCode roleUserCode = new RoleUserCode();
            roleUserCode.setRoleId(role.getId())
                    .setAppId(app.getId())
                    .setUserCodeId(userCodeId);
            roleUserCodes.add(roleUserCode);
        }
        return ok(crudService.saveBatch(roleUserCodes));
    }

    @ApiOperation("?????????????????????????????????????????????")
    @PostMapping({"/deleteAdmin"})
    @SysLog(value = "'???????????????,??????id:' + #deleteAdminVO.appId + ',??????id:' + #deleteAdminVO.roleId + ',??????id:' + #deleteAdminVO.userCodeId", optype = OptypeEnum.DELETE)
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<Boolean> deleteAdmin(@RequestBody @Validated DeleteAdminVO deleteAdminVO) {
        QueryWrapper<RoleUserCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RoleUserCode.APPID, deleteAdminVO.getAppId())
                .eq(RoleUserCode.ROLEID, deleteAdminVO.getRoleId())
                .eq(RoleUserCode.USERCODEID, deleteAdminVO.getUserCodeId());
        if (ObjectUtil.isEmpty(deleteAdminVO)) {
            return fail(AccountMngErrorEnum.USER_CODE_IS_NOT_EXIST);
        }
        return ok(this.crudService.remove(queryWrapper));
    }

    @ApiOperation("????????????????????????????????????????????????")
    @PostMapping({"/appMenu"})
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<List<MenuTreeVO>> appMenu(@Validated @RequestBody PublicAppIdReqVO reqVO) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Menu.APPID, reqVO.getAppId());
        List<Menu> list = menuService.list(queryWrapper);
        List<MenuTreeVO> menuTreeVOS = new ArrayList<>();
        list.forEach(t -> menuTreeVOS.add(BeanUtil.toBean(t, MenuTreeVO.class)));
        return ok(TreeUtils.buildByRecursive(menuTreeVOS, Constants.ROOTID));
    }

    @ApiOperation("????????????????????????????????????????????????????????????")
    @PostMapping({"/appRoleMenu"})
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<Set<String>> appRoleMenu(@RequestBody @Validated AppRoleMenuVO appRoleMenuVO) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RoleMenu.APPID, appRoleMenuVO.getAppId())
                .eq(RoleMenu.ROLEID, appRoleMenuVO.getRoleId());
        List<RoleMenu> roleMenus = roleMenuService.list(queryWrapper);
        if (ObjectUtil.isEmpty(roleMenus)) {
            return ok(new HashSet<>());
        }
        Set<String> roleMenuSet = roleMenus.stream().map(t -> t.getMenuId()).collect(Collectors.toSet());
        return ok(roleMenuSet);
    }

    @ApiOperation("??????????????????????????????????????????????????????")
    @PostMapping({"/addOrDelRoleMenu"})
    @SysLog(value = "'????????????????????????,??????id:' + #addOrDelRoleMenuVO?.appId + ',??????id:' + #addOrDelRoleMenuVO?.roleId + ',??????id:' + #addOrDelRoleMenuVO?.menuIds", optype = OptypeEnum.UPDATE)
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result addOrDelRoleMenu(@Validated @RequestBody AddOrDelRoleMenuVO addOrDelRoleMenuVO) {
        if (ObjectUtil.isEmpty(addOrDelRoleMenuVO.getMenuIds())) {
            return fail(MenuMngErrorEnum.MENU_ID_CANNOT_BE_NULL.getCode(), "??????????????????");
        }
        //???????????????????????????
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RoleMenu.APPID, addOrDelRoleMenuVO.getAppId())
                .eq(RoleMenu.ROLEID, addOrDelRoleMenuVO.getRoleId());
        List<RoleMenu> list = roleMenuService.list(queryWrapper);
        //?????????  ????????????
        if (ObjectUtil.isEmpty(list)) {
            List<RoleMenu> roleMenus = new ArrayList<>();
            for (String menuId : addOrDelRoleMenuVO.getMenuIds()) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setAppId(addOrDelRoleMenuVO.getAppId())
                        .setRoleId(addOrDelRoleMenuVO.getRoleId())
                        .setMenuId(menuId);
                roleMenus.add(roleMenu);
            }
            roleMenuService.saveBatch(roleMenus);
        }
        //???????????????????????????????????????
        else {
            //??????????????????
            Set<String> collect = list.stream().map(t -> t.getMenuId()).collect(Collectors.toSet());
            Set<String> request = new HashSet<>();
            request = addOrDelRoleMenuVO.getMenuIds();
            if (request.size() > collect.size()) {
                //??????
                addOrDelRoleMenuVO.getMenuIds().removeAll(collect);
                if (ObjectUtil.isNotEmpty(addOrDelRoleMenuVO.getMenuIds())) {
                    List<RoleMenu> roleMenus = new ArrayList<>();
                    for (String menuId : addOrDelRoleMenuVO.getMenuIds()) {
                        RoleMenu roleMenu = new RoleMenu();
                        roleMenu.setAppId(addOrDelRoleMenuVO.getAppId())
                                .setRoleId(addOrDelRoleMenuVO.getRoleId())
                                .setMenuId(menuId);
                        roleMenus.add(roleMenu);
                    }
                    roleMenuService.saveBatch(roleMenus);
                }
            } else {
                //??????
                collect.removeAll(request);
                if (ObjectUtil.isNotEmpty(collect)) {
                    QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
                    roleMenuQueryWrapper.eq(RoleMenu.APPID, addOrDelRoleMenuVO.getAppId())
                            .eq(RoleMenu.ROLEID, addOrDelRoleMenuVO.getRoleId())
                            .in(RoleMenu.MENUID, collect);
                    roleMenuService.remove(roleMenuQueryWrapper);
                }
            }
        }
        return ok();
    }


    //??????????????????
    @ApiOperation("???????????????????????????????????????")
    @PostMapping({"/rolelist"})
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<Set<RouAndAppVO>> rolelist(@RequestBody @Validated IdVO id) {
        QueryWrapper<RoleUserCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RoleUserCode.USERCODEID, id.getId());
        List<RoleUserCode> rserCodeList = crudService.list(queryWrapper);
        List<RouAndAppVO> rouAndAppVOS = new ArrayList<>();
        if (rserCodeList.size() > 0) {
            //??????id
            List<String> roleIds = rserCodeList.stream().map(t -> t.getRoleId()).collect(Collectors.toList());
            List<Role> roles = new ArrayList<>();
            if (roleIds.size() > 0) {
                roles = roleService.listByIds(roleIds);
            }
            //??????
            List<String> appIds = rserCodeList.stream().map(t -> t.getAppId()).collect(Collectors.toList());
            List<App> apps = new ArrayList<>();
            if (appIds.size() > 0) {
                apps = appService.listByIds(appIds);
            }
            for (RoleUserCode roleUserCode : rserCodeList) {
                RouAndAppVO rouAndAppVO = BeanUtil.toBean(roleUserCode, RouAndAppVO.class);
                if (roles.size() > 0) {
                    Optional<Role> role = roles.stream().filter(t -> t.getId().equals(roleUserCode.getRoleId())).findFirst();
                    role.ifPresent(t -> rouAndAppVO.setRoleName(t.getRoleName()));
                }
                if (apps.size() > 0) {
                    Optional<App> app = apps.stream().filter(t -> t.getId().equals(roleUserCode.getAppId())).findFirst();
                    app.ifPresent(t -> rouAndAppVO.setAppName(t.getAppName()));
                }
                rouAndAppVOS.add(rouAndAppVO);
            }
        }
        return ok(new HashSet<>(rouAndAppVOS));
    }


}
