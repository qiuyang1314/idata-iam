package com.zyaud.idata.iam.api.req.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : dong
 * @ClassName: RemoteOrgDTO
 * @date : 2022-01-07 14:40
 * @Description : 浪潮远程同步机构DTO
 * @Version :
 **/


@Data
public class RemoteOrgDTO {



    @ApiModelProperty("操作类型")
    private String type;


    @ApiModelProperty("机构同步数据DTO")
    private  RemoteOrgRqeDTO data ;



}
