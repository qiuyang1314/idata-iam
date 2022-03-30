package com.zyaud.idata.iam.biz.third.resource;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : dong
 * @ClassName: RemoteResourceUpdateReqDTO
 * @date : 2021-12-27 19:22
 * @Description :远程资源菜单修改DTO
 * @Version :
 **/
@Data
public class RemoteResourceUpdateReqDTO {

    @ApiModelProperty("资源id")
    private String  id;

    @ApiModelProperty("权限标识")
    private String  resourceCode;

    @ApiModelProperty("应用id")
    private String appId;

    @ApiModelProperty("资源名称")
    private String name;

    @ApiModelProperty("是否为父节点 0是   1否")
    private String  isLeaf;

    @ApiModelProperty("是否显示(是否显示)  1 是  0 否")
    private String  isAuthorize;


    @ApiModelProperty("路由地址")
    private String  resourceUrl;


}
