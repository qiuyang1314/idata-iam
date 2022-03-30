package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.biz.model.entity.RoleUserCode;
import com.zyaud.idata.iam.biz.model.vo.RoleUserCodeVO;
import com.zyaud.idata.iam.biz.model.vo.UserCodeAndAppAndRoleInfoVO;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 角色用户 服务类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface IRoleUserCodeService extends IService<RoleUserCode> {
    List<RoleUserCodeVO> getUserCodes(String appCode, Serializable roleName);

    void verifyRoleUserCode(String appId, List<String> userCodeIds);

    List<RoleUserCode> getRoleUserCodeByUserCodeId(String userCodeId);

    List<RoleUserCode> findRoleUserCode(List<String> userCodeIds, List<String> appIds, List<String> roleIds);

    List<UserCodeAndAppAndRoleInfoVO> getUserCodeAndAppAndRoleInfoByUserCodeIdsAndAppIdsAndRoleIds(List<String> userCodeIds, List<String> appIds, List<String> roleIds);
}
