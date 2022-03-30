package com.zyaud.idata.iam.biz.mapper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.idata.iam.biz.model.entity.StdType;

import java.util.List;

/**
 * <p>
 * StdTypeMapper 接口
 * 字典管理
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
public interface StdTypeMapper extends BaseMapper<StdType> {

    /**
     * 根据字典名称获取符合条件的字典的数量
     *
     * @param name
     * @param id
     * @return
     */
    default Long countByName(String name, String id) {
        QueryWrapper<StdType> wrapper = new QueryWrapper<>();
        wrapper.eq(StdType.STDNAME, name)
                .ne(ObjectUtil.isNotEmpty(id), StdType.ID, id);
        return this.selectCount(wrapper);
    }


    /**
     * 根据字典值名称获取符合条件的字典的数量
     *
     * @param value
     * @param id
     * @return
     */
    default Long countByValue(String value, String id) {
        QueryWrapper<StdType> wrapper = new QueryWrapper<>();
        wrapper.eq(StdType.STDNUM, value)
                .ne(ObjectUtil.isNotEmpty(id), StdType.ID, id);
        return this.selectCount(wrapper);
    }

    default List<StdType> listOrderByAsc(String orderAsc) {
        QueryWrapper<StdType> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc(StrUtil.isNotBlank(orderAsc), orderAsc);
        return this.selectList(wrapper);
    }

    default StdType getByStdNum(String stdNum) {
        if (StrUtil.isEmpty(stdNum)) {
            BizAssert.fail("字典编号不能为空");
        }
        QueryWrapper<StdType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StdType.STDNUM, stdNum);
        return this.selectOne(queryWrapper);
    }
}
