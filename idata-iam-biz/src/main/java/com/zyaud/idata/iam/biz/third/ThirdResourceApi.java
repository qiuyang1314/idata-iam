package com.zyaud.idata.iam.biz.third;

import com.zyaud.idata.iam.biz.third.appResultDto.AppResultDto;
import com.zyaud.idata.iam.biz.third.resource.RemoteResourceCreateReqDTO;
import com.zyaud.idata.iam.biz.third.resource.RemoteResourceDeleteReqDTO;
import com.zyaud.idata.iam.biz.third.resource.RemoteResourceUpdateReqDTO;

/**
 * @program: idata-iam
 * @description: 第三方菜单同步接口
 * @author: qiuyang
 * @create: 2022-01-04 11:05
 **/
public interface ThirdResourceApi {

    /**
    * @Description 新增菜单
    * @Author qiuyang
    * @param remoteResourceCreateReqDTO
    * @return AppResultDto
    * @Date 2022/1/4 14:52
    **/
    AppResultDto remoteAddResource(RemoteResourceCreateReqDTO remoteResourceCreateReqDTO);


    /**
    * @Description 修改菜单
    * @Author qiuyang
    * @param remoteResourceUpdateReqDTO
    * @return AppResultDto
    * @Date 2022/1/4 15:01
    **/
    AppResultDto remoteUpdateResource(RemoteResourceUpdateReqDTO remoteResourceUpdateReqDTO);

    /**
     * @Description 删除菜单
     * @Author qiuyang
     * @param remoteResourceDeleteReqDTO
     * @return AppResultDto
     * @Date 2022/1/4 15:01
     **/
    AppResultDto remoteDeleteResource(RemoteResourceDeleteReqDTO remoteResourceDeleteReqDTO);

}
