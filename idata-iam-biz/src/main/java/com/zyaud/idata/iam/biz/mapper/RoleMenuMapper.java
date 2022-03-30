package com.zyaud.idata.iam.biz.mapper;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.idata.iam.biz.model.entity.RoleMenu;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * RoleMenuMapper 接口
 * 角色菜单
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    default List<RoleMenu> findRoleMenuListByRoleIds(Set<String> roleIds) {
        BizAssert.isFalse(ObjectUtil.isEmpty(roleIds), "角色不能为空");
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(RoleMenu.ROLEID, roleIds);
        return this.selectList(queryWrapper);
    }
    default List<RoleMenu> findRoleMenuListByMenuIds(Set<String> menuIds) {
        BizAssert.isFalse(ObjectUtil.isEmpty(menuIds), "菜单不能为空");
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(RoleMenu.MENUID, menuIds);
        return this.selectList(queryWrapper);
    }

    default List<RoleMenu> findRoleMenuListByRoleAndMenu(Set<String> roleIds, Set<String> menuIds) {
        BizAssert.isFalse(ObjectUtil.isEmpty(roleIds), "角色不能为空");
        BizAssert.isFalse(ObjectUtil.isEmpty(menuIds), "菜单不能为空");
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(RoleMenu.ROLEID, roleIds);
        queryWrapper.in(RoleMenu.MENUID, menuIds);
        return this.selectList(queryWrapper);
    }

    default int removeByMenuIds(List<String> menuIds) {
        BizAssert.isFalse(ObjectUtil.isEmpty(menuIds), "菜单不能为空");
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(RoleMenu.MENUID, menuIds);
        return this.delete(queryWrapper);
    }
}
