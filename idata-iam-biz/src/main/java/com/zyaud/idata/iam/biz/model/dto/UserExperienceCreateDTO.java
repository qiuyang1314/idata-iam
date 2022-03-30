package com.zyaud.idata.iam.biz.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

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
public class UserExperienceCreateDTO implements Serializable {

    private static final long serialVersionUID=1L;
    /**
     * 用户id
     */
    @TableField(value = "user_id", condition = LIKE)
    @Length(max = 64, message = "用户id长度不能超过64")
    private String userId;

    /**
     * 学历
     */
    @TableField(value = "education", condition = LIKE)
    @Length(max = 32, message = "学历长度不能超过32")
    private String education;

    /**
     * 专业
     */
    @TableField(value = "major", condition = LIKE)
    @Length(max = 32, message = "专业长度不能超过32")
    private String major;

    /**
     * 参加工作时间
     */
    @TableField(value = "job_date", condition = LIKE)
    @Length(max = 32, message = "参加工作时间长度不能超过32")
    private String jobDate;

    /**
     * 工作经验
     */
    @TableField("job_experience")
    private String jobExperience;

    /**
     * 任职经历
     */
    @TableField("post_experience")
    private String postExperience;

    /**
     * 备注
     */
    @TableField(value = "remark", condition = LIKE)
    @Length(max = 255, message = "备注长度不能超过255")
    private String remark;




}
