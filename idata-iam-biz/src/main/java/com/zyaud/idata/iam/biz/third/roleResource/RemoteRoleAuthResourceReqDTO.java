package com.zyaud.idata.iam.biz.third.roleResource;

import com.zyaud.idata.iam.biz.third.resource.RemoteResourceCreateReqDTO;
import com.zyaud.idata.iam.biz.third.resourceItem.RemoteResourceItemCreateReqDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author : dong
 * @ClassName: RemoteRoleAuthResourceReqDTO
 * @date : 2021-12-28 11:31
 * @Description :远程 角色授权资源菜单权限DTO  先删除后新增
 * @Version :
 **/

@Data
public class RemoteRoleAuthResourceReqDTO {


    @ApiModelProperty("角色编码")
    private String roleCode ;

    @ApiModelProperty("资源菜单")
    private List<RemoteResourceCreateReqDTO>   remoteResourceCreateReqDTOS;

    @ApiModelProperty("资源权限")
    private List<RemoteResourceItemCreateReqDto>   remoteResourceItemCreateReqDtos;


}
