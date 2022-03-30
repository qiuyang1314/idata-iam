package com.zyaud.idata.iam.api.req.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: fiaud-das
 * @description: 直传token
 * @author: qiuyang
 * @create: 2021-05-21 10:50
 **/
@Data
public class OnlyTokenReqDto {
    @ApiModelProperty(value = "token")
    private String token;
}
