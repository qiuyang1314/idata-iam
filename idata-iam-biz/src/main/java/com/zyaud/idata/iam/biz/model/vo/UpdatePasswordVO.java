package com.zyaud.idata.iam.biz.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class UpdatePasswordVO {
    @NotBlank(message = "id不能为空")
    private String id;
    @NotBlank(message = "密码不能为空")
    private String password;
}
