package com.zyaud.idata.iam.api.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;


/**
* @Description: 远程登录返回VO
* @Author: dong
* @return:
* @Date: 2022/1/8 16:10
*/
@Data
@ApiModel("远程登录返回VO")
@Accessors(chain = true)
public class RemoteLoginRspVO implements Serializable {
    @ApiModelProperty(value = "状态")
    private boolean success;

    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "机构名")
    private String domainName;

    @ApiModelProperty(value = "accessToken")
    private String accessToken;

    @ApiModelProperty(value = "refreshToken")
    private String refreshToken;

    @ApiModelProperty(value = "登录名")
    private String expirationTime;

    @ApiModelProperty(value = "账号id")
    private String userId;

    @ApiModelProperty(value = "tno")
    private String tno;

    @ApiModelProperty(value = "returnURL")
    private String returnURL;

    @ApiModelProperty(value = "扩展参数")
    private Map<String, Object> ext;

    @ApiModelProperty(value = "是否是初始密码")
    private boolean isPassword;

    @ApiModelProperty(value = "是否是")
    private boolean isSuper;
}
