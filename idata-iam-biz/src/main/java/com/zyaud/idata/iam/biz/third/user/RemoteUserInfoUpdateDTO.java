package com.zyaud.idata.iam.biz.third.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : dong
 * @ClassName: RemoteUserInfoUpdateDTO
 * @date : 2021-12-27 16:18
 * @Description : 远程修改用户DTO
 * @Version :
 **/

@Data
public class RemoteUserInfoUpdateDTO {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("登录名")
    private String loginName;

    @ApiModelProperty("用户状态")
    private String status;

    @ApiModelProperty("用户密码")
    private String password;

}
