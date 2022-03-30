package com.zyaud.idata.iam.biz.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zyaud.fzhx.binder.annotation.BindField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class SysLogPageVO {
    @ApiModelProperty(value = "id")
    private String id;

    @BindField(bindTo = "appName", method = "getAppNameByAppCodes", api = "appServiceImpl")
    private String appCode;

    @ApiModelProperty(value = "应用名")
    private String appName;

    @ApiModelProperty(value = "模块")
    private String module;

    @ApiModelProperty(value = "日志类型(1普通2系统3安全4审计)")
    private String logType;

    @ApiModelProperty(value = "操作ip地址")
    private String remoteIp;

    @ApiModelProperty(value = "操作类型")
    private String operateType;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "操作明细")
    private String infos;

    @ApiModelProperty(value = "浏览器")
    private String browser;

    @ApiModelProperty(value = "操作系统")
    private String os;

    @ApiModelProperty(value = "异常信息")
    private String errMsg;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime createTime;
}
