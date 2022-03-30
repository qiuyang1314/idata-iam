package com.zyaud.idata.iam.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "公共appCodeVO")
public class PublicAppCodeReqVO {

    @ApiModelProperty(value = "appCode")
    private String appCode;
}
