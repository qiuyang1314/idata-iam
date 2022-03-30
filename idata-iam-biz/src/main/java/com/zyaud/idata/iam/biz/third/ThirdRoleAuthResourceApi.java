package com.zyaud.idata.iam.biz.third;

import com.zyaud.idata.iam.biz.third.appResultDto.AppResultDto;
import com.zyaud.idata.iam.biz.third.roleResource.RemoteRoleAuthResourceReqDTO;

/**
 * @program: idata-iam
 * @description: 第三方角色授权资源同步接口
 * @author: qiuyang
 * @create: 2022-01-04 15:19
 **/
public interface ThirdRoleAuthResourceApi {

    /**
    * @Description 角色授权资源
    * @Author qiuyang
    * @param remoteRoleAuthResourceReqDTO
    * @return AppResultDto
    * @Date 2022/1/4 15:21
    **/
    AppResultDto remoteRoleAuthResource(RemoteRoleAuthResourceReqDTO remoteRoleAuthResourceReqDTO);

}
