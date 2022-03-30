package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.fzhx.iam.core.FzhxIamUser;
import com.zyaud.idata.iam.biz.model.entity.UserCode;
import com.zyaud.idata.iam.biz.model.vo.UserBaseInfoRspVO;
import com.zyaud.idata.iam.biz.model.vo.UserCodeRoleReqVO;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户账号 服务类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface IUserCodeService extends IService<UserCode> {
    UserCode getByLoginName(String loginName);

    FzhxIamUser getFzhxIamUserByLoginName(UserCode userCode);

    FzhxIamUser getFzhxIamUserByLoginName(String loginName);

    List<UserCode> findUserCodeListByUserIds(Set<String> userIds);

    boolean verify(String loginName, String id);

    UserCode getUserCodeByUserId(String userId);

    boolean setAppDefaultRole(UserCodeRoleReqVO reqVO);

    UserBaseInfoRspVO getUserBaseInfo();
}
