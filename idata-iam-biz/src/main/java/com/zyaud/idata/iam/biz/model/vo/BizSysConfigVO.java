package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "BizSysConfigVO", description = "业务系统配置VO")
public class BizSysConfigVO implements Serializable {
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "业务系统编号")
    private String appCode;

    @ApiModelProperty(value = "业务系统名称")
    private String appName;

    @ApiModelProperty(value = "业务系统图标")
    private String appIcon;

    @ApiModelProperty(value = "是否配置Access-Control-Allow-Credentials，0--否；1--是")
    private String openAccessControlAllowCredentials;

    @ApiModelProperty(value = "是否配置strict-transport-security，0--否；1--是")
    private String openStrictTransportSecurity;

    @ApiModelProperty(value = "是否配置Content-Security-Policy，0--否；1--是")
    private String openContentSecurityPolicy;

    @ApiModelProperty(value = "是否配置X-Content-Type-Options，0--否；1--是")
    private String openXContentTypeOptions;

    @ApiModelProperty(value = "是否配置X-XSS-Protection，0--否；1--是")
    private String openXXSSProtection;

    @ApiModelProperty(value = "是否配置X-Frame-Options，0--否；1--是")
    private String openXFrameOptions;
}
