package com.zyaud.idata.iam.api.req.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author : dong
 * @ClassName: RemoteUserRqeDTO
 * @date : 2022-01-07 15:36
 * @Description :浪潮同步用户DTO
 * @Version :
 **/

@Data
public class RemoteUserRqeDTO {

    @ApiModelProperty(value = "人员id")
    @NotBlank(message = "用户id不能为空")
    private String userId;
    @ApiModelProperty(value = "用户姓名")
    private String userName;
    @ApiModelProperty(value = "状态是否有效，0有效，1 无效")
    private String userStatus;
    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "用户所在的机构ID")
    private String corporationId;
    @ApiModelProperty(value = "用户所在的机构名称")
    private String corporationName;
    @ApiModelProperty(value = "职务")
    private String post;
    @ApiModelProperty(value = "职级")
    private String rank;
    @ApiModelProperty(value = "身份证号码")
    private String idCard;
    @ApiModelProperty(value = "性别 0 男 1 女")
    private String sex;
    @ApiModelProperty(value = "账号有效期")
    private String expiredTime;
    @ApiModelProperty(value = "工作证号码")
    private String identityCard;
    @ApiModelProperty(value = "手机号")
    private String phoneNumber;
    @ApiModelProperty(value = "座机号")
    private String telephone;
    @ApiModelProperty(value = "邮箱地址")
    private String mailbox;
    @ApiModelProperty(value = "用户排序")
    private String sort;

}
