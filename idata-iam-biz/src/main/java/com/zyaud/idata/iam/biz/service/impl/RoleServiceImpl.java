package com.zyaud.idata.iam.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyaud.idata.iam.biz.mapper.*;
import com.zyaud.idata.iam.biz.model.entity.Role;
import com.zyaud.idata.iam.biz.service.IRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统应用角色 服务实现类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private AppMapper appMapper;

    @Resource
    private RoleUserCodeMapper roleUserCodeMapper;

    @Resource
    private UserCodeMapper userCodeMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public Long verifyRoleName(String id, String appId, String roleName) {
        return baseMapper.countRoleName(id, appId, roleName);
    }

    @Override
    public Long verifyRoleCode(String id, String appId, String roleCode) {
        return baseMapper.countRoleCode(id, appId, roleCode);
    }

    @Override
    public List<Role> findRole(String appId, String roleName, String roleCode, String roleType,String userable) {
        return baseMapper.findRole(appId, roleName, roleCode,roleType, userable);
    }

}
