package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "UserRoleReqVO对象", description = "用户角色权限获取VO")
public class UserRoleReqVO implements Serializable {
    @ApiModelProperty(value = "应用编码", required = true)
    @NotBlank(message = "应用编码不能为空")
    private String appCode;

    @ApiModelProperty(value = "用户账号ID", required = true)
    @NotBlank(message = "用户账号ID不能为空")
    private String userCodeId;
}
