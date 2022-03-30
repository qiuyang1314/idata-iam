package com.zyaud.idata.iam.client.dto;

import com.zyaud.fzhx.core.model.TimeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 内部系统角色返回类
 */

@Data
@Accessors(chain = true)
@ApiModel(value = "内部系统角色返回类")
public class RoleInDTO extends TimeEntity<String> {

    private static final long serialVersionUID = 1L;

    /**
     * 系统应用id
     */
    @Length(max = 64, message = "系统应用id长度不能超过64")
    @ApiModelProperty(value = "系统应用id")
    private String appId;


    /**
     * 角色名称
     */
    @Length(max = 128, message = "角色名称长度不能超过128")
    @ApiModelProperty(value = "角色名称")
    private String roleName;


    /**
     * 角色编码
     */
    @Length(max = 128, message = "角色编码长度不能超过128")
    @ApiModelProperty(value = "角色编码")
    private String roleCode;


    /**
     * 角色类型(1内置2自定义)
     */
    @Length(max = 128, message = "角色类型(1内置2自定义)长度不能超过128")
    @ApiModelProperty(value = "角色类型(1内置2自定义)")
    private String roleType;


    /**
     * 是否可用
     */
    @Length(max = 1, message = "是否可用长度不能超过1")
    @ApiModelProperty(value = "是否可用")
    private String useable;

}
