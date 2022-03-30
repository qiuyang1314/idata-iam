package com.zyaud.idata.iam.biz.mapper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.idata.iam.api.req.DTO.RemoteUserInfoCreateReqDTO;
import com.zyaud.idata.iam.biz.model.entity.UserCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * UserCodeMapper 接口
 * 用户账号
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface UserCodeMapper extends BaseMapper<UserCode> {
    default List<UserCode> findUserCodeListByLoginNameAndId(String loginName, String id) {
        BizAssert.isFalse(StrUtil.isBlank(loginName), "登录名称不能为空!");
        QueryWrapper<UserCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(UserCode.LOGINNAME, loginName);
        queryWrapper.ne(StrUtil.isNotBlank(id), UserCode.ID, id);
        return this.selectList(queryWrapper);
    }

    default UserCode getUserCodeByUserId(String userId) {
        QueryWrapper<UserCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(UserCode.USERID, userId);
        return this.selectOne(queryWrapper);
    }

    default List<UserCode> listUserCodeByUserIds(Set<String> userIds) {
        if (userIds.size() == 0) {
            return new ArrayList<>();
        }
        QueryWrapper<UserCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(UserCode.USERID, userIds);
        return selectList(queryWrapper);
    }

    default UserCode getUserCodeByUserCodeIdAndStatus(String userCodeId, String status) {
        if (StrUtil.isBlank(userCodeId)) {
            return new UserCode();
        }
        QueryWrapper<UserCode> wrapper = new QueryWrapper<>();
        wrapper.eq(UserCode.ID, userCodeId)
                .eq(StrUtil.isNotBlank(status), UserCode.STATUS, status);
        return selectOne(wrapper);
    }

    default List<UserCode> findUserCodeListByLoginNames(List<String> loginNames) {
        QueryWrapper<UserCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(ObjectUtil.isNotEmpty(loginNames), UserCode.LOGINNAME, loginNames);
        return this.selectList(queryWrapper);
    }

    default List<UserCode> findAllUserCode() {
        QueryWrapper<UserCode> queryWrapper = new QueryWrapper<>();
        return this.selectList(queryWrapper);
    }

    default UserCode getByLoginName(String loginName){
        QueryWrapper<UserCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(UserCode.LOGINNAME, loginName);
        return this.selectOne(queryWrapper);
    }

    /**
    * @Description 获取同步appframe用户信息
    * @Author qiuyang
    * @return
    * @Date 2022/1/7 19:03
    **/
    List<RemoteUserInfoCreateReqDTO> getAppframeSyncUser();
}
