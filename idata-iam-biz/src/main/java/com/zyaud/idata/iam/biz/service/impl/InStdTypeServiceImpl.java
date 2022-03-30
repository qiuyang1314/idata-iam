package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.idata.iam.biz.mapper.StdCodeMapper;
import com.zyaud.idata.iam.biz.mapper.StdTypeMapper;
import com.zyaud.idata.iam.biz.model.dto.StdTypeCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.StdTypeUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.StdCode;
import com.zyaud.idata.iam.biz.model.entity.StdType;
import com.zyaud.idata.iam.biz.service.IInStdCodeService;
import com.zyaud.idata.iam.biz.service.IInStdTypeService;
import com.zyaud.idata.iam.common.errorcode.DictMngErrorEnum;
import com.zyaud.idata.iam.common.utils.Constants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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
public class InStdTypeServiceImpl extends ServiceImpl<StdTypeMapper, StdType> implements IInStdTypeService {

    @Resource
    private StdTypeMapper stdTypeMapper;

    @Resource
    private StdCodeMapper stdCodeMapper;

    @Resource
    private IInStdCodeService stdCodeService;


    @Override
    public void verifyName(String name, String id) {
        Long count = stdTypeMapper.countByName(name, id);
        if (count > 0) {
            BizAssert.fail(DictMngErrorEnum.STD_TYPE_STD_NAME_IS_EXIST);
        }
    }

    @Override
    public void verifyValue(String value, String id) {
        Long count = stdTypeMapper.countByValue(value, id);
        if (count > 0) {
            BizAssert.fail(DictMngErrorEnum.STD_TYPE_STD_NUM_IS_EXIST);
        }
    }

    @Override
    public void createStdType(StdTypeCreateDTO createDTO) {
        //校验字典名称，字典值是否重复
        this.verifyName(createDTO.getStdName(), null);
        this.verifyValue(createDTO.getStdNum(), null);
        StdType entity = BeanUtil.toBean(createDTO, StdType.class);
        this.save(entity);
    }

    @Override
    public void updateStdType(StdTypeUpdateDTO updateDTO) {
        StdType stdType = this.getById(updateDTO.getId());
        BizAssert.isNotEmpty(stdType, DictMngErrorEnum.STD_TYPE_IS_DELETE_UPDATE_FAIL);
        BizAssert.isFalse(Constants.STD_TYPE_IN.equals(stdType.getStdType()), DictMngErrorEnum.STD_TYPE_IS_ZERO_UPDATE_FAIL);

        //校验字典名称，字典值是否重复
        this.verifyName(updateDTO.getStdName(), updateDTO.getId());
        this.verifyValue(updateDTO.getStdNum(), updateDTO.getId());
        StdType entity = BeanUtil.toBean(updateDTO, StdType.class);

        //如果启用停用状态有变则将下面所有字典项都同步
        if (!entity.getUseable().equals(stdType.getUseable())) {
            List<StdCode> stdCodes = stdCodeMapper.findStdCodeByStdTypesAndCodeNameAndCodeValues(null,entity.getStdType(), null);
            List<StdCode> stdCodeList = stdCodes.stream().map(t -> t.setUseable(entity.getUseable()))
                    .collect(Collectors.toList());
            stdCodeService.updateBatchById(stdCodeList);
        }
        this.updateById(entity);
    }

    @Override
    public void deleteStdType(String stdTypeId) {
        StdType stdType = this.getById(stdTypeId);
        if (ObjectUtil.isNotEmpty(stdType)) {
            BizAssert.isFalse(Constants.STD_TYPE_IN.equals(stdType.getStdType()), DictMngErrorEnum.STD_TYPE_IS_ZERO_UPDATE_FAIL);
            stdCodeMapper.deleteStdCodeByStdType(stdType.getStdType());
            this.removeById(stdTypeId);
        }
    }
}
