package com.zyaud.idata.iam.client.dto;

import com.zyaud.fzhx.binder.annotation.BindField;
import com.zyaud.fzhx.core.model.TimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserInDTO extends TimeEntity<String> {

    @BindField(bindTo = "dictLabel", dict = "thickGrade", method = "getDictData", api = "stdCodeServiceImpl")
    private String secirityFlag;
    private String dictLabel;
    @BindField(bindTo = "officeName", method = "getOfficeNameById", api = "officeServiceImpl")
    private String officeId;
    private String officeName;

    private String loginName;
    private String userCodeId;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;


    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String sex;


    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;


    /**
     * 民族
     */
    @ApiModelProperty(value = "名族")
    private String nation;


    /**
     * 出生年月
     */
    @ApiModelProperty(value = "出生年月")
    private String birthday;


    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String idCard;


    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String phone;


    /**
     * 其他联系方式
     */
    @ApiModelProperty(value = "其他联系方式")
    private String otherContact;


    /**
     * 职务
     */
    @ApiModelProperty(value = "职务")
    private String userPost;


    /**
     * 级别
     */
    @ApiModelProperty(value = "级别")
    private String userLevel;


    /**
     * 编制
     */
    @ApiModelProperty(value = "编制")
    private String budgeted;


    /**
     * 编制职务
     */
    @ApiModelProperty(value = "编制职务")
    private String budgetedPost;


}
