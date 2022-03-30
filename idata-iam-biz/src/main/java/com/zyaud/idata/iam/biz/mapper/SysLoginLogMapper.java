package com.zyaud.idata.iam.biz.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyaud.idata.iam.biz.model.entity.SysLoginLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * SysLogMapper 接口
 * 系统登录日志
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {

    /**
     * 根据用户账号获取用户账号的上次登录记录
     * @param name 登录名
     * @return
     */
    List<SysLoginLog> getLastRegister(@Param("name")String name);

    default List<SysLoginLog> findSysLoginLogByLoginName(String loginName) {
        QueryWrapper<SysLoginLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SysLoginLog.LOGINNAME, loginName)
                .orderByDesc(SysLoginLog.CREATE_TIME);
        return this.selectList(queryWrapper);
    }
}
