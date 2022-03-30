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
 * 用户信息
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserPageDTO extends PageParam {

    private static final long serialVersionUID=1L;
    /**
     * 姓名
     */
    @BindQuery(field = "name", comparison = Comparison.LIKE)
    @Length(max = 255, message = "姓名长度不能超过255")
    private String name;

    /**
     * 性别
     */
    @BindQuery(field = "sex", comparison = Comparison.LIKE)
    @Length(max = 1, message = "性别长度不能超过1")
    private String sex;

    /**
     * 头像
     */
    @BindQuery(field = "avatar", comparison = Comparison.LIKE)
    @Length(max = 255, message = "头像长度不能超过255")
    private String avatar;

    /**
     * 民族
     */
    @BindQuery(field = "nation", comparison = Comparison.LIKE)
    @Length(max = 32, message = "民族长度不能超过32")
    private String nation;

    /**
     * 出生年月
     */
    @BindQuery(field = "birthday", comparison = Comparison.LIKE)
    @Length(max = 32, message = "出生年月长度不能超过32")
    private String birthday;

    /**
     * 身份证号
     */
    @BindQuery(field = "id_card", comparison = Comparison.LIKE)
    @Length(max = 18, message = "身份证号长度不能超过18")
    private String idCard;

    /**
     * 手机号码
     */
    @BindQuery(field = "phone", comparison = Comparison.LIKE)
    @Length(max = 32, message = "手机号码长度不能超过32")
    private String phone;

    /**
     * 其他联系方式
     */
    @BindQuery(field = "other_contact", comparison = Comparison.LIKE)
    @Length(max = 32, message = "其他联系方式长度不能超过32")
    private String otherContact;

    /**
     * 密级标识 (0核心涉密人员1重要涉密人员2一般涉密人员3非涉密人员)
     */
    @BindQuery(field = "secirity_flag", comparison = Comparison.LIKE)
    @Length(max = 1, message = "密级标识 (0核心涉密人员1重要涉密人员2一般涉密人员3非涉密人员)长度不能超过1")
    private String secirityFlag;

    /**
     * 部门
     */
    @BindQuery(field = "office_id", comparison = Comparison.LIKE)
    @Length(max = 32, message = "部门长度不能超过32")
    private String officeId;

    /**
     * 职务
     */
    @BindQuery(field = "user_post", comparison = Comparison.LIKE)
    @Length(max = 32, message = "职务长度不能超过32")
    private String userPost;

    /**
     * 级别
     */
    @BindQuery(field = "user_level", comparison = Comparison.LIKE)
    @Length(max = 32, message = "级别长度不能超过32")
    private String userLevel;

    /**
     * 编制
     */
    @BindQuery(field = "budgeted", comparison = Comparison.LIKE)
    @Length(max = 32, message = "编制长度不能超过32")
    private String budgeted;

    /**
     * 编制职务
     */
    @BindQuery(field = "budgeted_post", comparison = Comparison.LIKE)
    @Length(max = 32, message = "编制职务长度不能超过32")
    private String budgetedPost;




}
