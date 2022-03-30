package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.document.FzhxOffice;
import com.zyaud.idata.foundation.util.utils.DownloadFile;
import com.zyaud.idata.foundation.util.utils.ExcelSizeUtil;
import com.zyaud.idata.foundation.util.utils.FileUploadUtil;
import com.zyaud.idata.foundation.util.utils.Methods;
import com.zyaud.idata.iam.biz.enums.FileBizTypeEnum;
import com.zyaud.idata.iam.biz.mapper.FileInfoMapper;
import com.zyaud.idata.iam.biz.model.entity.FileInfo;
import com.zyaud.idata.iam.biz.model.vo.CheckFileExistRspVO;
import com.zyaud.idata.iam.biz.model.vo.FileGroupNameAndFileIdVO;
import com.zyaud.idata.iam.biz.model.vo.FileStoreInfoVO;
import com.zyaud.idata.iam.biz.model.vo.FileUploadRspVO;
import com.zyaud.idata.iam.biz.service.IFileInfoService;
import com.zyaud.idata.iam.biz.utils.UploadUtils;
import com.zyaud.idata.iam.common.utils.Constants;
import com.zyaud.idata.iam.common.utils.LambdaStreamUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.*;

/**
 * <p>
 * 内审文件信息 服务实现类
 * </p>
 *
 * @author luohuixiang
 * @since 2021-04-22
 */
