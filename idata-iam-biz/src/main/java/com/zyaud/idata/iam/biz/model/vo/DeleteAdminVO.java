package com.zyaud.idata.iam.biz.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class DeleteAdminVO {
    /**
     * 系统id
     */
    @NotBlank(message = "系统编码不存在")
    private String appId;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不存在")
    private String roleId;

    /**
     * 用户编码
     */
    @NotBlank(message = "用户编码不存在")
    private String userCodeId;
}
