package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.biz.model.entity.StdType;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 字典管理 服务类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-21
 */
public interface IStdTypeService extends IService<StdType> {
    Map<String, String> getByCodeType(Set<Serializable> sodeTypes);

    StdType getByStdNum(String stdNum);
}
