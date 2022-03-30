package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.idata.iam.biz.mapper.StdCodeMapper;
import com.zyaud.idata.iam.biz.model.dto.StdCodeCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.StdCodeUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.StdCode;
import com.zyaud.idata.iam.biz.service.IInStdCodeService;
import com.zyaud.idata.iam.common.errorcode.DictMngErrorEnum;
import com.zyaud.idata.iam.common.utils.Constants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.zyaud.idata.iam.common.errorcode.DictMngErrorEnum.STD_CODE_IS_NULL;

/**
 * <p>
 * 字典编码 服务实现类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-21
 */
@Service
public class InStdCodeServiceImpl extends ServiceImpl<StdCodeMapper, StdCode> implements IInStdCodeService {

    @Resource
    private StdCodeMapper stdCodeMapper;

    @Override
    public void verifyName(String stdType, String name, String id) {
        List<StdCode> stdCodes = stdCodeMapper.findStdCodeByStdTypeAndCodeNameAndCodeValueNeId(stdType, name, null, id);
        BizAssert.isFalse(stdCodes.size() > 0, DictMngErrorEnum.STD_CODE_NAME_IS_EXIST.getCode(),
                "操作失败：字典项名称" + name + "已存在");
    }

    @Override
    public void verifyValue(String stdType, String value, String id) {
        List<StdCode> stdCodes = stdCodeMapper.findStdCodeByStdTypeAndCodeNameAndCodeValueNeId(stdType, null, value, id);
        BizAssert.isFalse(stdCodes.size() > 0, DictMngErrorEnum.STD_CODE_VALUE_IS_EXIST.getCode(),
                "操作失败：字典项值" + value + "已存在");
    }

    @Override
    public void createStdCode(StdCodeCreateDTO createDTO) {
        //检验
        this.verifyName(createDTO.getStdType(), createDTO.getCodeName(), null);
        this.verifyValue(createDTO.getStdType(), createDTO.getCodeValue(), null);
        StdCode entity = BeanUtil.toBean(createDTO, StdCode.class);
        this.save(entity);
    }

    @Override
    public void updateStdCode(StdCodeUpdateDTO updateDTO) {
        StdCode stdCode = this.getById(updateDTO.getId());
        BizAssert.isFalse(ObjectUtil.isEmpty(stdCode), STD_CODE_IS_NULL);
        StdCode entity = BeanUtil.toBean(updateDTO, StdCode.class);
        this.verifyName(entity.getStdType(), entity.getCodeName(), entity.getId());
        this.verifyValue(entity.getStdType(), entity.getCodeValue(), entity.getId());
        this.updateById(entity);
    }


    @Override
    public StdCode getStdCodeByStdTypeAndValue(String stdType, String value) {
        if (StrUtil.isBlank(stdType) || StrUtil.isBlank(value)) {
            return null;
        }
        List<StdCode> stdCodes = stdCodeMapper.findStdCodeByStdTypesAndCodeNameAndCodeValues(Arrays.asList(stdType), null, Arrays.asList(value));
        if (stdCodes.size() > 0) {
            return stdCodes.get(0);
        }
        return null;
    }

    @Override
    public List<StdCode> getStdCodeListByStdType(String stdType) {
        List<StdCode> stdCodes = new ArrayList<>();
        if (StrUtil.isNotBlank(stdType)) {
            stdCodes = stdCodeMapper.findStdCodeByStdTypesAndCodeNameAndCodeValues(Arrays.asList(stdType), null, null);
        }
        return stdCodes;
    }

    @Override
    public void deleteStdCodeByStdCodeIds(List<String> stdCodeIds) {
        List<StdCode> dictDatas = this.listByIds(stdCodeIds);
        Set<String> collect = dictDatas.stream().map(t -> t.getStdType()).collect(Collectors.toSet());
        //取系统默认交集
        collect.retainAll(Constants.getList());
        if (ObjectUtil.isNotEmpty(collect)) {
            List<StdCode> dictDataList = new ArrayList<>();
            for (String s : collect) {
                Optional<StdCode> first = dictDataList.stream().filter(t -> t.getStdType().equals(s)).findFirst();
                first.ifPresent(t -> dictDataList.add(t));
            }
            if (ObjectUtil.isNotEmpty(dictDataList)) {
                String string = "";
                for (StdCode dictData : dictDataList) {
                    string += "字典值:" + dictData.getCodeName() + " 是系统数据,不许删除！";
                }
                BizAssert.fail(DictMngErrorEnum.STD_CODE_NAME_IS_SYS_DATA_CANNOT_DELETE.getCode(),string);
            }
        }
        this.removeByIds(stdCodeIds);
    }
}
