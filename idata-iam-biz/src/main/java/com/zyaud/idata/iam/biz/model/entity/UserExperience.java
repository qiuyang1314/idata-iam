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
 * 用户教育工作经历
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(UserExperience.USEREXPERIENCE)
public class UserExperience extends TimeEntity<String> {

    private static final long serialVersionUID=1L;
    public static final String USEREXPERIENCE = "iam_user_experience";
    public static final String USERID = "user_id";
    public static final String EDUCATION = "education";
    public static final String MAJOR = "major";
    public static final String JOBDATE = "job_date";
    public static final String JOBEXPERIENCE = "job_experience";
    public static final String POSTEXPERIENCE = "post_experience";
    public static final String REMARK = "remark";


    /**
     * 用户id
     */
    @TableField(USERID)
    @Length(max = 64, message = "用户id长度不能超过64")
    private String userId;


    /**
     * 学历
     */
    @TableField(EDUCATION)
    @Length(max = 32, message = "学历长度不能超过32")
    private String education;


    /**
     * 专业
     */
    @TableField(MAJOR)
    @Length(max = 32, message = "专业长度不能超过32")
    private String major;


    /**
     * 参加工作时间
     */
    @TableField(JOBDATE)
    @Length(max = 32, message = "参加工作时间长度不能超过32")
    private String jobDate;


    /**
     * 工作经验
     */
    @TableField(JOBEXPERIENCE)
        private String jobExperience;


    /**
     * 任职经历
     */
    @TableField(POSTEXPERIENCE)
        private String postExperience;


    /**
     * 备注
     */
    @TableField(REMARK)
    @Length(max = 255, message = "备注长度不能超过255")
    private String remark;



}
