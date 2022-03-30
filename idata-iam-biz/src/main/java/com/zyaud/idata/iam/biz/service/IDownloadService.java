package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.biz.model.dto.DownloadCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.DownloadUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.Download;

/**
 * <p>
 * 下载中心 服务类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2021-05-20
 */
public interface IDownloadService extends IService<Download> {

    boolean create(DownloadCreateDTO createDTO);
    boolean update(DownloadUpdateDTO updateDTO);
}
