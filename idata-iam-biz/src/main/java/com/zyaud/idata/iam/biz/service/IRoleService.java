package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.biz.model.entity.Role;

import java.util.List;

/**
 * <p>
 * 系统应用角色 服务类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface IRoleService extends IService<Role> {
    Long verifyRoleName(String id, String appId, String roleName);

    Long verifyRoleCode(String id, String appId, String roleCode);

    List<Role> findRole(String appId, String roleName, String roleCode, String roleType, String userable);
}
