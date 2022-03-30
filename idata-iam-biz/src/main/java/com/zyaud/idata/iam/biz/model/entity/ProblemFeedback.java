package com.zyaud.idata.iam.biz.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zyaud.fzhx.core.model.TimeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 问题反馈表
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(ProblemFeedback.PROBLEMFEEDBACK)
public class ProblemFeedback extends TimeEntity<String> {

    private static final long serialVersionUID=1L;
    public static final String PROBLEMFEEDBACK = "iam_problem_feedback";
    public static final String OPINIONTITLE = "opinion_title";
    public static final String ISSUESYSTEM = "issue_system";
    public static final String ISSUEDETAILS = "issue_details";
    public static final String STATE = "state";


    /**
     * 意见标题
     */
    @TableField(OPINIONTITLE)
    @Length(max = 255, message = "意见标题长度不能超过255")
    private String opinionTitle;


    /**
     * 问题系统
     */
    @TableField(ISSUESYSTEM)
    @Length(max = 100, message = "问题系统长度不能超过100")
    private String issueSystem;


    /**
     * 问题详情
     */
    @TableField(ISSUEDETAILS)
    @Length(max = 1000, message = "问题详情长度不能超过1000")
    private String issueDetails;


    /**
     * 任务状态
     */
    @TableField(STATE)
    @Length(max = 1, message = "任务状态长度不能超过1")
    private String state;



}
