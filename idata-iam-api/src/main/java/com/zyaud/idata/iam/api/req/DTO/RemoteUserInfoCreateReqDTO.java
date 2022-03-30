package com.zyaud.idata.iam.api.req.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : dong
 * @ClassName: RemoteUserInfoCreateDTO
 * @date : 2021-12-27 16:18
 * @Description : 远程新增用户DTO
 * @Version :
 **/

@Data
public class RemoteUserInfoCreateReqDTO {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("登录名")
    private String loginName;

    @ApiModelProperty("机构编码")
    private String orgCode;

    @ApiModelProperty("用户状态")
    private String status;

}
