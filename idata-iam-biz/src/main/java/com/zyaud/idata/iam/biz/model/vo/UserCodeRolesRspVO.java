package com.zyaud.idata.iam.biz.model.vo;

import com.zyaud.idata.iam.biz.model.entity.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "UserCodeRolesRspVO", description = "用户账号拥有角色列表应答VO")
public class UserCodeRolesRspVO implements Serializable {
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户角色列表")
    private List<Role> roleList;

    @ApiModelProperty(value = "用户默认角色")
    private String defaultRoleId;
}
