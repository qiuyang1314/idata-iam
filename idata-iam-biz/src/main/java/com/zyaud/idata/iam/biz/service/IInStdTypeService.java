package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.biz.model.dto.StdTypeCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.StdTypeUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.StdType;

/**
 * <p>
 * 字典管理 服务类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-21
 */
public interface IInStdTypeService extends IService<StdType> {

    void verifyName(String name, String id);

    void verifyValue(String value, String id);

    void createStdType(StdTypeCreateDTO createDTO);

    void updateStdType(StdTypeUpdateDTO updateDTO);

    void deleteStdType(String stdTypeId);
}
