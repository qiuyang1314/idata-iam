package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.client.dto.UserCodeInDTO;
import com.zyaud.idata.iam.biz.model.entity.UserCode;
import com.zyaud.idata.iam.biz.model.vo.UserRoleRspVO;
import com.zyaud.idata.iam.biz.model.vo.UserTreeReqVO;
import com.zyaud.idata.iam.biz.model.vo.UserTreeVO;

import java.util.List;

public interface IInUserCodeService extends IService<UserCode> {

    List<UserCode> listUserCodeByAppCode(String appCode);

    /**
     * 获取用户在指定应用下的角色列表
     * @param appCode
     * @param userCodeId
     * @return
     */
    List<UserRoleRspVO> findUserRoleList(String appCode, String userCodeId);

    /**
     * 获取在指定机构下的账号列表
     * @param officeId
     * @return
     */
    List<UserCodeInDTO> findUserCodeListByOfficeId(String officeId);

    /**
     * 机构用户下拉树形列表
     * @param reqVO
     * @return
     */
    List<UserTreeVO> getOfficeUserTree(UserTreeReqVO reqVO);

    /**
     * 角色用户下拉树形列表
     * @param reqVO
     * @return
     */
    List<UserTreeVO> getRoleUserTree(UserTreeReqVO reqVO);

    List<UserTreeVO> getUserTreeVOS(UserTreeReqVO reqVO);
}