@Service
@Slf4j
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements IFileInfoService {

    // 考虑换成java.io.userdir，因为tmpdir常遇到权限问题
    public static final String TMPDIR = System.getProperty("java.io.tmpdir");
    @Resource
    private FastFileStorageClient storageClient;

    @Value("${spring.preview_prefix}")
    private static String previewPrefix;

    private void fillFileStoreInfo(CheckFileExistRspVO existRspVO, String fileUrl) {
        if (StrUtil.isNotBlank(fileUrl)) {
            String groupName = fileUrl.substring(0, fileUrl.indexOf("/"));
            String fileId = fileUrl.substring(groupName.length() + 1);
            existRspVO.setGroupName(groupName);
            existRspVO.setFileId(fileId);
        }
    }

    @Override
    public CheckFileExistRspVO exist(Serializable md5) {
        CheckFileExistRspVO existRspVO = new CheckFileExistRspVO();

        List<FileInfo> fileInfos = this.baseMapper.findFileInfoList(md5);
        if (fileInfos.size() < 1) {
            return existRspVO;
        }

        FileInfo fileInfo = fileInfos.get(0);
        existRspVO.setExist(true);
        existRspVO.setFileType(fileInfo.getFtype());
        fillFileStoreInfo(existRspVO, fileInfo.getFurl());

        return existRspVO;
    }

    @Override
    public FileUploadRspVO upload(MultipartFile file) {
        BizAssert.isNotEmpty(file, "请选择上传文件");
        FileUploadRspVO uploadRspVO = new FileUploadRspVO();
        try {
            Map<String, String> fileUrlMap = FileUploadUtil.uploadFile(file.getInputStream(), Constants.IAM_CODE);
            uploadRspVO.setGroupName(fileUrlMap.get("groupName"));
            uploadRspVO.setFileId(fileUrlMap.get("fileId"));
        } catch (Exception e) {
            e.printStackTrace();
            BizAssert.fail("上传文件错误");
        }

        return uploadRspVO;
    }

    @Override
    public FileUploadRspVO upload(String name, String md5, Long size, Integer total, Integer chunk,
                                  MultipartFile file) throws IOException {
        BizAssert.isNotEmpty(file, "请选择上传文件");
        if (ObjectUtil.isNotNull(total) && total > 0) {
            FileUploadRspVO uploadRspVO = new FileUploadRspVO();
            String fileName = UploadUtils.getFileName(md5, total);
            String path = System.getProperty("java.io.tmpdir") + fileName;
            UploadUtils.writeWithBlok(path, size, file.getInputStream(), file.getSize(), total, chunk);
            UploadUtils.addChunk(md5, chunk);
            if (UploadUtils.isUploaded(md5)) {
                Map<String, String> fileUrlMap = FileUploadUtil.uploadFile(new File(path), Constants.IAM_CODE);
                uploadRspVO.setGroupName(fileUrlMap.get("groupName"));
                uploadRspVO.setFileId(fileUrlMap.get("fileId"));
            }

            return uploadRspVO;
        }

        return upload(file);
    }

    /**
     * 依fileUrl检测文件是否存在
     *
     * @param fileUrl
     * @return
     */
    private CheckFileExistRspVO exist(String fileUrl) {
        CheckFileExistRspVO existRspVO = new CheckFileExistRspVO();
        try {
            fillFileStoreInfo(existRspVO, fileUrl);
            existRspVO.setExist(FileUploadUtil.validFile(existRspVO.getGroupName(), existRspVO.getFileId()));
        } catch (Exception e) {
            e.printStackTrace();
            existRspVO.setExist(false);
        }

        return existRspVO;
    }

    @Override
    public String genAndStorePreviewHtml(FileInfo fileInfo) {
        CheckFileExistRspVO existRspVO = exist(fileInfo.getFurl());
        if (!existRspVO.getExist()) {
            BizAssert.fail("文件不存在，请确认是否已上传");
        }

        String htmlUrl = fileInfo.getFurl();
        File downloadFile = FileUploadUtil.getFile(existRspVO.getGroupName(), existRspVO.getFileId());

        if (!Constants.TOVIEWS.contains(fileInfo.getFtype().toLowerCase())) {
            return htmlUrl;
        }

        try {
            //是否是大数据excel
            if (ExcelSizeUtil.whetherOrNotConvertHtml(downloadFile)) {
                return "";
            }
            //不是大数据量excel或不是excel文件
            String id = IdUtil.fastSimpleUUID();
            String htmlFilePath = "";
            try {
                htmlFilePath = FzhxOffice.toHtml(downloadFile.getAbsolutePath(), fileInfo.getFname(), fileInfo.getFtype(), TMPDIR);
            } catch (Throwable e) {
                FileUtil.del(Paths.get(TMPDIR, String.format("%s.html", fileInfo.getFname())));
                e.printStackTrace();
                System.gc();
                log.error("文件:" + fileInfo.getFname() + "(" + id + ")" + "转成HTML失败");
            }

            //返回预览地址
            if (StrUtil.isNotBlank(htmlFilePath)) {
                Map<String, String> map = FileUploadUtil.uploadFile(new File(htmlFilePath), Constants.IAM_CODE);
                return Methods.jointFileUrl(map.get("groupName"), map.get("fileId"));
            } else {
                return fileInfo.getFurl();
            }
        } catch (Exception e) {
            e.printStackTrace();
            BizAssert.fail("生成预览文件失败");
        }

        return htmlUrl;
    }

    @Override
    public boolean batchAddFile(List<FileStoreInfoVO> fileStoreInfoVOS, String bizId, FileBizTypeEnum bizType) {
        List<FileInfo> fileInfos = new ArrayList<>();
        for (FileStoreInfoVO infoVO : fileStoreInfoVOS) {
            FileInfo fileInfo = BeanUtil.toBean(infoVO, FileInfo.class)
                    .setFname(FileUtil.mainName(infoVO.getFileName()))
                    .setFtype(FileUtil.extName(infoVO.getFileName()))
                    .setFurl(StrUtil.join("/", infoVO.getGroupName(), infoVO.getFileId()))
                    .setBizId(bizId)
                    .setFsize(infoVO.getFileSize())
                    .setMd5(infoVO.getFileMd5())
                    .setBizType("" + bizType.getBizCode());

            // 生成预览文件
            fileInfo.setPreviewUrl(genAndStorePreviewHtml(fileInfo));

            fileInfos.add(fileInfo);
        }

        return this.saveBatch(fileInfos);
    }

    @Override
    public void preview(String id, HttpServletResponse response) {
        FileInfo fileInfo = this.getById(id);
        if (ObjectUtil.isEmpty(fileInfo)) {
            try {
                DownloadFile.preview(Methods.getErrMsg("文件不存在").getBytes(), "?", response, previewPrefix);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        byte[] bytes;
        if (StrUtil.isNotBlank(fileInfo.getPreviewUrl())) {
            FileGroupNameAndFileIdVO fileGroupNameAndFileId = FileInfo.getFileGroupNameAndFileId(fileInfo.getPreviewUrl());
            try {
                bytes = FileUploadUtil.getBytes(fileGroupNameAndFileId.getGroupName(), fileGroupNameAndFileId.getFileId());
            } catch (Exception e) {
                e.printStackTrace();
                bytes = Constants.NOTSUPPORT.getBytes();
            }
        } else {
            bytes = Constants.NOTSUPPORT.getBytes();
        }

        try {
            DownloadFile.preview(bytes, fileInfo.getFtype(), response, previewPrefix);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void download(String id, HttpServletResponse response) {
        FileInfo fileInfo = this.getById(id);
        if (ObjectUtil.isEmpty(fileInfo)) {
            DownloadFile.getMsg(response, "文件不存在");
            return;
        }

        FileGroupNameAndFileIdVO fileGroupNameAndFileId = FileInfo.getFileGroupNameAndFileId(fileInfo.getFurl());
        if (!FileUploadUtil.validFile(fileGroupNameAndFileId.getGroupName(), fileGroupNameAndFileId.getFileId())) {
            DownloadFile.getMsg(response, "文件不存在");
            return;
        }

        File file = FileUploadUtil.getFile(fileGroupNameAndFileId.getGroupName(), fileGroupNameAndFileId.getFileId());
        DownloadFile.downloadFile(FileUtil.getInputStream(file),
                fileInfo.getFname() + "." + fileInfo.getFtype(), response, false);
        file.delete();
    }

    @Override
    public List<FileInfo> findFileListByBizIds(Collection<? extends Serializable> bizIds) {
        return this.baseMapper.findFileInfoListByBizIdsAndFileName(bizIds, null);
    }

    @Override
    public List<FileInfo> findFileInfoListByBizIdsAndFileName(List<String> bizIds, String fileName) {
        return this.baseMapper.findFileInfoListByBizIdsAndFileName(bizIds, fileName);
    }

    @Override
    public int deleteByBizIds(List<String> ids) {
        return this.baseMapper.deleteByBizIds(ids);
    }

    @Override
    public List<FileInfo> findFileInfoList(String fileName, FileBizTypeEnum bizTypeEnum) {
        return this.baseMapper.findFileInfoList(fileName, bizTypeEnum);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFiles(List<FileStoreInfoVO> bizFiles, String bizId, FileBizTypeEnum bizTypeEnum) {

        // 查询原匹配的文件
        List<FileInfo> fileInfos = findFileListByBizIds(new ArrayList<String>() {{
            add(bizId);
        }});

        // 过滤要删除的文件
        Set<FileInfo> toDelFileInfos = new HashSet<>();
        fileInfos.forEach(fi -> {
            if (bizFiles.stream().noneMatch(
                    p -> p.getFileName().equals(fi.getFname()) && p.getFileMd5().equals(fi.getMd5()))) {
                toDelFileInfos.add(fi);
            }
        });

        // 删除文件记录
        if (ObjectUtil.isNotEmpty(toDelFileInfos)) {
            deleteByBizIds(LambdaStreamUtil.toList(toDelFileInfos, FileInfo::getBizId));
        }

        // 添加新增的文件
        bizFiles.removeIf(f -> fileInfos.stream().anyMatch(i -> f.getFileMd5().equals(i.getMd5()) &&
                f.getFileName().equals(i.getFname())));
        if (bizFiles.size() > 0) {
            batchAddFile(bizFiles, bizId, bizTypeEnum);
        }
    }
}
