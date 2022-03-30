package com.zyaud.idata.iam.biz.mapper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.idata.iam.biz.model.entity.App;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * AppMapper 接口
 * 系统应用
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface AppMapper extends BaseMapper<App> {

    default App getAppByCode(String appCode) {
        BizAssert.isTrue(StrUtil.isNotBlank(appCode), "应用编号不能为空");
        QueryWrapper<App> wrapper = new QueryWrapper<>();
        wrapper.eq(App.APPCODE, appCode);
        return selectOne(wrapper);
    }

    default <T> List<App> listAppByCodes(Collection<T> appCodes) {
        QueryWrapper<App> wrapper = new QueryWrapper<>();
        wrapper.in(ObjectUtil.isNotEmpty(appCodes), App.APPCODE, appCodes);
        return selectList(wrapper);
    }

    default List<App> findAppByAppNameAndAppCode(String appName, String appCode, String useable) {
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(appName), App.APPNAME, appName)
                .eq(StrUtil.isNotBlank(appCode), App.APPCODE, appCode)
                .eq(StrUtil.isNotBlank(useable), App.USEABLE, useable);
        return this.selectList(queryWrapper);
    }

    default List<App> findAppByAppNameAndAppCodeAndNid(String appName, String appCode, String id) {
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne(StrUtil.isNotBlank(id), App.ID, id)
                .eq(StrUtil.isNotBlank(appName), App.APPNAME, appName)
                .eq(StrUtil.isNotBlank(appCode), App.APPCODE, appCode);
        return this.selectList(queryWrapper);
    }

    default App getAppByAppNameAndAppCode(String appName, String appCode) {
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(appName), App.APPNAME, appName)
                .eq(StrUtil.isNotBlank(appCode), App.APPCODE, appCode);
        return this.selectOne(queryWrapper);
    }
}
