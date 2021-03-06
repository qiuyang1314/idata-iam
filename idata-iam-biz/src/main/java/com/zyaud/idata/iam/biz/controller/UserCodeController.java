package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyaud.fzhx.binder.annotation.BindResult;
import com.zyaud.fzhx.cache.service.CacheService;
import com.zyaud.fzhx.common.enums.ResultStatus;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.common.utils.JsonUtils;
import com.zyaud.fzhx.common.utils.TreeUtils;
import com.zyaud.fzhx.core.model.IdEntity;
import com.zyaud.fzhx.core.mybatis.QueryBuilder;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.iam.annotation.PreAuthorize;
import com.zyaud.fzhx.iam.core.FzhxIam;
import com.zyaud.fzhx.iam.core.FzhxIamUser;
import com.zyaud.fzhx.iam.token.Token;
import com.zyaud.fzhx.iam.token.TokenStore;
import com.zyaud.fzhx.log.annotation.SysLog;
import com.zyaud.fzhx.log.enums.OptypeEnum;
import com.zyaud.idata.iam.biz.model.dto.UserCodeCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.UserCodePageDTO;
import com.zyaud.idata.iam.biz.model.dto.UserCodeUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.*;
import com.zyaud.idata.iam.biz.model.vo.*;
import com.zyaud.idata.iam.biz.service.*;
import com.zyaud.idata.iam.biz.strategy.UserLoginStrategy;
import com.zyaud.idata.iam.biz.utils.SyncAppframe;
import com.zyaud.idata.iam.common.errorcode.AccountMngErrorEnum;
import com.zyaud.idata.iam.common.errorcode.AppMngErrorEnum;
import com.zyaud.idata.iam.common.errorcode.MenuMngErrorEnum;
import com.zyaud.idata.iam.common.errorcode.UserMngErrorEnum;
import com.zyaud.idata.iam.common.utils.Constants;
import com.zyaud.idata.iam.common.utils.MngUtil;
import com.zyaud.idata.iam.common.utils.PasswordEncryptor;
import com.zyaud.idata.iam.common.utils.RandomImgCodeUtil;
import com.zyaud.idata.iam.common.vo.PublicIdListReqVO;
import com.zyaud.idata.iam.common.vo.PublicIdReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Date;
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
@RequestMapping("/system/user-code")
public class UserCodeController extends SuperController {
    @Resource
    private IUserService userService;
    @Resource
    private IUserCodeService crudService;
    @Resource
    private IRoleUserCodeService roleUserCodeService;
    @Resource
    private IAppService appService;
    @Resource
    private IRoleService roleService;
    @Resource
    private UserLoginStrategy userLoginStrategy;
    @Resource
    private TokenStore tokenStore;
    @Resource
    private IOfficeService officeService;
    @Resource
    private IConfigService configService;
    @Resource
    private ILoginService loginService;
    @Resource
    private CacheService cacheService;
    @Resource
    private SyncAppframe syncAppframe;

    @Value("${spring.captchaComplexity}")
    private Boolean captchaComplexity;

    @Value("${spring.captchaByKey}")
    private Boolean captchaByKey;


    @Value("${spring.captchaBackColor:0xF5CCD3}")
    private Integer captchaBackColor;


    @Value("${spring.captchaFontColor:0x8D7073}")
    private Integer captchaFontColor;


    @ApiOperation("????????????")
    @PostMapping(value = {"/create"})
    @SysLog(value = "'????????????,?????????:' + #createDTO.loginName", optype = OptypeEnum.INSERT)
    @PreAuthorize(value = "hasPermission('system:user-code:create')", msg = "????????????????????????????????????")
    public Result<Boolean> create(@Validated @RequestBody UserCodeCreateDTO createDTO) {
        if (crudService.verify(createDTO.getLoginName(), null)) {
            return fail(AccountMngErrorEnum.USER_CODE_IS_EXIST);
        }
        UserCode entity = BeanUtil.toBean(createDTO, UserCode.class);
        String saltValue = RandomUtil.randomNumbers(Constants.USERCODE_RANDOM_NUMBER);
        PasswordEncryptor encoderMd51 = new PasswordEncryptor(saltValue, "sha-256");
        entity.setPasswd(encoderMd51.encode(createDTO.getPasswd()))
                .setSalt(saltValue)
                .setPwdUpdateTime(new Date(System.currentTimeMillis()));
        return this.ok(this.crudService.save(entity));
    }

