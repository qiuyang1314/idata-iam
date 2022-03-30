package com.zyaud.idata.iam.biz.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 姓名
     */
    @TableField(value = "name", condition = LIKE)
    @Length(max = 255, message = "姓名长度不能超过255")
    @NotBlank(message = "请输入姓名")
    private String name;

    /**
     * 性别
     */
    @TableField(value = "sex", condition = LIKE)
    @Length(max = 1, message = "性别长度不能超过1")
    @NotBlank(message = "请选择性别")
    private String sex;

    /**
     * 头像
     */
    @TableField(value = "avatar", condition = LIKE)
    @Length(max = 255, message = "头像长度不能超过255")
    private String avatar;

    /**
     * 民族
     */
    @TableField(value = "nation", condition = LIKE)
    @Length(max = 32, message = "民族长度不能超过32")
    private String nation;

    /**
     * 出生年月
     */
    @TableField(value = "birthday", condition = LIKE)
    @Length(max = 32, message = "出生年月长度不能超过32")
    private String birthday;

    /**
     * 身份证号
     */
    @TableField(value = "id_card", condition = LIKE)
    @Length(max = 18, message = "身份证号长度不能超过18")
    private String idCard;

    /**
     * 手机号码
     */
    @TableField(value = "phone", condition = LIKE)
    @Length(max = 32, message = "手机号码长度不能超过32")
    private String phone;

    /**
     * 其他联系方式
     */
    @TableField(value = "other_contact", condition = LIKE)
    @Length(max = 32, message = "其他联系方式长度不能超过32")
    private String otherContact;

    /**
     * 密级标识 (0核心涉密人员1重要涉密人员2一般涉密人员3非涉密人员)
     */
    @TableField(value = "secirity_flag", condition = LIKE)
    @Length(max = 1, message = "密级标识 (0核心涉密人员1重要涉密人员2一般涉密人员3非涉密人员)长度不能超过1")
    @NotBlank(message = "请选择密级标识")
    private String secirityFlag;

    /**
     * 机构
     */
    @TableField(value = "office_id", condition = LIKE)
    @Length(max = 32, message = "机构长度不能超过32")
    @NotBlank(message = "请选择机构")
    private String officeId;

    /**
     * 职务
     */
    @TableField(value = "user_post", condition = LIKE)
    @Length(max = 32, message = "职务长度不能超过32")
    @NotBlank(message = "请输入职务")
    private String userPost;

    /**
     * 级别
     */
    @TableField(value = "user_level", condition = LIKE)
    @Length(max = 32, message = "级别长度不能超过32")
    @NotBlank(message = "请输入级别")
    private String userLevel;

    /**
     * 编制
     */
    @TableField(value = "budgeted", condition = LIKE)
    @Length(max = 32, message = "编制长度不能超过32")
    private String budgeted;

    /**
     * 编制职务
     */
    @TableField(value = "budgeted_post", condition = LIKE)
    @Length(max = 32, message = "编制职务长度不能超过32")
    private String budgetedPost;


}
