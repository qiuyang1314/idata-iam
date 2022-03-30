package com.zyaud.idata.iam.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyaud.idata.iam.biz.mapper.StdTypeMapper;
import com.zyaud.idata.iam.biz.model.entity.StdType;
import com.zyaud.idata.iam.biz.service.IStdTypeService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典管理 服务实现类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-21
 */
@Service
public class StdTypeServiceImpl extends ServiceImpl<StdTypeMapper, StdType> implements IStdTypeService {

    @Override
    public Map<String, String> getByCodeType(Set<Serializable> codeTypes) {
        QueryWrapper<StdType> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(StdType.STDTYPE, codeTypes);
        List<StdType> list = this.list(queryWrapper);
        Map<String, String> collect = list.stream().collect(Collectors.toMap(t -> t.getStdType(), t -> t.getStdName()));
        return collect;
    }

    @Override
    public StdType getByStdNum(String stdNum) {
        StdType stdType = new StdType();
        try {
            stdType = this.baseMapper.getByStdNum(stdNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stdType;
    }
}
