package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;


@Data
@Accessors(chain = true)
@ApiModel(value = "LoginPageConfigReqVO对象", description = "登录页配置请求VO")
public class LoginPageConfigReqVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统名称", required = true)
    private String loginSysName;

    @ApiModelProperty(value = "是否开启验证码：0--未开启；1--开启", required = true)
    @NotBlank(message = "是否开启验证码")
    private String enableCaptcha;

    @ApiModelProperty(value = "系统图标")
    private FileStoreInfoVO iconFile;

    @ApiModelProperty(value = "系统登录页图")
    private List<FileStoreInfoVO> loginPageFiles;
}
