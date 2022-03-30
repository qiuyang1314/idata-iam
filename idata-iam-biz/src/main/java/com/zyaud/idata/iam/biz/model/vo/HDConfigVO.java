package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class HDConfigVO {

    @ApiModelProperty(value = "密码过期时间", required = true)
    private Integer pastDueTime = 0;

    /**
     * 是否单总端登录(0否，1是)
     */
    @ApiModelProperty(value = "是否单总端登录(0否，1是)", required = true)
    private String isOneWayLogin = "0";

    /**
     * 是否向syslog服务发送登录失败日志(0否，1是)
     */
    @ApiModelProperty(value = "是否向syslog服务发送登录失败日志(0否，1是)", required = true)
    private String isSendSyslog = "0";
}
