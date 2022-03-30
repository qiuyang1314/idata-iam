package com.zyaud.idata.iam.biz.mapper;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyaud.idata.iam.api.req.DTO.RemoteOrgCreateReqDTO;
import com.zyaud.idata.iam.biz.model.entity.Office;

import java.util.List;

/**
 * <p>
 * OfficeMapper 接口
 * 机构部门
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface OfficeMapper extends BaseMapper<Office> {

    default List<Office> listOfficeByNameNeId(String name, String id) {
        QueryWrapper<Office> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(name), Office.NAME, name)
                .ne(StrUtil.isNotBlank(id), Office.ID, id);
        List<Office> offices = selectList(wrapper);
        return offices;
    }

    default List<Office> listOfficeByCodeNeId(String code, String id) {
        QueryWrapper<Office> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(code), Office.CODE, code)
                .ne(StrUtil.isNotBlank(id), Office.ID, id);
        List<Office> offices = selectList(wrapper);
        return offices;
    }

    default List<Office> listOfficeLikePathAndNeId(String paths, String id) {
        QueryWrapper<Office> wrapper = new QueryWrapper<>();
        wrapper.likeRight(Office.PATHS, paths)
                .ne(Office.ID, id);
        return this.selectList(wrapper);
    }

    default List<Office> findOfficeLikePathAndNeId(String paths, String id) {
        QueryWrapper<Office> wrapper = new QueryWrapper<>();
        wrapper.likeRight(Office.PATHS, paths)
                .ne(Office.ID, id);
        return this.selectList(wrapper);
    }

    default List<Office> findAllOffice(){
        QueryWrapper<Office> wrapper = new QueryWrapper<>();
        return this.selectList(wrapper);
    }

    default List<Office> findOfficeLikePathAndId(String paths, String id) {
        QueryWrapper<Office> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StrUtil.isNotBlank(paths), Office.PATHS, paths)
                .eq(StrUtil.isNotBlank(id), Office.ID, id);
        return this.selectList(wrapper);
    }

    default  List<Office> getOfficeListByType(String orgType){
        QueryWrapper<Office> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Office.OTYPE,orgType);
        return this.selectList(queryWrapper);
    }

    /**
     * @Description 获取同步appframe机构信息
     * @Author qiuyang
     * @return
     * @Date 2022/1/10 10:44
     **/
    List<RemoteOrgCreateReqDTO> getAppframeSyncOrg();
}
