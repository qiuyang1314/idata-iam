package com.zyaud.idata.iam.biz.mapper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.idata.iam.biz.model.entity.User;

import java.util.List;

/**
 * <p>
 * UserMapper 接口
 * 用户信息
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface UserMapper extends BaseMapper<User> {
    default List<User> findUserListByIdCard(String idCard) {
        BizAssert.isFalse(ObjectUtil.isEmpty(idCard), "身份证号不能为空");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(User.IDCARD, idCard);
        return this.selectList(queryWrapper);
    }

    default List<User> findUserListByName(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(name), User.NAME, name);
        return this.selectList(queryWrapper);
    }

    default List<User> findUserListByOfficeId(String officeId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(officeId), User.OFFICEID, officeId);
        return this.selectList(queryWrapper);
    }

    default long countByIdCard(String idCard,String id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne(StrUtil.isNotBlank(id), User.ID, id)
                .eq(StrUtil.isNotBlank(idCard), User.IDCARD, idCard);
        return this.selectCount(queryWrapper);
    }

}
