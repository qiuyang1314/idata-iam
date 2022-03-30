package com.zyaud.idata.iam.biz.third.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : dong
 * @ClassName: RemoteRoleCreateReqDto
 * @date : 2021-12-27 20:49
 * @Description :远程删除角色DTO
 * @Version :
 **/

@Data
public class RemoteRoleDeleteReqDto {

    @ApiModelProperty("角色id")
    private  String roleId;
}
