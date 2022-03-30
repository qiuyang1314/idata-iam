package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LastLoginVO {

    @ApiModelProperty(value = "登录ip")
    private String ipAdress;

    @ApiModelProperty(value = "登录时间")
    private String loginTime;
}
