package com.zyaud.idata.iam.biz.third.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : dong
 * @ClassName: RemoteUserInfoCreateDTO
 * @date : 2021-12-27 16:18
 * @Description : 远程删除用户DTO
 * @Version :
 **/

@Data
public class RemoteUserInfoDeleteDTO {

    @ApiModelProperty("用户id")
    private String userId;


}
