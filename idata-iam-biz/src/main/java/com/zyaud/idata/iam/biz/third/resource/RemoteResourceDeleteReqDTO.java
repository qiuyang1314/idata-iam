package com.zyaud.idata.iam.biz.third.resource;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : dong
 * @ClassName: RemoteResourceDeleteReqDTO
 * @date : 2021-12-27 19:22
 * @Description :远程资源菜单删除DTO
 * @Version :
 **/
@Data
public class RemoteResourceDeleteReqDTO {


    @ApiModelProperty("资源id")
    private String  id;



}
