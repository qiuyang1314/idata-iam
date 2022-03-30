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
 * 用户教育工作经历
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserExperiencePageDTO extends PageParam {

    private static final long serialVersionUID=1L;
    /**
     * 用户id
     */
    @BindQuery(field = "user_id", comparison = Comparison.LIKE)
    @Length(max = 64, message = "用户id长度不能超过64")
    private String userId;

    /**
     * 学历
     */
    @BindQuery(field = "education", comparison = Comparison.LIKE)
    @Length(max = 32, message = "学历长度不能超过32")
    private String education;

    /**
     * 专业
     */
    @BindQuery(field = "major", comparison = Comparison.LIKE)
    @Length(max = 32, message = "专业长度不能超过32")
    private String major;

    /**
     * 参加工作时间
     */
    @BindQuery(field = "job_date", comparison = Comparison.LIKE)
    @Length(max = 32, message = "参加工作时间长度不能超过32")
    private String jobDate;

    /**
     * 工作经验
     */
    @BindQuery(field = "job_experience")
    private String jobExperience;

    /**
     * 任职经历
     */
    @BindQuery(field = "post_experience")
    private String postExperience;

    /**
     * 备注
     */
    @BindQuery(field = "remark", comparison = Comparison.LIKE)
    @Length(max = 255, message = "备注长度不能超过255")
    private String remark;




}
