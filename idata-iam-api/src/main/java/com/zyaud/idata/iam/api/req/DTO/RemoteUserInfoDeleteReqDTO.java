package com.zyaud.idata.iam.api.req.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author : qiuyang
 * @ClassName: RemoteUserInfoDeleteReqDTO
 * @date : 2022/1/5 15:21
 * @Description : 远程删除用户DTO
 * @Version :
 **/

@Data
public class RemoteUserInfoDeleteReqDTO {

    @ApiModelProperty("用户id")
    private List<String> userId;


}
