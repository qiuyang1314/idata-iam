package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyaud.idata.iam.biz.mapper.StdCodeMapper;
import com.zyaud.idata.iam.biz.model.entity.StdCode;
import com.zyaud.idata.iam.biz.service.IStdCodeService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典编码 服务实现类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-21
 */
@Service
public class StdCodeServiceImpl extends ServiceImpl<StdCodeMapper, StdCode> implements IStdCodeService {
    @Override
    public Map<String, String> getDictData(String dictType, Set<String> dictValue) {
        QueryWrapper<StdCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(dictType), StdCode.STDTYPE, dictType)
                .in(ObjectUtil.isNotEmpty(dictValue), StdCode.CODEVALUE, dictValue);
        List<StdCode> list = this.list(queryWrapper);
        Map<String, String> map = list.stream()
                .collect(Collectors.toMap(d -> d.getCodeValue(), v -> v.getCodeName()));
        return map;
    }

    @Override
    public List<StdCode> findStdCodeByStdTypesAndCodeNameAndCodeValues(Collection<? extends Serializable> stdTypes, String codeName, Collection<? extends Serializable> codeValues) {
        return baseMapper.findStdCodeByStdTypesAndCodeNameAndCodeValues(stdTypes, codeName, codeValues);
    }


    @Override
    public List<StdCode> getByStdType(List<String> stdTypes, String userable) {
        if (stdTypes.size() == 0) {
            return new ArrayList<>();
        }
        return this.baseMapper.getByStdType(stdTypes, userable);
    }

    @Override
    public List<StdCode> getByStdType(String stdType, String userable) {
        return this.baseMapper.getByStdType(stdType, userable);
    }

}
