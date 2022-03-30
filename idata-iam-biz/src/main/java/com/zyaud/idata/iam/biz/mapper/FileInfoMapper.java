package com.zyaud.idata.iam.biz.mapper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyaud.idata.iam.biz.enums.FileBizTypeEnum;
import com.zyaud.idata.iam.biz.model.entity.FileInfo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 内审文件信息 Mapper 接口
 * </p>
 *
 * @author luohuixiang
 * @since 2021-04-22
 */
public interface FileInfoMapper extends BaseMapper<FileInfo> {
    default List<FileInfo> findFileInfoList(Serializable md5) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FileInfo.MD5, md5);
        return this.selectList(queryWrapper);
    }

    default List<FileInfo> findFileInfoListByBizIdsAndFileName(Collection<? extends Serializable> bizIds, String fileName) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(ObjectUtil.isNotEmpty(bizIds), FileInfo.BIZ_ID, bizIds);
        queryWrapper.like(StrUtil.isNotBlank(fileName), FileInfo.FNAME, fileName);
        queryWrapper.orderByAsc(FileInfo.CREATE_TIME);
        return this.selectList(queryWrapper);
    }

    default List<FileInfo> findFileInfoList(String fileName, FileBizTypeEnum bizTypeEnum) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(FileInfo.FNAME, fileName);
        queryWrapper.eq(FileInfo.BIZ_TYPE, "" + bizTypeEnum.getBizCode());
        return this.selectList(queryWrapper);
    }

    default int deleteByBizIds(List<String> bizIds) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(FileInfo.BIZ_ID, bizIds);
        return this.delete(queryWrapper);
    }
}
