package com.zyaud.idata.iam.api.req;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class LoginReqVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 签名
     */
    @NotBlank(message = "签名不能为空")
    private String sign;
    /**
     * 应用id
     */
    @NotBlank(message = "应用id不能为空")
    private String appId;
    /**
     * 登录名
     */
    @NotBlank(message = "登录名不能为空")
    private String loginName;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String passwd;
}
