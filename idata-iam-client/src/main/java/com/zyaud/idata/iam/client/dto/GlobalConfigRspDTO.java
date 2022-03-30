package com.zyaud.idata.iam.client.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value="CfgGlobalConfig对象", description="全局配置表")
public class GlobalConfigRspDTO {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "业务id")
    private String businessId;

    @ApiModelProperty(value = "配置key")
    private String configKey;


    @ApiModelProperty(value = "配置值")
    private String configValue;

    @ApiModelProperty(value = "配置类型")
    private String configType;


    @ApiModelProperty(value = "配置名称")
    private String configName;


    @ApiModelProperty(value = "是否启用  0关闭 1启用")
    private Integer enabled;

    @ApiModelProperty(value = "配置说明")
    private String remark;

    //todo 暂定配置常量名
    public static final String CONFIG  = "GlobalConfig";



}
