package com.zyaud.idata.iam.biz.mapper;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyaud.idata.iam.biz.model.entity.Menu;

import java.util.List;

/**
 * <p>
 * MenuMapper 接口
 * 菜单
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface MenuMapper extends BaseMapper<Menu> {
    default List<Menu> findMenuListByParentIdAndName(String parentId, String menuName) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Menu.PARENTID, parentId);
        queryWrapper.eq(Menu.NAME, menuName);
        return this.selectList(queryWrapper);
    }

    default List<Menu> findMenuListByAppId(String appId) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Menu.APPID, appId);
        return this.selectList(queryWrapper);
    }

    default List<Menu> findMenuListByMenuCode(String code) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Menu.CODE, code);
        return this.selectList(queryWrapper);
    }

    default List<Menu> findMenusByAppIdAndNameNeType(String appId, String name, String type) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(appId), Menu.APPID, appId)
                .like(StrUtil.isNotBlank(name), Menu.NAME, name)
                .ne(StrUtil.isNotBlank(type), Menu.TYPE, type);
        return this.selectList(queryWrapper);
    }

    default List<Menu> findMenusByParentIdAndNameAndType(String parentId, String name, String type) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(parentId), Menu.PARENTID, parentId)
                .like(StrUtil.isNotBlank(name), Menu.NAME, name)
                .eq(StrUtil.isNotBlank(type), Menu.TYPE, type);
        return this.selectList(queryWrapper);
    }
}
