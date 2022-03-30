package com.zyaud.idata.iam.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.biz.enums.FileBizTypeEnum;
import com.zyaud.idata.iam.biz.model.entity.FileInfo;
import com.zyaud.idata.iam.biz.model.vo.CheckFileExistRspVO;
import com.zyaud.idata.iam.biz.model.vo.FileStoreInfoVO;
import com.zyaud.idata.iam.biz.model.vo.FileUploadRspVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 内审文件信息 服务类
 * </p>
 *
 * @author luohuixiang
 * @since 2021-04-22
 */
public interface IFileInfoService extends IService<FileInfo> {
    /**
     * 根据md5值检测文件是否已经上传(有碰撞率)
     * @param md5
     * @return
     */
    CheckFileExistRspVO exist(Serializable md5);

    /**
     * 上传文件
     * @param file
     * @return
     */
    FileUploadRspVO upload(MultipartFile file);

    FileUploadRspVO upload(String name,
                       String md5,
                       Long size,
                       Integer total,
                       Integer chunk,
                       MultipartFile file) throws IOException;

    /**
     * 产生预览html并且存储到fastdfs
     * @param fileInfo
     * @return
     */
    String genAndStorePreviewHtml(FileInfo fileInfo);


    /**
     * 批量插入文件信息
     * @param fileStoreInfoVOS
     * @param bizId
     * @param bizType
     */
    boolean batchAddFile(List<FileStoreInfoVO> fileStoreInfoVOS, String bizId, FileBizTypeEnum bizType);

    /**
     * 预览
     * @param id
     * @param response
     */
    void preview(String id, HttpServletResponse response);

    /**
     * 下载
     * @param id
     * @param response
     */
    void download(String id, HttpServletResponse response);

    /**
     * 根据业务ID批量查询
     * @param bizIds
     * @return
     */
    List<FileInfo> findFileListByBizIds(Collection<? extends Serializable> bizIds);

    /**
     * 根据业务id和文件名过滤
     * @param bizIds
     * @param fileName
     * @return
     */
    List<FileInfo> findFileInfoListByBizIdsAndFileName(List<String> bizIds, String fileName);

    /**
     * 按业务id删除相关文件
     * @param ids
     * @return
     */
    int deleteByBizIds(List<String> ids);

    /**
     * 根据业务类型和文件名过滤
     * @param fileName
     * @param bizTypeEnum
     * @return
     */
    List<FileInfo> findFileInfoList(String fileName, FileBizTypeEnum bizTypeEnum);

    /**
     * 更新业务对应的文件
     * @param bizFiles
     * @param bizId
     * @param bizTypeEnum
     */
    void updateFiles(List<FileStoreInfoVO> bizFiles, String bizId, FileBizTypeEnum bizTypeEnum);
}
