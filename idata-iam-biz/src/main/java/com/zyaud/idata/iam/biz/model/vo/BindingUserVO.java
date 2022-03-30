package com.zyaud.idata.iam.biz.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class BindingUserVO {
    @NotBlank(message = "请选择用户")
    private String userId;
    @NotBlank(message = "请选择账号")
    private String userCodeId;
}
