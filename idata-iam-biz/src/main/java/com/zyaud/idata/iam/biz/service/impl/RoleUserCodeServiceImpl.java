package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.idata.iam.biz.mapper.RoleUserCodeMapper;
import com.zyaud.idata.iam.biz.model.entity.App;
import com.zyaud.idata.iam.biz.model.entity.Role;
import com.zyaud.idata.iam.biz.model.entity.RoleUserCode;
import com.zyaud.idata.iam.biz.model.entity.UserCode;
import com.zyaud.idata.iam.biz.model.vo.RoleUserCodeVO;
import com.zyaud.idata.iam.biz.model.vo.UserCodeAndAppAndRoleInfoVO;
import com.zyaud.idata.iam.biz.service.IAppService;
import com.zyaud.idata.iam.biz.service.IRoleService;
import com.zyaud.idata.iam.biz.service.IRoleUserCodeService;
import com.zyaud.idata.iam.biz.service.IUserCodeService;
import com.zyaud.idata.iam.common.errorcode.AppMngErrorEnum;
import com.zyaud.idata.iam.common.errorcode.RoleMngErrorEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色账号 服务实现类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
@Service
public class RoleUserCodeServiceImpl extends ServiceImpl<RoleUserCodeMapper, RoleUserCode> implements IRoleUserCodeService {

    @Resource
    private IUserCodeService userCodeService;
    @Resource
    private IRoleService roleService;
    @Resource
    private IAppService appService;

    @Override
    public List<RoleUserCodeVO> getUserCodes(String appCode, Serializable roleName) {
        App app = appService.getAppByAppCode(appCode);
        BizAssert.isNotEmpty(app, AppMngErrorEnum.APP_IS_NULL);

        //角色
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_name", roleName)
                .eq("app_id", app.getId());
        Role role = roleService.getOne(queryWrapper);
        if (ObjectUtil.isEmpty(role)) {
            BizAssert.fail(RoleMngErrorEnum.ROLE_IS_NOT_EXIST.getCode(),
                    roleName + RoleMngErrorEnum.ROLE_IS_NOT_EXIST.getMessage());
        }
        //角色账号关联
        QueryWrapper<RoleUserCode> roleUserCodeQueryWrapper = new QueryWrapper<>();
        roleUserCodeQueryWrapper.eq("app_id", app.getId())
                .eq("role_id", role.getId());
        List<RoleUserCode> roleUserCodes = this.list(roleUserCodeQueryWrapper);
        if (ObjectUtil.isEmpty(roleUserCodes)) {
            return new ArrayList<>();
        }
        List<RoleUserCodeVO> roleUserCodeVOS = new ArrayList<>();
        roleUserCodes.forEach(t -> roleUserCodeVOS.add(BeanUtil.toBean(t, RoleUserCodeVO.class)));
        List<String> collect = roleUserCodes.stream().map(t -> t.getUserCodeId()).collect(Collectors.toList());
        QueryWrapper<UserCode> userCodeQueryWrapper = new QueryWrapper<>();
        userCodeQueryWrapper.in("id", collect);
        List<UserCode> userCodes = userCodeService.list(userCodeQueryWrapper);
        if (ObjectUtil.isEmpty(userCodes)) {
            return new ArrayList<>();
        }
        for (RoleUserCodeVO roleUserCodeVO : roleUserCodeVOS) {
            Optional<UserCode> first = userCodes.stream().filter(t -> t.getId().equals(roleUserCodeVO.getUserCodeId())).findFirst();
            first.ifPresent(t -> roleUserCodeVO.setLoginName(t.getLoginName()).setUserId(t.getUserId()));
        }
        return roleUserCodeVOS;
    }

    @Override
    public void verifyRoleUserCode(String appId, List<String> userCodeIds) {
        List<String> roles = new ArrayList<>();
        roles.add("系统管理员");
        roles.add("安全管理员");
        roles.add("审计管理员");
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.in("role_name", roles);
        List<Role> list1 = roleService.list(roleQueryWrapper);
        QueryWrapper<RoleUserCode> roleUserCodeQueryWrapper = new QueryWrapper<>();
        roleUserCodeQueryWrapper.eq("app_id", appId)
                .in("role_id", list1.stream().map(t -> t.getId()).collect(Collectors.toSet()))
                .in("user_code_id", userCodeIds);
        List<RoleUserCode> list = this.list(roleUserCodeQueryWrapper);
        if (ObjectUtil.isNotEmpty(list)) {
            Set<String> collect = list.stream().map(t -> t.getUserCodeId()).collect(Collectors.toSet());
            List<UserCode> userCodes = userCodeService.listByIds(collect);
            StringBuffer name = new StringBuffer();
            userCodes.forEach(t -> name.append(t.getLoginName() + ","));
            BizAssert.fail(RoleMngErrorEnum.ROLE_EXCLUSION_CANNOT_JOIN.getCode(),
                    name.toString() + "," + RoleMngErrorEnum.ROLE_EXCLUSION_CANNOT_JOIN.getMessage());
        }
    }

