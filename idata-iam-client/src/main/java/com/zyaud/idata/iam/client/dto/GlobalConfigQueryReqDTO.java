package com.zyaud.idata.iam.client.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value="CfgGlobalConfig对象", description="全局配置表")
public class GlobalConfigQueryReqDTO {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "配置key")
    private String configKey;



   



}
