package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyaud.idata.iam.biz.mapper.DownloadMapper;
import com.zyaud.idata.iam.biz.model.dto.DownloadCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.DownloadUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.Download;
import com.zyaud.idata.iam.biz.service.IDownloadService;
import com.zyaud.idata.iam.common.utils.Constants;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 下载中心 服务实现类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2021-05-20
 */
@Service
public class DownloadServiceImpl extends ServiceImpl<DownloadMapper, Download> implements IDownloadService {

    @Override
    public boolean create(DownloadCreateDTO createDTO) {
        Download download = BeanUtil.toBean(createDTO, Download.class);
        download.setStatus(Constants.DOWNLOADSTATUS_INIT);
        return this.save(download);
    }

    @Override
    public boolean update(DownloadUpdateDTO updateDTO) {
        Download download = this.baseMapper.getDownload(updateDTO.getAppCode(), updateDTO.getTaskId());
        if (ObjectUtil.isEmpty(download)) {
            return false;
        }

        download.setFpath(updateDTO.getFpath());
        download.setMd5(updateDTO.getMd5());
        download.setFsize(updateDTO.getFsize());
        download.setStatus(updateDTO.getStatus());
        return this.updateById(download);
    }
}