    @ApiOperation(value = "????????????")
    @PostMapping(value = "/delete")
    @SysLog(value = "'????????????,??????id:' + #ids", optype = OptypeEnum.DELETE)
    //@PreAuthorize(value = "hasPermission('system:user-code:delete')", msg = "????????????????????????????????????")
    public Result<Boolean> delete(@Validated @RequestBody PublicIdListReqVO reqVO) {
        //????????????appframe??????
        syncAppframe.remoteDeleteBindUser(reqVO);
        return ok(crudService.removeByIds(reqVO.getIds()));
    }

    @ApiOperation("????????????")
    @PostMapping(value = {"/update"})
    @SysLog(value = "'????????????,??????id:' + #updateDTO?.id", optype = OptypeEnum.UPDATE)
    //@PreAuthorize(value = "hasPermission('system:user-code:get')", msg = "????????????????????????????????????")
    public Result<Boolean> update(@Validated @RequestBody UserCodeUpdateDTO updateDTO) {
        if (crudService.verify(updateDTO.getLoginName(), updateDTO.getId())) {
            return fail(AccountMngErrorEnum.USER_CODE_IS_EXIST);
        }
        UserCode entity = BeanUtil.toBean(updateDTO, UserCode.class);
        //????????????appframe??????
        syncAppframe.remoteUpdateUserCode(updateDTO);
        return this.ok(this.crudService.updateById(entity));
    }

    @ApiOperation("?????????????????????")
    @PostMapping(value = {"/updatePassword"})
    @SysLog(value = "'?????????????????????,??????id:' + #updatePasswordVO?.id", optype = OptypeEnum.UPDATE)
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<Boolean> updatePassword(@Validated @RequestBody UpdatePasswordVO updatePasswordVO) {
        UserCode userCode = crudService.getById(updatePasswordVO.getId());
        BizAssert.isNotEmpty(userCode, UserMngErrorEnum.USERCODE_NOTEXIST);
        String saltValue = RandomUtil.randomNumbers(Constants.USERCODE_RANDOM_NUMBER);
        PasswordEncryptor encoderMd51 = new PasswordEncryptor(saltValue, "sha-256");
        userCode.setPasswd(encoderMd51.encode(updatePasswordVO.getPassword()))
                .setSalt(saltValue)
                .setPwdUpdateTime(new Date(System.currentTimeMillis()));
        return this.ok(this.crudService.updateById(userCode));
    }

    @ApiOperation(value = "????????????")
    @PostMapping(value = "/get")
    @SysLog(value = "'????????????,??????id:' + #reqVO?.id", optype = OptypeEnum.SELECT)
    public Result<UserCode> get(@RequestBody @Validated PublicIdReqVO reqVO) {
        return ok(crudService.getById(reqVO.getId()));
    }


    @ApiOperation(value = "??????????????????????????????")
    @PostMapping(value = "/getUserBaseInfo")
    @SysLog(value = "??????????????????????????????", optype = OptypeEnum.SELECT)
    @BindResult
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<UserBaseInfoRspVO> getUserBaseInfo() {
        return ok(crudService.getUserBaseInfo());
    }

    @ApiOperation("??????????????????")
    @PostMapping({"/page"})
    @BindResult
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<IPage<UserCode>> page(@RequestBody UserCodePageDTO pageDTO) {
        Wrapper<UserCode> query = QueryBuilder.queryWrapper(pageDTO);
        IPage<UserCode> page = this.crudService.page(pageDTO.getPage(), query);
        IPage iPage = new Page(page.getCurrent(), page.getSize(), page.getTotal());
        List<UserCodeVO> userCodeVOS = new ArrayList<>();
        page.getRecords().forEach(t -> userCodeVOS.add(BeanUtil.toBean(t, UserCodeVO.class)));
        iPage.setRecords(userCodeVOS);
        return this.ok(iPage);
    }

