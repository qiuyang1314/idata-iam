package com.zyaud.idata.iam.biz.third.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : dong
 * @ClassName: RemoteRoleCreateReqDto
 * @date : 2021-12-27 20:49
 * @Description :远程新增角色DTO
 * @Version :
 **/
@Data
public class RemoteRoleCreateReqDto {


    @ApiModelProperty("角色id")
    private  String roleId;

    @ApiModelProperty("角色编码")
    private  String roleCode;

    @ApiModelProperty("角色名称")
    private  String roleName;

}
