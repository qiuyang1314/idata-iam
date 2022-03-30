package com.zyaud.idata.iam.biz.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyaud.idata.iam.biz.model.entity.Download;

/**
 * <p>
 * DownloadMapper 接口
 * 下载中心
 * </p>
 *
 * @author fzhx-aicode
 * @since 2021-05-20
 */
public interface DownloadMapper extends BaseMapper<Download> {
    default Download getDownload(String appCode, String taskId) {
        QueryWrapper<Download> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Download.APPCODE, appCode);
        queryWrapper.eq(Download.TASKID, taskId);
        return this.selectOne(queryWrapper);
    }
}
