package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.biz.model.dto.StdCodeCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.StdCodeUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.StdCode;

import java.util.List;

/**
 * <p>
 * 字典编码 服务类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-21
 */
public interface IInStdCodeService extends IService<StdCode> {
    StdCode getStdCodeByStdTypeAndValue(String stdType, String value);

    List<StdCode> getStdCodeListByStdType(String stdType);

    void verifyName(String stdType, String name, String id);

    void verifyValue(String stdType, String value, String id);

    void createStdCode(StdCodeCreateDTO createDTO);

    void updateStdCode(StdCodeUpdateDTO updateDTO);

    void deleteStdCodeByStdCodeIds(List<String> stdCodeIds);

}
