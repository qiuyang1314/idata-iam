package com.zyaud.idata.iam.biz.mapper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyaud.idata.iam.biz.model.entity.StdCode;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * StdCodeMapper 接口
 * 字典编码
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
public interface StdCodeMapper extends BaseMapper<StdCode> {

    default void deleteStdCodeByStdType(String stdType) {
        if (StrUtil.isNotBlank(stdType)) {
            QueryWrapper<StdCode> wrapper = new QueryWrapper<>();
            wrapper.eq(StdCode.STDTYPE, stdType);
            this.delete(wrapper);
        }
    }


    default List<StdCode> getByStdType(List<String> stdTypes,String userable) {
        QueryWrapper<StdCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(ObjectUtil.isNotEmpty(stdTypes), StdCode.STDTYPE, stdTypes)
                .eq(StrUtil.isNotBlank(userable), StdCode.USEABLE, userable)
                .orderByAsc(StdCode.ORDERINDEX);
        return this.selectList(queryWrapper);
    }


    default List<StdCode> getByStdType(String stdType, String userable) {
        QueryWrapper<StdCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(stdType), StdCode.STDTYPE, stdType)
                .eq(StrUtil.isNotBlank(userable), StdCode.USEABLE, userable)
                .orderByAsc(StdCode.ORDERINDEX);
        return this.selectList(queryWrapper);
    }

    default List<StdCode> findStdCodeByStdTypeAndCodeNameAndCodeValueNeId(String stdType,
                                                                          String codeName,
                                                                          String codeValue,
                                                                          String id) {
        QueryWrapper<StdCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne(StrUtil.isNotBlank(id), StdCode.ID, id)
                .eq(StrUtil.isNotBlank(stdType), StdCode.STDTYPE, stdType)
                .eq(StrUtil.isNotBlank(codeName), StdCode.CODENAME, codeName)
                .eq(StrUtil.isNotBlank(codeValue), StdCode.CODEVALUE, codeValue)
                .eq(StdCode.USEABLE, "0")
                .orderByAsc(StdCode.ORDERINDEX);
        return selectList(queryWrapper);
    }

    default List<StdCode> findStdCodeByStdTypesAndCodeNameAndCodeValues(Collection<? extends Serializable> stdTypes,
                                                                        String codeName,
                                                                        Collection<? extends Serializable> codeValues) {
        QueryWrapper<StdCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(ObjectUtil.isNotEmpty(stdTypes), StdCode.STDTYPE, stdTypes)
                .eq(StrUtil.isNotBlank(codeName), StdCode.CODENAME, codeName)
                .in(ObjectUtil.isNotEmpty(codeValues), StdCode.CODEVALUE, codeValues)
                .eq(StdCode.USEABLE, "0")
                .orderByAsc(StdCode.ORDERINDEX);
        return selectList(queryWrapper);
    }

//    default List<StdCode> findStdCodeByStdType() {
//        Set<String> openType = menuTreeVOS.stream().map(MenuTreeVO::getOpenType).collect(Collectors.toSet());
//        QueryWrapper<StdCode> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq(StdCode.STDTYPE, "menuOpenWay")
//                .in(StdCode.CODEVALUE, openType);
//        return selectList(queryWrapper);
//    }
}
