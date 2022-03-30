package com.zyaud.idata.iam.biz.model.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ConfigReqVO对象", description = "配置查询入参对象")
public class ConfigReqVO {

    @ApiModelProperty(value = "配置类型（0：系统配置）")
    private String cType;

    @ApiModelProperty(value = "配置项编码（0：密码过期时间，1：ip配置,2回收站）")
    private String cCode;
}
