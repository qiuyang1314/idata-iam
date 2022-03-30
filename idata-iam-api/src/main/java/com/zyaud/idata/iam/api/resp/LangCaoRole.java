package com.zyaud.idata.iam.api.resp;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : dong
 * @ClassName: LangCaoRole
 * @date : 2022-01-18 14:32
 * @Description :
 * @Version :
 **/

@Data
public class LangCaoRole {

    @ApiModelProperty(value = "角色id")
    private String roleId;
    @ApiModelProperty(value = "角色编码")
    private String roleCode;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "角色描述")
    private String description;

}
