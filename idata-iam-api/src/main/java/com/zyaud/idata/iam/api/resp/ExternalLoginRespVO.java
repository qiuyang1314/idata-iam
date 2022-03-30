package com.zyaud.idata.iam.api.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ExternalLoginRespVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户类型")
    private String authType;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

}
