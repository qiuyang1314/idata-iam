package com.zyaud.idata.iam.api.req.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : dong
 * @ClassName: RemoteLoginReqDTO
 * @date : 2022-01-08 16:07
 * @Description :登录请求DTO
 * @Version :
 **/
@Data
@ApiModel("登录请求DTO")
public class RemoteLoginReqDTO {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("登录名")
    private String LoginName;
}
