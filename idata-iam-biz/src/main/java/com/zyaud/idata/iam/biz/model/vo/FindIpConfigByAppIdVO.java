package com.zyaud.idata.iam.biz.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zyaud.fzhx.binder.annotation.BindField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Accessors(chain = true)
public class FindIpConfigByAppIdVO {

    @ApiModelProperty(value = "应用id")
    @BindField(bindTo = "appName", method = "getAppNameById", api = "appServiceImpl")
    private String appId;

    @ApiModelProperty(value = "应用名")
    private String appName;

    @ApiModelProperty(value = "ip个数")
    private Integer ipSum;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "可访问ip")
    private Set<String> ips;

    @ApiModelProperty(value = "可访问ip")
    private Set<String> ipconfigs;

}
