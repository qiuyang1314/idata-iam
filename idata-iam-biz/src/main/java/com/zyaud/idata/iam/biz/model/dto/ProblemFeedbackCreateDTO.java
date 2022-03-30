package com.zyaud.idata.iam.biz.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 问题反馈
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-20
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProblemFeedbackCreateDTO implements Serializable {

    private static final long serialVersionUID=1L;
    /**
     * 意见标题
     */
    @TableField(value = "opinion_title", condition = LIKE)
    @Length(max = 255, message = "意见标题长度不能超过255")
    @NotEmpty(message = "问题标题不能为空")
    private String opinionTitle;



    /**
     * 问题系统
     */
    @TableField(value = "issue_system", condition = LIKE)
    @Length(max = 255, message = "问题系统长度不能超过100")
    @NotEmpty(message = "问题系统不能为空")
    private String issueSystem;



    /**
     * 问题详情
     */
    @TableField(value = "issue_details", condition = LIKE)
    @Length(max = 255, message = "问题详情长度不能超过1000")
    @NotEmpty(message = "问题描述不能为空")
    private String issueDetails;


    /**
     * 状态
     */
    @TableField(value = "state")
    @Length(max = 255, message = "状态长度不能超过1")
    private String state;




}
