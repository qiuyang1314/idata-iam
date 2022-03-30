package com.zyaud.idata.iam.api.req;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class TokenAnalysisReqVO implements Serializable {
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
     * token
     */
    @NotBlank(message = "token不能为空")
    private String token;
}
