package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FindAppsRspVO {
    @ApiModelProperty(value = "应用id")
    private String id;

    @ApiModelProperty(value = "应用名字")
    private String appName;

    @ApiModelProperty(value = "应用编码")
    private String appCode;

    @ApiModelProperty(value = "打开方式")
    private String openType;

    @ApiModelProperty(value = "是否生效")
    private String useable;

    @ApiModelProperty(value = "系统应用地址")
    private String url;

    @ApiModelProperty(value = "应用图片")
    private String img;

    @ApiModelProperty(value = "排序")
    private Integer orderIndex;

    @ApiModelProperty(value = "系统应用公钥(账号)")
    private String appKey;

    @ApiModelProperty(value = "系统应用私钥(密码)")
    private String appSecret;
}
