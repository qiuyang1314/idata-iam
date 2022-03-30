package com.zyaud.idata.iam.biz.mapper;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyaud.idata.iam.biz.model.entity.ProblemPhoto;

import java.util.List;


/**
 * <p>
 * XsProblemFeedbackMapper 问题反馈
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-11
 */
public interface ProblemPhotoMapper extends BaseMapper<ProblemPhoto> {
    default void deleteByIssueIds(List<String> issueIds) {
        if (issueIds.size() > 0) {
            QueryWrapper<ProblemPhoto> queryWrapper = new QueryWrapper<>();
            queryWrapper.in(ProblemPhoto.ISSUEID, issueIds);
            this.delete(queryWrapper);
        }
    }

    default List<ProblemPhoto> getProblemPhotosByIssueId(String issueId) {
        QueryWrapper<ProblemPhoto> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(issueId), ProblemPhoto.ISSUEID, issueId);
        return this.selectList(queryWrapper);
    }
}
