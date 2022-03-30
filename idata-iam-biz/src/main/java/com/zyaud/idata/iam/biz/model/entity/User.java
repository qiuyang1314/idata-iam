package com.zyaud.idata.iam.biz.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zyaud.fzhx.core.model.TimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(User.USER)
public class User extends TimeEntity<String> {

    private static final long serialVersionUID=1L;
    public static final String USER = "iam_user";
    public static final String NAME = "name";
    public static final String SEX = "sex";
    public static final String AVATAR = "avatar";
    public static final String NATION = "nation";
    public static final String BIRTHDAY = "birthday";
    public static final String IDCARD = "id_card";
    public static final String PHONE = "phone";
    public static final String OTHERCONTACT = "other_contact";
    public static final String SECIRITYFLAG = "secirity_flag";
    public static final String OFFICEID = "office_id";
    public static final String USERPOST = "user_post";
    public static final String USERLEVEL = "user_level";
    public static final String BUDGETED = "budgeted";
    public static final String BUDGETEDPOST = "budgeted_post";


    /**
     * 姓名
     */
    @TableField(NAME)
    @Length(max = 255, message = "姓名长度不能超过255")
    @ApiModelProperty(value = "姓名")
    private String name;


    /**
     * 性别
     */
    @TableField(SEX)
    @Length(max = 1, message = "性别长度不能超过1")
    @ApiModelProperty(value = "性别")
    private String sex;


    /**
     * 头像
     */
    @TableField(AVATAR)
    @Length(max = 255, message = "头像长度不能超过255")
    @ApiModelProperty(value = "头像")
    private String avatar;


    /**
     * 民族
     */
    @TableField(NATION)
    @Length(max = 32, message = "民族长度不能超过32")
    @ApiModelProperty(value = "名族")
    private String nation;


    /**
     * 出生年月
     */
    @TableField(BIRTHDAY)
    @Length(max = 32, message = "出生年月长度不能超过32")
    @ApiModelProperty(value = "出生年月")
    private String birthday;


    /**
     * 身份证号
     */
    @TableField(IDCARD)
    @Length(max = 18, message = "身份证号长度不能超过18")
    @ApiModelProperty(value = "身份证号")
    private String idCard;


    /**
     * 手机号码
     */
    @TableField(PHONE)
    @Length(max = 32, message = "手机号码长度不能超过32")
    @ApiModelProperty(value = "手机号码")
    private String phone;


    /**
     * 其他联系方式
     */
    @TableField(OTHERCONTACT)
    @Length(max = 32, message = "其他联系方式长度不能超过32")
    @ApiModelProperty(value = "其他联系方式")
    private String otherContact;


    /**
     * 密级标识 (0核心涉密人员1重要涉密人员2一般涉密人员3非涉密人员)
     */
    @TableField(SECIRITYFLAG)
    @Length(max = 1, message = "密级标识 (0核心涉密人员1重要涉密人员2一般涉密人员3非涉密人员)长度不能超过1")
    @ApiModelProperty(value = "密级标识(0核心涉密人员1重要涉密人员2一般涉密人员3非涉密人员)")
    private String secirityFlag;


    /**
     * 部门
     */
    @TableField(OFFICEID)
    @Length(max = 32, message = "部门长度不能超过32")
    @ApiModelProperty(value = "部门")
    private String officeId;


    /**
     * 职务
     */
    @TableField(USERPOST)
    @Length(max = 32, message = "职务长度不能超过32")
    @ApiModelProperty(value = "职务")
    private String userPost;


    /**
     * 级别
     */
    @TableField(USERLEVEL)
    @Length(max = 32, message = "级别长度不能超过32")
    @ApiModelProperty(value = "级别")
    private String userLevel;


    /**
     * 编制
     */
    @TableField(BUDGETED)
    @Length(max = 32, message = "编制长度不能超过32")
    @ApiModelProperty(value = "编制")
    private String budgeted;


    /**
     * 编制职务
     */
    @TableField(BUDGETEDPOST)
    @Length(max = 32, message = "编制职务长度不能超过32")
    @ApiModelProperty(value = "编制职务")
    private String budgetedPost;



}