    @ApiOperation("??????????????????")
    @PostMapping({"/changePassword"})
    @SysLog(value = "'??????????????????'", optype = OptypeEnum.UPDATE)
    public Result<Boolean> changePassword(@Validated @RequestBody ChangePasswordVO changePasswordVO) {
        if (!changePasswordVO.getNewPassword().equals(changePasswordVO.getConfirmPassword())) {
            return fail(AccountMngErrorEnum.TWO_PWD_IS_NOT_EQUAL_UPDATE_PWD_FAIL.getCode(), "???????????????????????????");
        }
        //???????????????
        String userId = FzhxIam.getUser().getUserId();
        UserCode userCode = crudService.getById(userId);
        if (ObjectUtil.isEmpty(userCode)) {
            return fail(AccountMngErrorEnum.USER_CODE_IS_NOT_EXIST);
        }
        PasswordEncryptor encoderMd51 = new PasswordEncryptor(userCode.getSalt(), "sha-256");
        if (!encoderMd51.encode(changePasswordVO.getOldPassword()).equals(userCode.getPasswd())) {
            return fail(AccountMngErrorEnum.OLD_PWD_IS_NOT_CORRECT_UPDATE_PWD_FAIL.getCode(), "???????????????");
        }
        //???????????????
        String saltValue = RandomUtil.randomNumbers(Constants.USERCODE_RANDOM_NUMBER);
        PasswordEncryptor encoder = new PasswordEncryptor(saltValue, "sha-256");
        userCode.setPasswd(encoder.encode(changePasswordVO.getNewPassword()))
                .setSalt(saltValue)
                .setPwdUpdateTime(new Date(System.currentTimeMillis()));
        return ok(crudService.updateById(userCode));
    }

    @ApiOperation("????????????")
    @PostMapping({"/bindingUser"})
    @SysLog(value = "'????????????:??????id' + #bindingUserVO?.userId + ',??????id' + #bindingUserVO?.userCodeId ", optype = OptypeEnum.UPDATE)
    //@PreAuthorize(value = "hasPermission('system:user-code:bindingUser')", msg = "????????????????????????????????????")
    public Result<Boolean> bindingUser(@Validated @RequestBody BindingUserVO bindingUserVO) {
        UserCode userCode = this.crudService.getById(bindingUserVO.getUserCodeId());
        if (ObjectUtil.isEmpty(userCode)) {
            return fail(AccountMngErrorEnum.USER_CODE_IS_NOT_EXIST);
        }
        User user = userService.getById(bindingUserVO.getUserId());
        if (ObjectUtil.isEmpty(user)) {
            return fail(AccountMngErrorEnum.USER_IS_NOT_EXIST);
        }
        //????????????????????????
        QueryWrapper<UserCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", bindingUserVO.getUserId());
        UserCode one = this.crudService.getOne(queryWrapper);
        if (ObjectUtil.isNotEmpty(one)) {
            return fail(AccountMngErrorEnum.ALREADY_BIND_USER_CODE_BIND_USER.getCode(), "????????????????????????????????????");
        }
        //??????????????????
        userCode.setUserId(bindingUserVO.getUserId());
        //?????????????????????appframe
        syncAppframe.remoteAddUser(userCode, user);
        return ok(this.crudService.updateById(userCode));
    }

    @ApiOperation(value = "?????????????????????appframe")
    @PostMapping(value = "/syncUserAppframe")
    @SysLog(value = "'???????????????appframe'")
    //@PreAuthorize(value = "hasPermission('system:user-code:syncUserAppframe')", msg = "???????????????????????????appframe????????????")
    public Result<Boolean> syncAppframe() {
        //????????????appframe??????
        syncAppframe.remoteBatchUser();
        return ok();
    }

    @ApiOperation("????????????")
    @PostMapping({"/unbindUser"})
    @SysLog(value = "'????????????:??????id' + #bindingUserVO?.userId + ',??????id' + #bindingUserVO?.userCodeId ", optype = OptypeEnum.UPDATE)
    //@PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<Boolean> unbindUser(@Validated @RequestBody BindingUserVO bindingUserVO) {
        UserCode userCode = this.crudService.getById(bindingUserVO.getUserCodeId());
        if (ObjectUtil.isEmpty(userCode)) {
            return fail(AccountMngErrorEnum.USER_CODE_IS_NOT_EXIST);
        }
        //?????????????????????appframe
        PublicIdListReqVO codes = new PublicIdListReqVO();
        List<String> codeIds = new ArrayList<>();
        codeIds.add(userCode.getId());
        codes.setIds(codeIds);
        syncAppframe.remoteDeleteBindUser(codes);

