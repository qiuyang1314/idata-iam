package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.common.utils.TreeUtils;
import com.zyaud.fzhx.core.mybatis.QueryBuilder;
import com.zyaud.idata.foundation.infra.client.client.SysAreaClient;
import com.zyaud.idata.foundation.infra.client.vo.SysAreaListRspVO;
import com.zyaud.idata.iam.biz.mapper.OfficeMapper;
import com.zyaud.idata.iam.biz.mapper.UserMapper;
import com.zyaud.idata.iam.biz.model.dto.OfficeCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.OfficePageDTO;
import com.zyaud.idata.iam.biz.model.dto.OfficeUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.Office;
import com.zyaud.idata.iam.biz.model.entity.StdCode;
import com.zyaud.idata.iam.biz.model.entity.User;
import com.zyaud.idata.iam.biz.model.vo.OfficeTreeVO;
import com.zyaud.idata.iam.biz.service.IOfficeService;
import com.zyaud.idata.iam.biz.service.IStdCodeService;
import com.zyaud.idata.iam.biz.service.IUserService;
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
public class OfficeServiceImpl extends ServiceImpl<OfficeMapper, Office> implements IOfficeService {
    @Resource
    private IStdCodeService stdCodeService;
    @Resource
    private UserMapper userMapper;

    @Resource
    private SysAreaService sysAreaService;

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
    public void repetitionName(String name, String id) {
        QueryWrapper<Office> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Office.NAME, name)
                .ne(StrUtil.isNotEmpty(id), Office.ID, id);
        if (this.count(queryWrapper) > 0) {
            BizAssert.fail(OfficeMngErrorEnum.OFFICE_NAME_IS_EXIST);
        }
    }

    @Override
    public void repetitionCode(String code, String id) {
        QueryWrapper<Office> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Office.CODE, code)
                .ne(StrUtil.isNotEmpty(id), Office.ID, id);
        if (this.count(queryWrapper) > 0) {
            BizAssert.fail(OfficeMngErrorEnum.OFFICE_CODE_IS_EXIST);
        }
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
            QueryWrapper<StdCode> queryWrapper = new QueryWrapper<>();
            queryWrapper.in(StdCode.STDTYPE, new ArrayList<String>() {{
                add("orgType");
                add("orgRank");
            }}).in(StdCode.CODEVALUE, otypes);
            List<StdCode> stdCodes = stdCodeService.list(queryWrapper);
            //机构类型
            Set<StdCode> orgType = stdCodes.stream().filter(t -> t.getStdType().equals("orgType")).collect(Collectors.toSet());
            //机构级别
            Set<StdCode> orgRank = stdCodes.stream().filter(t -> t.getStdType().equals("orgRank")).collect(Collectors.toSet());
            //行政区划
            Map<String, String> areaNameMap = sysAreaService.getAreaNameMap();

            for (OfficeTreeVO officeTreeVO : officeTreeVOS) {
                Optional<Office> first = offices.stream().filter(t -> t.getId().equals(officeTreeVO.getParentOfficeId())).findFirst();
                first.ifPresent(t -> officeTreeVO.setParentName(t.getName()));

                Optional<StdCode> first1 = orgType.stream().filter(t -> t.getCodeValue().equals(officeTreeVO.getOtype())).findFirst();
                first1.ifPresent(t -> officeTreeVO.setOtypeName(t.getCodeName()));

                Optional<StdCode> first2 = orgRank.stream().filter(t -> t.getCodeValue().equals(officeTreeVO.getLevels())).findFirst();
                first2.ifPresent(t -> officeTreeVO.setLevelsName(t.getCodeName()));

                officeTreeVO.setDistrictName(areaNameMap.get(officeTreeVO.getDistrictId()));
            }
        }
        return TreeUtils.buildByRecursive(officeTreeVOS, Constants.ROOTID);
    }


    @Override
    public boolean createOffice(OfficeCreateDTO createDTO) {
        repetitionName(createDTO.getName(), null);
        repetitionCode(createDTO.getCode(), null);
        Office entity = BeanUtil.toBean(createDTO, Office.class);
        if (ObjectUtil.isEmpty(createDTO.getParentId())) {
            entity.setParentId(Constants.ROOTID)
                    .setPaths(entity.getCode() + StrUtil.SLASH);
        } else {
            Office office = this.getById(createDTO.getParentId());
            BizAssert.isTrue(ObjectUtil.isNotEmpty(office), OfficeMngErrorEnum.PARENT_OFFICE_IS_NULL);
            entity.setPaths(office.getPaths() + entity.getCode() + StrUtil.SLASH);
        }
        return this.save(entity);
    }

    @Override
    public void updateOffice(OfficeUpdateDTO updateDTO) {
        repetitionName(updateDTO.getName(), updateDTO.getId());
        repetitionCode(updateDTO.getCode(), updateDTO.getId());
        Office office = this.getById(updateDTO.getId());
        BizAssert.isTrue(ObjectUtil.isNotEmpty(office), OfficeMngErrorEnum.OFFICE_IS_NULL);
        Office entity = BeanUtil.toBean(updateDTO, Office.class);
        //验证父级
        if (ObjectUtil.isEmpty(updateDTO.getParentId())) {
            entity.setParentId(Constants.ROOTID)
                    .setPaths(entity.getCode() + StrUtil.SLASH);
        } else {
            Office parentOffice = this.getById(updateDTO.getParentId());
            BizAssert.isTrue(ObjectUtil.isNotEmpty(parentOffice), OfficeMngErrorEnum.PARENT_OFFICE_IS_NULL);
            entity.setPaths(parentOffice.getPaths() + entity.getCode() + StrUtil.SLASH);
        }

        //当前级code有变化
        if (!entity.getCode().equals(office.getCode()) && StrUtil.isNotEmpty(office.getPaths())) {
            QueryWrapper<Office> queryWrapper = new QueryWrapper<>();
            queryWrapper.likeRight(Office.PATHS, office.getPaths())
                    .ne(Office.ID, office.getId());
            List<Office> officeList = this.list(queryWrapper);
            //将当前级子集的paths更新
            officeList.forEach(t -> t.setPaths(t.getPaths().replace(office.getPaths(), entity.getPaths())));
            this.updateBatchById(officeList);
        }
        this.updateById(entity);
    }

    @Override
    public boolean deleteOfficeByIds(List<String> ids) {
        int count = 0;
        for (String id : ids) {
            List<User> users = userMapper.findUserListByOfficeId(id);
            if (ObjectUtil.isNotEmpty(users)) {
                count++;
                continue;
            }
            this.removeById(id);
        }
        BizAssert.isTrue(count == 0, "机构下有关联用户无法删除！");
        return true;
    }

    @Override
    public List<OfficeTreeVO> getOfficeList(OfficePageDTO pageDTO) {
        QueryWrapper<Office> query = QueryBuilder.queryWrapper(pageDTO);
        List<Office> list = this.list(query);
        if (list.size() > 0) {
            //处理所有树
            List<String> collect = list.stream().map(Office::getPaths).collect(Collectors.toList());
            List<String> codes = new ArrayList<>();
            for (String s : collect) {
                codes.addAll(Arrays.asList(s.split("/")));
            }
            List<Office> offices = findByCodes(codes);
            return getOfficeTree(offices);
        }
        return new ArrayList<>();
    }

}
