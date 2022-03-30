package com.zyaud.idata.iam.biz.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author CaiFan
 * @Date 2020/11/2 16:47
 */
@Data
@Accessors(chain = true)
public class AppCodeUserIdVO {
    @NotBlank(message = "应用编码不能为空")
    private String appCode;

    @NotBlank(message = "用户账号id不能为空")
    private String userCodeId;
}
