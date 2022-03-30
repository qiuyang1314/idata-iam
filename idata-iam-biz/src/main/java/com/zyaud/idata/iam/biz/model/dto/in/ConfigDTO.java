package com.zyaud.idata.iam.biz.model.dto.in;

import com.zyaud.fzhx.core.model.TimeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ConfigDTO对象")
public class ConfigDTO extends TimeEntity<String> {
    /**
     * 配置类型（0：系统配置）
     */
    @ApiModelProperty(value = "配置类型（0：系统配置）")
    private String ctype;

    /**
     * 配置项编码（0：密码过期时间，1：ip配置）
     */
    @ApiModelProperty(value = "配置项编码（0：密码过期时间，1：ip配置，2：回收站过期时间）")
    private String ccode;

    /**
     * 配置项（配置中文名称）
     */
    @ApiModelProperty(value = "配置项（配置中文名称）")
    private String citem;

    /**
     * 配置项值
     */
    @ApiModelProperty(value = "配置项值")
    private String cvalue;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer orderIndex;
}
