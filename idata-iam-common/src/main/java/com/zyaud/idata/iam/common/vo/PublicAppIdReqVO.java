package com.zyaud.idata.iam.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@ApiModel(value = "公共appIdVO")
public class PublicAppIdReqVO {

    @ApiModelProperty(value = "appId")
    @NotBlank(message = "appId不能为空")
    private String appId;
}
