package com.zyaud.idata.iam.biz.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysLogCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用编码")
    private String appCode;

    @ApiModelProperty(value = "操作IP")
    private String requestIp;

    @ApiModelProperty(value = "租户编码")
    private String tenantCode;

    @ApiModelProperty(value = "日志类型{OPT:操作类型;EX:异常类型}")
    private String type;

    @ApiModelProperty(value = "操作类型")
    private String optype;

    @ApiModelProperty(value = "操作人")
    private String userName;

    @ApiModelProperty(value = "模块")
    private String module;

    @ApiModelProperty(value = "操作描述")
    private String description;

    @ApiModelProperty(value = "类路径")
    private String classPath;

    @ApiModelProperty(value = "请求类型")
    private String actionMethod;

    @ApiModelProperty(value = "请求地址")
    private String requestUri;

    @ApiModelProperty(value = "请求类型{GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;PATCH:PATCH请求;TRACE:TRACE请求;HEAD:HEAD请求;OPTIONS:OPTIONS请求;}")
    private String httpMethod;

    @ApiModelProperty(value = "请求参数")
    private String params;

    @ApiModelProperty(value = "返回值")
    private String result;

    @ApiModelProperty(value = "异常描述")
    private String exDesc;

    @ApiModelProperty(value = "异常详情信息")
    private String exDetail;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "完成时间")
    private Date finishTime;

    @ApiModelProperty(value = "消耗时间")
    private Long consumingTime;

    @ApiModelProperty(value = "浏览器")
    private String ua;

    @ApiModelProperty(value = "用户id")
    private String createUser;
}
