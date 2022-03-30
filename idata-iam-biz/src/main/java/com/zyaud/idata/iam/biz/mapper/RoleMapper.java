package com.zyaud.idata.iam.biz.mapper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.idata.iam.biz.model.entity.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * RoleMapper 接口
 * 系统应用角色
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface RoleMapper extends BaseMapper<Role> {

    default List<Role> listRoleByAppIdAndUseable(String appId, String userable) {
        if (StrUtil.isBlank(appId)) {
            return new ArrayList<>();
        }
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq(Role.APPID, appId)
                .eq(StrUtil.isNotBlank(userable), Role.USEABLE, userable);
        return this.selectList(wrapper);
    }

    default List<Role> listRoleByUseable(String userable) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(userable), Role.USEABLE, userable);
        return this.selectList(wrapper);
    }

    default List<Role> findRole(String appId, String roleName, String roleCode, String roleType, String userable) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(appId), Role.APPID, appId)
                .eq(StrUtil.isNotBlank(roleName), Role.ROLENAME, roleName)
                .eq(StrUtil.isNotBlank(roleCode), Role.ROLECODE, roleCode)
                .eq(StrUtil.isNotBlank(roleType), Role.ROLETYPE, roleType)
                .eq(StrUtil.isNotBlank(userable), Role.USEABLE, userable);
        return this.selectList(wrapper);
    }
    default Role getByAppIdAndRoleName(String appId, String roleName) {
        if (StrUtil.isEmpty(appId)) {
            BizAssert.fail("应用id不能为可空");
        }
        if (StrUtil.isEmpty(roleName)) {
            BizAssert.fail("角色名不能为可空");
        }
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Role.APPID, appId)
                .eq(Role.ROLENAME, roleName);
        return this.selectOne(queryWrapper);
    }

    default List<Role> getByAppIds(List<String> appIds) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(ObjectUtil.isNotEmpty(appIds), Role.APPID, appIds);
        return this.selectList(queryWrapper);
    }

    default List<Role> getByRoleCodes(List<String> roleCodes) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(ObjectUtil.isNotEmpty(roleCodes), Role.ROLECODE, roleCodes);
        return this.selectList(queryWrapper);
    }

    default Long countRoleName(String id, String appId, String roleName) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne(StrUtil.isNotBlank(id), Role.ID, id)
                .eq(StrUtil.isNotBlank(appId), Role.APPID, appId)
                .eq(StrUtil.isNotBlank(roleName), Role.ROLENAME, roleName);
        return this.selectCount(queryWrapper);
    }

    default Long countRoleCode(String id, String appId, String roleCode) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne(StrUtil.isNotBlank(id), Role.ID, id)
                .eq(StrUtil.isNotBlank(appId), Role.APPID, appId)
                .eq(StrUtil.isNotBlank(roleCode), Role.ROLECODE, roleCode);
        return this.selectCount(queryWrapper);
    }
}
