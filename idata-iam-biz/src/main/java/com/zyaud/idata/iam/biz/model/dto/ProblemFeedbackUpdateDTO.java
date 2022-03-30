package com.zyaud.idata.iam.biz.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zyaud.fzhx.core.model.IdEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 角色菜单
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class ProblemFeedbackUpdateDTO implements Serializable {

    private static final long serialVersionUID=1L;
    /**
     * 系统id
     */
    @NotNull(message = "id不能为空", groups = IdEntity.Update.class)
    private String Id;

    /**
     * 意见标题
     */
    @TableField(value = "opinion_title", condition = LIKE)
    @Length(max = 255, message = "意见标题长度不能超过255")
    private String opinionTitle;



    /**
     * 问题系统
     */
    @TableField(value = "issue_system", condition = LIKE)
    @Length(max = 255, message = "问题系统长度不能超过100")
    private String issueSystem;



    /**
     * 问题详情
     */
    @TableField(value = "issue_details", condition = LIKE)
    @Length(max = 255, message = "问题详情长度不能超过1000")
    private String issueDetails;


    /**
     * 状态
     */
    @TableField(value = "state")
    @Length(max = 255, message = "状态长度不能超过1")
    private String state;



}
