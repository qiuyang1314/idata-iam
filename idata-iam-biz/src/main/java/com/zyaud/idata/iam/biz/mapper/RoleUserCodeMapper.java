package com.zyaud.idata.iam.biz.mapper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyaud.idata.iam.biz.model.entity.RoleUserCode;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * RoleUserCodeMapper 接口
 * 角色用户
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface RoleUserCodeMapper extends BaseMapper<RoleUserCode> {

    default List<RoleUserCode> listRoleUserCodeByAppId(String appId) {
        if (StrUtil.isBlank(appId)) {
            return new ArrayList<>();
        }
        QueryWrapper<RoleUserCode> wrapper = new QueryWrapper<>();
        wrapper.eq(RoleUserCode.APPID, appId);
        return selectList(wrapper);
    }

    default List<RoleUserCode> listRoleUserCodeByAppIdsAndRoleIds(List<String> appIds, List<String> roleIds) {
        return listRoleUserCodeByAppIdsAndRoleIdsAndUserCodeIds(appIds, roleIds, null);
    }

    default void deleteByAppIdsAndRoleIds(List<String> appIds, List<String> roleIds) {
        deleteByAppIdsAndRoleIdsAndUserCodeIds(appIds, roleIds, null);
    }

    default void deleteByUserCodeIds(List<String> userCodeIds) {
        deleteByAppIdsAndRoleIdsAndUserCodeIds(null, null, userCodeIds);
    }

    default void deleteByAppIdsAndRoleIdsAndUserCodeIds(List<String> appIds, List<String> roleIds, List<String> userCodeIds) {
        QueryWrapper<RoleUserCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(ObjectUtil.isNotEmpty(appIds), RoleUserCode.APPID, appIds)
                .in(ObjectUtil.isNotEmpty(roleIds), RoleUserCode.ROLEID, roleIds)
                .in(ObjectUtil.isNotEmpty(userCodeIds), RoleUserCode.USERCODEID, userCodeIds);
        this.delete(queryWrapper);
    }

    default List<RoleUserCode> listRoleUserCodeByUserCodeIds(List<String> userCodeIds) {
        return listRoleUserCodeByAppIdsAndRoleIdsAndUserCodeIds(null, null, userCodeIds);
    }

    default List<RoleUserCode> listRoleUserCodeByAppIdsAndRoleIdsAndUserCodeIds(List<String> appIds,
                                                                                List<String> roleIds,
                                                                                List<String> userCodeIds) {
        QueryWrapper<RoleUserCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(ObjectUtil.isNotEmpty(appIds), RoleUserCode.APPID, appIds)
                .in(ObjectUtil.isNotEmpty(roleIds), RoleUserCode.ROLEID, roleIds)
                .in(ObjectUtil.isNotEmpty(userCodeIds), RoleUserCode.USERCODEID, userCodeIds);
        return this.selectList(queryWrapper);
    }
}
