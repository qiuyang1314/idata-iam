package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginVO {
    @ApiModelProperty(value = "账号", required = true)
    private String loginName;

    @ApiModelProperty(value = "密码", required = true)
    private String passwd;

    //TODO 验证码走配置校验
    @ApiModelProperty(value = "验证码唯一标识", required = true)
    private String key;

    @ApiModelProperty(value = "验证码", required = true)
    private String captcha;

    @ApiModelProperty(value = "回跳地址")
    private String returnURL = "";

    @ApiModelProperty(value = "巡视单位code，现场工作子系统登录使用")
    private String xsunitCode;

    @ApiModelProperty(value = "登录的系统code")
    private String appCode;
}
