package com.zyaud.idata.iam.biz.model.vo;

import com.zyaud.fzhx.binder.annotation.BindField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "UserBaseInfoRspVO对象", description = "登录用户基础信息获取VO")
public class UserBaseInfoRspVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 账号类型
     */
    @BindField(bindTo = "typeName", dict = "accountType", method = "getDictData", api = "stdCodeServiceImpl")
    private String type;
    private String typeName;


    /**
     * 状态 (0正常1锁定2禁用)
     */
    private String status;


    /**
     * 用户信息
     */
    @BindField(bindTo = "userName", method = "getUserNameById", api = "userServiceImpl")
    private String userId;
    private String userName;

    /**
     * 机构
     */
    @BindField(bindTo = "officeName", method = "getOfficeNameById", api = "officeServiceImpl")
    private String officeId;
    private String officeName;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "名族")
    private String nation;

    @ApiModelProperty(value = "出生年月")
    private String birthday;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "其他联系方式")
    private String otherContact;

    @ApiModelProperty(value = "密级标识(0核心涉密人员1重要涉密人员2一般涉密人员3非涉密人员)")
    private String secirityFlag;

    @ApiModelProperty(value = "职务")
    private String userPost;

    @ApiModelProperty(value = "级别")
    private String userLevel;

    @ApiModelProperty(value = "编制")
    private String budgeted;

    @ApiModelProperty(value = "编制职务")
    private String budgetedPost;
}
