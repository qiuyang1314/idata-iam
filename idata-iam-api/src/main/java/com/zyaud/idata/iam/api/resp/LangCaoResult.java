package com.zyaud.idata.iam.api.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class LangCaoResult<T> implements Serializable {

    @ApiModelProperty(value = "响应编码:0/200-请求处理成功")
    private int code;

    @ApiModelProperty(value = "提示消息")
    private String msg;

    @ApiModelProperty(value = "响应状态")
    private boolean success;

    @ApiModelProperty(value = "响应日志")
    private String logMsg;

    @ApiModelProperty(value = "同步信息响应数据")
    private T data;

    @ApiModelProperty(value = "登录响应数据")
    private T content;

    @ApiModelProperty(value = "登录响应提示")
    private String message;
}
