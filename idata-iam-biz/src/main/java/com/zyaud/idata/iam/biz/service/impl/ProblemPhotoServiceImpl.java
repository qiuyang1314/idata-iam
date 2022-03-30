package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.zyaud.idata.foundation.util.utils.FileUploadUtil;
import com.zyaud.idata.iam.biz.mapper.ProblemPhotoMapper;
import com.zyaud.idata.iam.biz.model.entity.ProblemPhoto;
import com.zyaud.idata.iam.biz.service.IProblemPhotoService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 问题反馈图片关联表 服务实现类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
@Service
public class ProblemPhotoServiceImpl extends ServiceImpl<ProblemPhotoMapper, ProblemPhoto> implements IProblemPhotoService {
    @Resource
    private FastFileStorageClient storageClient;

    @Override
    public void deleteByIssueIds(List<String> issueIds) {
        baseMapper.deleteByIssueIds(issueIds);
    }

    @Override
    public void createProblemPhoto(MultipartFile[] multipartFiles, String issueId) throws IOException {
        if (ObjectUtil.isNotEmpty(multipartFiles)) {
            List<ProblemPhoto> problemPhotos = new ArrayList<>();
            for (MultipartFile multipartFile : multipartFiles) {
                String fileName = multipartFile.getOriginalFilename();
                String extension = FilenameUtils.getExtension(fileName);
                Map<String, String> map = FileUploadUtil.uploadFile(multipartFile, extension);
                ProblemPhoto problemPhoto = new ProblemPhoto();
                problemPhoto.setFileId(map.get("fileId"))
                        .setGroupName(map.get("groupName"))
                        .setIssueId(issueId);
                problemPhotos.add(problemPhoto);
            }
            this.saveBatch(problemPhotos);
        }
    }

    @Override
    public List<ProblemPhoto> getProblemPhotosByIssueId(String issueId) {
        return baseMapper.getProblemPhotosByIssueId(issueId);
    }
}
