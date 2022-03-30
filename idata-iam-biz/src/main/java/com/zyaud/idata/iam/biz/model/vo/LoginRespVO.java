package com.zyaud.idata.iam.biz.model.vo;

import com.zyaud.fzhx.iam.token.Token;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginRespVO {
    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "机构名")
    private String domainName;

//    @ApiModelProperty(value = "accessToken")
//    private String accessToken;
//
//    @ApiModelProperty(value = "refresh_token")
//    private String refreshToken;

    @ApiModelProperty(value = "登录名")
    private String expirationTime;

    @ApiModelProperty(value = "账号id")
    private String userId;

    @ApiModelProperty(value = "tno")
    private String tno;

    @ApiModelProperty(value = "returnURL")
    private String returnURL;

    @ApiModelProperty(value = "accessToken")
    private Token accessToken;

    @ApiModelProperty(value = "refreshToken")
    private Token refreshToken;
}