        userCode.setUserId("");
        return ok(crudService.updateById(userCode));
    }

    @ApiOperation("??????????????????????????????")
    @PostMapping({"/getIrslUsers"})
    @BindResult
    @PreAuthorize(value = "permitAll()")
    public Result<IPage<UserCodeVO>> getIrslUsers(@RequestBody GetIrslUsersVO getIrslUsersVO) {
        QueryWrapper<UserCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(getIrslUsersVO.getType()), UserCode.TYPE, getIrslUsersVO.getType())
                .like(ObjectUtil.isNotEmpty(getIrslUsersVO.getLoginName()), UserCode.LOGINNAME, getIrslUsersVO.getLoginName())
                .eq(ObjectUtil.isNotEmpty(getIrslUsersVO.getStatus()), UserCode.STATUS, getIrslUsersVO.getStatus());
        IPage<UserCode> page = crudService.page(getIrslUsersVO.getPage(), queryWrapper);
        IPage<UserCodeVO> iPage = new Page(page.getCurrent(), page.getSize(), page.getTotal());
        if (ObjectUtil.isNotEmpty(iPage.getRecords())) {
            List<UserCodeVO> userCodeVOS = new ArrayList<>();
            page.getRecords().forEach(t -> userCodeVOS.add(BeanUtil.toBean(t, UserCodeVO.class)));
            //??????id
            Set<String> collect = userCodeVOS.stream()
                    .filter(t -> ObjectUtil.isNotEmpty(t.getUserId()))
                    .map(t -> t.getUserId()).collect(Collectors.toSet());
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.in(IdEntity.ID, collect);
            List<User> users = userService.list(userQueryWrapper);
            for (UserCodeVO userCode : userCodeVOS) {
                Optional<User> first = users.stream().filter(t -> t.getId().equals(userCode.getUserId())).findFirst();
                first.ifPresent(t -> userCode.setUserName(t.getName()).setOfficeId(t.getOfficeId()));
            }
            iPage.setRecords(userCodeVOS);
        }
        return ok(iPage);
    }

    @ApiOperation("??????")
    @PostMapping({"/login"})
    @SysLog(value = "??????", optype = OptypeEnum.SELECT)
    public Result<LoginRspVO> login(@RequestBody LoginVO loginVO) {
        try {
            AuthParam authParam = BeanUtil.toBean(loginVO, AuthParam.class);
            LoginRspVO login = loginService.login(authParam);
            login.setLoginName(login.getUserName()).setSuper(false);
            return ok(login);
        } catch (Exception e) {
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    @ApiOperation("??????")
    @SysLog(value = "??????", optype = OptypeEnum.SELECT)
    @PostMapping("/logout")
    public Result logout() {
        FzhxIamUser user = FzhxIam.getUser();
        if (ObjectUtil.isEmpty(user)) {
            return ok();
        }
        String token = FzhxIam.getToken();
        tokenStore.removeToken(user.getUserId(), token);
        return ok();
    }

    @ApiOperation("??????access_token")
    @PostMapping("/refreshToken")
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<Map<String, Object>> refreshToken() {
        FzhxIamUser user = FzhxIam.getUser();
        BizAssert.isNotEmpty(user, ResultStatus.JWT_TOKEN_EXPIRED);
        //??????tno
        String tno = String.valueOf(DateUtil.current());
        user.getExt().put("tno", tno);
        cacheService.setExpire(Constants.IAM_LOGIN,
                Constants.USER_KEY + user.getName(), user, Constants.ACCESS_TOKEN_EXPIRE);
        FzhxIamUser tokenUser = BeanUtil.toBean(user, FzhxIamUser.class);
        //??????????????????????????????token??????
        tokenUser.setScopes(null).setRoles(null).setPermissions(null);
        Token accessToken = userLoginStrategy.getToken(tokenUser, Constants.ACCESS_TOKEN_EXPIRE);
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", accessToken.getValue());
        map.put("expirationTime", DateUtil.formatDateTime(accessToken.getExpireTime()));
        return ok(map);
    }

    @ApiOperation("??????????????????")
    @PostMapping(value = "/menuTreeByLoginUserRole")
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<List<MenuTreeVO>> menuTreeByLoginUserRole(@Validated @RequestBody UserCodeRoleReqVO roleReqVO) {
        App app = appService.getAppByAppCode(roleReqVO.getAppCode());
        BizAssert.isNotEmpty(app, AppMngErrorEnum.APP_IS_NULL_OR_EXIST_MULTIPLE_SAME);

        String userId = MngUtil.getUserId();
        BizAssert.isTrue(StrUtil.isNotBlank(userId), AccountMngErrorEnum.CURRENT_USER_ID_IS_NULL);
        cacheService.set(Constants.IAM_LOGIN, Constants.USER_APP + userId, app.getId());
        List<Menu> menus = userLoginStrategy.findMenusByUserCodeAndRoleForSpecialApp(userId, roleReqVO.getRoleId(), app);
        if (menus.size() > 0) {
            List<MenuTreeVO> menuTreeVOS = new ArrayList<>();
            menus.stream().filter(t -> !Constants.MENUTYPE_BUTTON.equals(t.getType()))
                    .forEach(t -> menuTreeVOS.add(BeanUtil.toBean(t, MenuTreeVO.class)));
            return ok(TreeUtils.buildByRecursive(menuTreeVOS, Constants.ROOTID));
        }
        return fail(MenuMngErrorEnum.MENU_IS_NULL);
    }

    @ApiOperation("??????????????????")
    @PostMapping(value = "/menuTreeByLoginUser")
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<List<MenuTreeVO>> getMenuTreeByLoginUser(@RequestBody AppCodeVO appCode) {
        if (StrUtil.isBlank(appCode.getAppCode())) {
            return fail(AppMngErrorEnum.APP_CODE_IS_NULL);
        }
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(App.APPCODE, appCode.getAppCode());
        List<App> appList = appService.list(queryWrapper);
        if (appList.size() != 1) {
            return fail(AppMngErrorEnum.APP_IS_NULL_OR_EXIST_MULTIPLE_SAME);
        }
        App app = appList.get(0);

        String userId = MngUtil.getUserId();
        if (StrUtil.isBlank(userId)) {
            return fail(AccountMngErrorEnum.CURRENT_USER_ID_IS_NULL);
        }
        cacheService.set(Constants.IAM_LOGIN, Constants.USER_APP + userId, app.getId());
        List<Menu> menus = userLoginStrategy.getMenuByUserCodeId(userId, app.getId());
        if (menus.size() > 0) {
            List<MenuTreeVO> menuTreeVOS = new ArrayList<>();
            menus.stream().filter(t -> !Constants.MENUTYPE_BUTTON.equals(t.getType()))
                    .forEach(t -> menuTreeVOS.add(BeanUtil.toBean(t, MenuTreeVO.class)));
            return ok(TreeUtils.buildByRecursive(menuTreeVOS, Constants.ROOTID));
        }
        return fail(MenuMngErrorEnum.MENU_IS_NULL);
    }

    @ApiOperation("????????????????????????")
    @PostMapping(value = "/getMenuPermsByLoginUser")
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<List<String>> getMenuPermsByLoginUser(@RequestBody AppCodeVO appCode) {
        if (ObjectUtil.isEmpty(appCode.getAppCode())) {
            return fail(AppMngErrorEnum.APP_CODE_CANNOT_BE_NULL);
        }
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(App.APPCODE, appCode.getAppCode());
        App app = appService.getOne(queryWrapper);
        BizAssert.isNotEmpty(app, AppMngErrorEnum.APP_IS_NULL);
        System.out.println(JSONUtil.toJsonStr(app));
        cacheService.set(Constants.IAM_LOGIN, Constants.USER_APP + FzhxIam.getUser().getUserId(), app.getId());
        List<Menu> menus = userLoginStrategy.getMenuByUserCodeId(FzhxIam.getUser().getUserId(), app.getId());
        if (menus.size() > 0) {
            List<String> collect = menus.stream()
                    .filter(t -> Constants.MENU_HIDDEN_YES.equals(t.getHidden()))
                    .filter(t -> !Constants.MENUTYPE_BUTTON.equals(t.getType()))
                    .map(t -> t.getPerms()).collect(Collectors.toList());
            return ok(collect);
        }
        return fail(MenuMngErrorEnum.MENU_IS_NULL.getCode(), "??????????????????");
    }

    @ApiOperation("??????????????????????????????")
    @PostMapping(value = "/getMenuPermsByLoginUserRole")
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<List<String>> getMenuPermsByLoginUserRole(@Validated @RequestBody UserCodeRoleReqVO reqVO) {
        App app = appService.getAppByAppCode(reqVO.getAppCode());
        BizAssert.isNotEmpty(app, AppMngErrorEnum.APP_IS_NULL);
        cacheService.set(Constants.IAM_LOGIN, Constants.USER_APP + FzhxIam.getUser().getUserId(), app.getId());
        List<Menu> menus = userLoginStrategy.findMenusByUserCodeAndRoleForSpecialApp(FzhxIam.getUser().getUserId(),
                reqVO.getRoleId(), app);
        if (ObjectUtil.isNotEmpty(menus)) {
            List<String> collect = menus.stream()
                    .filter(t -> Constants.MENU_HIDDEN_YES.equals(t.getHidden()))
                    .filter(t -> !Constants.MENUTYPE_BUTTON.equals(t.getType()))
                    .map(Menu::getPerms).collect(Collectors.toList());
            return ok(collect);
        }

        return fail(MenuMngErrorEnum.MENU_IS_NULL.getCode(), "??????????????????");
    }

    @ApiOperation("????????????????????????")
    @PostMapping(value = "/setAppDefaultRole")
    @PreAuthorize(value = "hasLogin()", msg = " ????????????????????????????????????..")
    public Result<Boolean> setAppDefaultRole(@Validated @RequestBody UserCodeRoleReqVO reqVO) {
        BizAssert.isTrue(StrUtil.isNotBlank(reqVO.getRoleId()), UserMngErrorEnum.EMPTY_ROLE);
        App app = appService.getAppByAppCode(reqVO.getAppCode());
        BizAssert.isNotEmpty(app, AppMngErrorEnum.APP_IS_NULL);
        cacheService.set(Constants.IAM_LOGIN, Constants.USER_APP + FzhxIam.getUser().getUserId(), app.getId());
        return ok(crudService.setAppDefaultRole(reqVO));
    }

    @ApiOperation("????????????")
    @PostMapping({"/findPermissions"})
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<List<String>> findPermissions(@RequestBody AppCodeVO appCode) {
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(App.APPCODE, appCode.getAppCode());
        App app = appService.getOne(queryWrapper);
        BizAssert.isNotEmpty(app, AppMngErrorEnum.APP_IS_NULL);
        System.out.println(JSONUtil.toJsonStr(app));
        cacheService.set(Constants.IAM_LOGIN, Constants.USER_APP + FzhxIam.getUser().getUserId(), app.getId());
        List<Menu> menus = userLoginStrategy.getMenuByUserCodeId(FzhxIam.getUser().getUserId(), app.getId());
        if (menus.size() > 0) {
            List<String> collect = menus.stream().filter(t -> Constants.MENU_HIDDEN_YES.equals(t.getHidden()))
                    .filter(t -> Constants.MENUTYPE_BUTTON.equals(t.getType()))
                    .map(t -> t.getPerms()).collect(Collectors.toList());
            return ok(collect);
        }
        return fail(MenuMngErrorEnum.MENU_IS_NULL.getCode(), "??????????????????");
    }

    @ApiOperation("??????????????????")
    @PostMapping({"/getAppsByUserCodeId"})
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<List<AppVO>> getAppsByUserCodeId() {
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc(App.ORDERINDEX);
        List<App> list = appService.list(queryWrapper);
        if (list.size() > 0) {
            List<AppVO> appVOS = new ArrayList<>();
            list.forEach(t -> appVOS.add(BeanUtil.toBean(t, AppVO.class).setIsuseable(false)));
            List<RoleUserCode> roleUserCodes = roleUserCodeService.getRoleUserCodeByUserCodeId(FzhxIam.getUser().getUserId());
            if (roleUserCodes.size() > 0) {
                Set<String> appIds = roleUserCodes.stream().map(t -> t.getAppId()).collect(Collectors.toSet());
                List<App> apps = appService.listByIds(appIds);
                for (AppVO appVO : appVOS) {
                    Optional<App> first = apps.stream().filter(t -> t.getId().equals(appVO.getId()))
                            .filter(t -> Constants.APP_IS_USE.equals(t.getUseable())).findFirst();
                    first.ifPresent(t -> appVO.setIsuseable(true));
                }
                return ok(appVOS);
            } else {
                return ok(appVOS);
            }
        } else {
            return ok(new ArrayList<>());
        }
    }

    @ApiOperation("??????????????????")
    @PostMapping({"/getRolesByUserCodeId"})
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<UserCodeRolesRspVO> getRolesByUserCodeId(@Validated @RequestBody AppCodeVO appCode) {
        String userCodeId = FzhxIam.getUser().getUserId();
        UserCodeRolesRspVO rolesRspVO = new UserCodeRolesRspVO();
        UserCode userCode = crudService.getById(userCodeId);
        if (StrUtil.isNotBlank(userCode.getDefaultRoles())) {
            HashMap<String, String> roleMap = JsonUtils.parse(userCode.getDefaultRoles(), HashMap.class);
            rolesRspVO.setDefaultRoleId(roleMap.get(appCode.getAppCode()));
        }
        List<Role> roleList = new ArrayList<>();
        App app = appService.getAppByAppCode(appCode.getAppCode());
        BizAssert.isNotEmpty(app, AppMngErrorEnum.APP_IS_NULL);
        System.out.println(JSONUtil.toJsonStr(app));
        cacheService.set(Constants.IAM_LOGIN, Constants.USER_APP + userCodeId, app.getId());
        QueryWrapper<RoleUserCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RoleUserCode.USERCODEID, userCodeId)
                .eq(RoleUserCode.APPID, app.getId());
        List<RoleUserCode> roleUserCodes = roleUserCodeService.list(queryWrapper);
        if (roleUserCodes.size() > 0) {
            Set<String> roles = roleUserCodes.stream().map(t -> t.getRoleId()).collect(Collectors.toSet());
            roleList = roleService.listByIds(roles);
        }

        rolesRspVO.setRoleList(roleList);

        return ok(rolesRspVO);
    }

    @ApiOperation("????????????id????????????")
    @SysLog(value = "'????????????id????????????,??????id:' + #ids", optype = OptypeEnum.SELECT)
    @PostMapping(value = "/listmaptt")
    @PreAuthorize(value = "permitAll()")
    public Map<String, String> list(@RequestBody List<String> ids) {
        if (ids.size() == 0) {
            List<UserCode> list = crudService.list();
            return list.stream().collect(Collectors.toMap(t -> t.getId(), t -> t.getLoginName()));
        } else {
            List<UserCode> list = this.crudService.listByIds(ids);
            Map<String, String> collect = list.stream().collect(Collectors.toMap(t -> t.getId(), t -> t.getLoginName()));
            return collect;
        }
    }

    @ApiOperation("???????????????????????????????????????")
    @PostMapping(value = "/getAccountUser")
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<User> getAccountUser() {
        FzhxIamUser iamUser = FzhxIam.getUser();
        BizAssert.isFalse(ObjectUtil.isEmpty(iamUser), "????????????????????????????????????,???????????????");
        UserCode userCode = crudService.getById(iamUser.getUserId());
        BizAssert.isFalse(ObjectUtil.isEmpty(userCode), "????????????:??????????????????????????????");
        BizAssert.isFalse(StrUtil.isBlank(userCode.getUserId()), "?????????????????????????????????,??????????????????");
        User user = userService.getById(userCode.getUserId());
        BizAssert.isFalse(ObjectUtil.isEmpty(user), "????????????:??????????????????????????????????????????");
        return ok(user);
    }


    @ApiOperation("????????????id??????????????????")
    @SysLog(value = "'????????????id??????????????????,??????id:' + #idVO.id", optype = OptypeEnum.SELECT)
    @PostMapping(value = "getOfficeByUserCodeId")
    public Result<Office> getOfficeByUserCodeId(@RequestBody IdVO idVO) {
        if (StrUtil.isEmpty(idVO.getId())) {
            return fail(AccountMngErrorEnum.USER_CODE_IDS_CANNOT_BE_EMPTY);
        }
        UserCode userCode = crudService.getById(idVO.getId());
        if (ObjectUtil.isEmpty(userCode)) {
            return fail(AccountMngErrorEnum.USER_CODE_IS_NOT_EXIST);
        }
        if (StrUtil.isNotEmpty(userCode.getUserId())) {
            User user = userService.getById(userCode.getUserId());
            if (ObjectUtil.isNotEmpty(user) && StrUtil.isNotEmpty(user.getOfficeId())) {
                Office office = officeService.getById(user.getOfficeId());
                return ok(office);
            }
        }
        return ok(new Office());
    }


    @ApiOperation("????????????id???????????????????????????")
    @SysLog(value = "'????????????id???????????????????????????,??????id:' + #idVO.id", optype = OptypeEnum.SELECT)
    @PostMapping(value = "getOfficeTreeNodeByUserCodeId")
    public Result<List<OfficeTreeVO>> getOfficeTreeNodeByUserCodeId(@RequestBody IdVO idVO) {
        if (StrUtil.isEmpty(idVO.getId())) {
            return fail(AccountMngErrorEnum.USER_CODE_IDS_CANNOT_BE_EMPTY);
        }
        UserCode userCode = crudService.getById(idVO.getId());
        if (ObjectUtil.isEmpty(userCode)) {
            return fail(AccountMngErrorEnum.USER_CODE_IS_NOT_EXIST);
        }
        if (StrUtil.isNotEmpty(userCode.getUserId())) {
            User user = userService.getById(userCode.getUserId());
            if (ObjectUtil.isNotEmpty(user) && StrUtil.isNotEmpty(user.getOfficeId())) {
                Office office = officeService.getById(user.getOfficeId());
                OfficeTreeVO otv = BeanUtil.toBean(office, OfficeTreeVO.class);
                List<OfficeTreeVO> treeVOList = new ArrayList<>();
                treeVOList.add(otv);
                return ok(treeVOList);
            }
        }
        return ok(new ArrayList<>());
    }

    /**
     * ??????????????????id??????????????????????????????????????????????????????
     *
     * @param codeVO
     * @return
     */
    @ApiOperation("????????????id???????????????????????????")
    @SysLog(value = "'????????????id???????????????????????????,???????????????' + #codeVO.appCode + ',??????id:' + #codeVO.userCodeId", optype = OptypeEnum.SELECT)
    @PostMapping(value = "/getMenuByUserCodeId")
    public Result<List<Menu>> getMenuByUserCodeId(@Validated @RequestBody AppCodeUserIdVO codeVO) {
        UserCode userCode = crudService.getById(codeVO.getUserCodeId());
        if (ObjectUtil.isEmpty(userCode)) {
            return fail(AccountMngErrorEnum.USER_CODE_IS_NOT_EXIST);
        }

        //????????????app??????????????????app???id
        App app = appService.getAppByAppCode(codeVO.getAppCode());
        BizAssert.isTrue(ObjectUtil.isNotNull(app), AppMngErrorEnum.APP_IS_NULL);
        //????????????id?????????????????????????????????
        List<Menu> menus = userLoginStrategy.getMenuByUserCodeId(codeVO.getUserCodeId(), app.getId());
        return ok(menus);
    }


    @ApiOperation("?????????????????????")
    @PostMapping(value = "/captcha")
    public Result<CaptchaRspVO> captcha() throws Exception {
        String randomUUID = IdUtil.randomUUID();
        String text = "";
        if (captchaComplexity) {
            text = RandomUtil.randomString(4);
        } else {
            text = RandomUtil.randomNumbers(4);
        }
        String code = RandomImgCodeUtil.imageToBase64(120, 40, captchaBackColor, captchaFontColor, text);
        // ?????????redis
        cacheService.setExpire(Constants.IAM_LOGIN, Constants.CAPTCHA_SESSION_KEY + randomUUID, text, Constants.CAPTCHA_EXPIRE);
        CaptchaRspVO captchaRspVO = new CaptchaRspVO();
        captchaRspVO.setKey(randomUUID).setImage("data:image/jpg;base64," + code);
        return ok(captchaRspVO);
    }

    @ApiOperation("?????????????????????")
    @PostMapping(value = "/captchaByKey")
    public Result<CaptchaRspVO> captchaByKey() throws Exception {
        if (!captchaByKey) {
            return ok(new CaptchaRspVO());
        }
        String randomUUID = IdUtil.randomUUID();
        String text = "";
        if (captchaComplexity) {
            text = RandomUtil.randomString(4);
        } else {
            text = RandomUtil.randomNumbers(4);
        }
        // ?????????redis
        cacheService.setExpire(Constants.IAM_LOGIN, Constants.CAPTCHA_SESSION_KEY + randomUUID, text, Constants.CAPTCHA_EXPIRE);
        CaptchaRspVO captchaRspVO = new CaptchaRspVO();
        captchaRspVO.setKey(randomUUID).setImage(text);
        return ok(captchaRspVO);
    }


    @ApiOperation("??????????????????????????????")
    @PostMapping(value = "/verifyPwdWhethePastDuer")
    public Result<Boolean> verifyPwdWhethePastDuer() {
        return ok(configService.verifyPwdWhethePastDuer());
    }


}
