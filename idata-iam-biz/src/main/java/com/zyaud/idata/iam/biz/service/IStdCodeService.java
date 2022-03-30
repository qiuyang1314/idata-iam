package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.biz.model.entity.StdCode;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 字典编码 服务类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-21
 */
public interface IStdCodeService extends IService<StdCode> {
    Map<String, String> getDictData(String dictType, Set<String> dictValue);

    List<StdCode> findStdCodeByStdTypesAndCodeNameAndCodeValues(Collection<? extends Serializable> stdTypes,
                                                                String codeName,
                                                                Collection<? extends Serializable> codeValues);


    List<StdCode> getByStdType(List<String> stdTypes, String userable);

    List<StdCode> getByStdType(String stdType, String userable);
}
