package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.common.utils.TreeUtils;
import com.zyaud.idata.iam.biz.mapper.*;
import com.zyaud.idata.iam.client.dto.OfficeInDTO;
import com.zyaud.idata.iam.biz.model.entity.Office;
import com.zyaud.idata.iam.biz.model.entity.StdCode;
import com.zyaud.idata.iam.biz.model.vo.OfficeTreeVO;
import com.zyaud.idata.iam.biz.service.IInOfficeService;
import com.zyaud.idata.iam.biz.service.IStdCodeService;
import com.zyaud.idata.iam.common.errorcode.OfficeMngErrorEnum;
import com.zyaud.idata.iam.common.utils.Constants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 机构部门 服务实现类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
@Service
public class InOfficeServiceImpl extends ServiceImpl<OfficeMapper, Office> implements IInOfficeService {
    @Resource
    private IStdCodeService stdCodeService;

    @Resource
    private OfficeMapper officeMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserCodeMapper userCodeMapper;

    @Resource
    private RoleUserCodeMapper roleUserCodeMapper;

    @Resource
    private AppMapper appMapper;

    @Override
    public Map<String, String> getOfficeNameById(Set<Serializable> ids) {
        if (ObjectUtil.isNotEmpty(ids)) {
            List<Office> offices = this.getBaseMapper().selectBatchIds(ids);
            Map<String, String> collect = offices.stream().collect(Collectors.toMap(t -> t.getId(), t -> t.getName()));
            return collect;
        } else {
            return new HashMap<>();
        }
    }

    @Override
    public void verifyName(String name, String id) {
        List<Office> offices = officeMapper.listOfficeByNameNeId(name, id);
        BizAssert.isFalse(offices.size() > 0, OfficeMngErrorEnum.OFFICE_NAME_IS_EXIST.getCode(),
                "操作失败:机构名称" + name + "已存在");
    }

    @Override
    public void verifyCode(String code, String id) {
        List<Office> offices = officeMapper.listOfficeByCodeNeId(code, id);
        BizAssert.isFalse(offices.size() > 0, OfficeMngErrorEnum.OFFICE_CODE_IS_EXIST.getCode(),
                "操作失败:机构编码" + code + "已存在");
    }

    @Override
    public void createOffice(Office entity) {
        this.verifyName(entity.getName(), null);
        this.verifyCode(entity.getCode(), null);

        if (ObjectUtil.isEmpty(entity.getParentId())) {
            entity.setParentId(Constants.ROOTID)
                    .setPaths(entity.getCode() + "/");
        } else {
            Office officeParent = this.getById(entity.getParentId());
            BizAssert.isFalse(ObjectUtil.isEmpty(officeParent), OfficeMngErrorEnum.PARENT_OFFICE_IS_DELETE);
            entity.setPaths(entity.getPaths() + entity.getCode() + "/");
        }
        this.save(entity);
    }

    @Override
    public void updateOffice(Office entity) {
        verifyName(entity.getName(), entity.getId());
        verifyCode(entity.getCode(), entity.getId());
        Office office = this.getById(entity.getId());
        BizAssert.isFalse(ObjectUtil.isEmpty(office), OfficeMngErrorEnum.OFFICE_IS_DELETE);
        //验证父级
        if (ObjectUtil.isEmpty(entity.getParentId())) {
            entity.setParentId(Constants.ROOTID)
                    .setPaths(entity.getCode() + "/");
        } else {
            Office parentOffice = this.getById(entity.getParentId());
            BizAssert.isFalse(ObjectUtil.isEmpty(parentOffice), OfficeMngErrorEnum.PARENT_OFFICE_IS_DELETE);
            entity.setPaths(parentOffice.getPaths() + entity.getCode() + "/");
        }

        //当前级code有变化
        if (!entity.getCode().equals(office.getCode()) && StrUtil.isNotEmpty(office.getPaths())) {
            List<Office> officeList = officeMapper.listOfficeLikePathAndNeId(office.getPaths(), office.getId());
            //将当前级子集的paths更新
            officeList.forEach(t -> t.setPaths(t.getPaths().replace(office.getPaths(), entity.getPaths())));
            this.updateBatchById(officeList);
        }
        this.updateById(entity);

    }

    @Override
    public List<Office> findByCodes(List<String> codes) {
        if (codes.size() > 0) {
            QueryWrapper<Office> queryWrapper = new QueryWrapper<>();
            queryWrapper.in(Office.CODE, codes);
            return this.list(queryWrapper);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<OfficeTreeVO> getOfficeTree(List<Office> list) {
        List<OfficeTreeVO> officeTreeVOS = new ArrayList<>();
        if (list.size() > 0) {
            list.forEach(t -> officeTreeVOS.add(BeanUtil.toBean(t, OfficeTreeVO.class).setParentOfficeId(t.getParentId())));
            //父级id
            Set<String> parentOfficeIds = officeTreeVOS.stream().map(OfficeTreeVO::getParentOfficeId).collect(Collectors.toSet());
            List<Office> offices = listByIds(parentOfficeIds);

            //机构类型
            Set<String> otypes = officeTreeVOS.stream().map(OfficeTreeVO::getOtype).collect(Collectors.toSet());
            //机构级别
            Set<String> levels = officeTreeVOS.stream().map(OfficeTreeVO::getLevels).collect(Collectors.toSet());
            //封装统一查询条件
            otypes.addAll(levels);
            List<StdCode> stdCodes = stdCodeService.list();
            //机构类型
            Set<StdCode> orgType = stdCodes.stream().filter(t -> Constants.DICTNAME_ORGTYPE.equals(t.getStdType())).collect(Collectors.toSet());
            //机构级别
            Set<StdCode> orgRank = stdCodes.stream().filter(t -> Constants.DICTNAME_ORGRANK.equals(t.getStdType())).collect(Collectors.toSet());

            for (OfficeTreeVO officeTreeVO : officeTreeVOS) {
                Optional<Office> first = offices.stream().filter(t -> t.getId().equals(officeTreeVO.getParentOfficeId())).findFirst();
                first.ifPresent(t -> officeTreeVO.setParentName(t.getName()));

                Optional<StdCode> first1 = orgType.stream().filter(t -> t.getCodeValue().equals(officeTreeVO.getOtype())).findFirst();
                first1.ifPresent(t -> officeTreeVO.setOtypeName(t.getCodeName()));

                Optional<StdCode> first2 = orgRank.stream().filter(t -> t.getCodeValue().equals(officeTreeVO.getLevels())).findFirst();
                first2.ifPresent(t -> officeTreeVO.setLevelsName(t.getCodeName()));
            }
        }
        return TreeUtils.buildByRecursive(officeTreeVOS, Constants.ROOTID);
    }

    /**
     * 通过机构类型获取机构列表
     *
     * @param orgType
     * @return
     */
    @Override
    public List<OfficeInDTO> getOfficeListByType(String orgType) {
        List<Office> officeListByType = baseMapper.getOfficeListByType(orgType);
        List<OfficeInDTO> list = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(officeListByType)){
            for (Office office: officeListByType) {
                list.add(BeanUtil.toBean(office,OfficeInDTO.class));
            }
        }
        return list;
    }

}
