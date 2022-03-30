package com.zyaud.idata.iam.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyaud.idata.iam.biz.mapper.RoleMenuMapper;
import com.zyaud.idata.iam.biz.model.entity.RoleMenu;
import com.zyaud.idata.iam.biz.service.IRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色菜单 服务实现类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Override
    public List<RoleMenu> findRoleMenuListByRoleIds(Set<String> roleIds) {
        return this.baseMapper.findRoleMenuListByRoleIds(roleIds);
    }

    @Override
    public List<RoleMenu> findRoleMenuListByRoleAndMenu(Set<String> roleIds, Set<String> menuIds) {
        return this.baseMapper.findRoleMenuListByRoleAndMenu(roleIds, menuIds);
    }

    @Override
    public List<RoleMenu> findRoleMenuListByMenuIds(Set<String> menuIds) {
        return this.baseMapper.findRoleMenuListByMenuIds(menuIds);
    }

    @Override
    public boolean removeByMenuIds(List<String> menuIds) {
        return this.baseMapper.removeByMenuIds(menuIds) > 0;
    }
}
