package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import com.zyaud.fzhx.core.model.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

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
public class ProblemFeedbackPageDTO extends PageParam {

    private static final long serialVersionUID=1L;
    /**
     * 意见标题
     */
    @BindQuery(field = "opinion_title", comparison = Comparison.LIKE)
    @Length(max = 255, message = "意见标题长度不能超过255")
    private String opinionTitle;



    /**
     * 问题系统
     */
    @BindQuery(field = "issue_system", comparison = Comparison.LIKE)
    @Length(max = 255, message = "问题系统长度不能超过100")
    private String issueSystem;



    /**
     * 问题详情
     */
    @BindQuery(field = "issue_details", comparison = Comparison.LIKE)
    @Length(max = 255, message = "问题详情长度不能超过1000")
    private String issueDetails;


    /**
     * 状态
     */
    @BindQuery(field = "state", comparison = Comparison.LIKE)
    @Length(max = 255, message = "状态长度不能超过1")
    private String state;



}
