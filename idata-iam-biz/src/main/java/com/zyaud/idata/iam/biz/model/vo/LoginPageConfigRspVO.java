package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "LoginPageConfigRspVO对象", description = "登录页配置应答VO")
public class LoginPageConfigRspVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录主键")
    private String id;

    @ApiModelProperty(value = "系统名称")
    private String loginSysName;

    @ApiModelProperty(value = "是否开启验证码：0--未开启；1--开启")
    private String enableCaptcha = "1";

    @ApiModelProperty(value = "系统图标")
    private FileStoreInfoVO iconFile;

    @ApiModelProperty(value = "系统图标访问路径")
    private String iconFileUrl;

    @ApiModelProperty(value = "系统登录页图")
    private List<FileStoreInfoVO> loginPageFiles = new ArrayList<>();

    @ApiModelProperty(value = "系统登录页图url")
    private List<String> loginPageFileUrls = new ArrayList<>();
}
