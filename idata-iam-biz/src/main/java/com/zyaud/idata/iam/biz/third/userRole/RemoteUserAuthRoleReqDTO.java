package com.zyaud.idata.iam.biz.third.userRole;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author : dong
 * @ClassName: RemoteUserAuthRoleReqDTO
 * @date : 2021-12-28 10:54
 * @Description :远程用户授权角色DTO   新增修改 先删除后新增
 * @Version :
 **/

@Data
public class RemoteUserAuthRoleReqDTO {

    @ApiModelProperty("用户id")
    private String userId;


    @ApiModelProperty("角色编码")
    private List<String> roleCodes ;





}