    @Override
    public List<RoleUserCode> getRoleUserCodeByUserCodeId(String userCodeId) {
        QueryWrapper<RoleUserCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RoleUserCode.USERCODEID, userCodeId);
        return this.list(queryWrapper);
    }

    @Override
    public List<RoleUserCode> findRoleUserCode(List<String> userCodeIds, List<String> appIds, List<String> roleIds) {
        return baseMapper.listRoleUserCodeByAppIdsAndRoleIdsAndUserCodeIds(appIds, roleIds, userCodeIds);
    }

    @Override
    public List<UserCodeAndAppAndRoleInfoVO> getUserCodeAndAppAndRoleInfoByUserCodeIdsAndAppIdsAndRoleIds(List<String> userCodeIds, List<String> appIds, List<String> roleIds) {
        QueryWrapper<RoleUserCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(ObjectUtil.isNotEmpty(userCodeIds), RoleUserCode.USERCODEID, userCodeIds)
                .in(ObjectUtil.isNotEmpty(appIds), RoleUserCode.APPID, appIds)
                .in(ObjectUtil.isNotEmpty(roleIds), RoleUserCode.ROLEID, roleIds);
        List<RoleUserCode> roleUserCodes = this.list(queryWrapper);
        //统一返回对象
        List<UserCodeAndAppAndRoleInfoVO> userCodeAndAppAndRoleInfoVOS = new ArrayList<>();

        //账号信息
        List<String> userCodeIdList = roleUserCodes.stream().map(t -> t.getUserCodeId()).collect(Collectors.toList());
        Map<String, UserCodeAndAppAndRoleInfoVO.UserCodeInfo> userCodeInfoMap = new HashMap<>();
        if (userCodeIdList.size() > 0) {
            List<UserCode> userCodes = userCodeService.listByIds(userCodeIdList);
            for (UserCode userCode : userCodes) {
                UserCodeAndAppAndRoleInfoVO.UserCodeInfo userCodeInfo = BeanUtil.toBean(userCode, UserCodeAndAppAndRoleInfoVO.UserCodeInfo.class);
                userCodeInfoMap.put(userCodeInfo.getId(), userCodeInfo);
            }
        }

        //应用信息
        List<String> appIdList = roleUserCodes.stream().map(t -> t.getAppId()).collect(Collectors.toList());
        Map<String, UserCodeAndAppAndRoleInfoVO.AppInfo> appInfoMap = new HashMap<>();
        if (appIdList.size() > 0) {
            List<App> apps = appService.listByIds(appIdList);
            for (App app : apps) {
                UserCodeAndAppAndRoleInfoVO.AppInfo appInfo = BeanUtil.toBean(app, UserCodeAndAppAndRoleInfoVO.AppInfo.class);
                appInfoMap.put(appInfo.getId(), appInfo);
            }
        }

        //角色信息
        List<String> roleIdList = roleUserCodes.stream().map(t -> t.getRoleId()).collect(Collectors.toList());
        Map<String, UserCodeAndAppAndRoleInfoVO.RoleInfo> roleInfoMap = new HashMap<>();
        if (roleIdList.size() > 0) {
            List<Role> roles = roleService.listByIds(roleIdList);
            for (Role role : roles) {
                UserCodeAndAppAndRoleInfoVO.RoleInfo roleInfo = BeanUtil.toBean(role, UserCodeAndAppAndRoleInfoVO.RoleInfo.class);
                roleInfoMap.put(roleInfo.getId(), roleInfo);
            }
        }

        for (RoleUserCode roleUserCode : roleUserCodes) {
            UserCodeAndAppAndRoleInfoVO userCodeAndAppAndRoleInfoVO = new UserCodeAndAppAndRoleInfoVO();
            userCodeAndAppAndRoleInfoVO.setAppInfo(appInfoMap.get(roleUserCode.getAppId()))
                    .setRoleInfo(roleInfoMap.get(roleUserCode.getRoleId()))
                    .setUserCodeInfo(userCodeInfoMap.get(roleUserCode.getUserCodeId()));
            userCodeAndAppAndRoleInfoVOS.add(userCodeAndAppAndRoleInfoVO);
        }
        return userCodeAndAppAndRoleInfoVOS;
    }
}
