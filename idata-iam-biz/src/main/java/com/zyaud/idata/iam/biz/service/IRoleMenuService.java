package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.biz.model.entity.RoleMenu;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色菜单 服务类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface IRoleMenuService extends IService<RoleMenu> {
    List<RoleMenu> findRoleMenuListByRoleIds(Set<String> roleIds);
    List<RoleMenu> findRoleMenuListByRoleAndMenu(Set<String> roleIds, Set<String> menuIds);
    List<RoleMenu> findRoleMenuListByMenuIds(Set<String> menuIds);
    boolean removeByMenuIds(List<String> menuIds);
}
