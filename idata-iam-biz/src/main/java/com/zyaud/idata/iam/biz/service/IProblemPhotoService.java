package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.biz.model.entity.ProblemPhoto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 问题反馈图片关联表 服务类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
public interface IProblemPhotoService extends IService<ProblemPhoto> {
    void deleteByIssueIds(List<String> issueIds);

    void createProblemPhoto(MultipartFile[] multipartFiles, String issueId) throws IOException;

    List<ProblemPhoto> getProblemPhotosByIssueId(String issueId);
}
