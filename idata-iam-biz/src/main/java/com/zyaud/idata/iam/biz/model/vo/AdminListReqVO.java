package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class AdminListReqVO {
    @ApiModelProperty(value = "roleName")
    @NotBlank(message = "角色名不能为空")
    private String roleName;
}
