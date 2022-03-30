package com.zyaud.idata.iam.biz.third;

import com.zyaud.idata.iam.biz.third.appResultDto.AppResultDto;
import com.zyaud.idata.iam.biz.third.userRole.RemoteUserAuthRoleReqDTO;

/**
 * @program: idata-iam
 * @description: 用户授权角色第三方接口
 * @author: qiuyang
 * @create: 2022-01-04 15:24
 **/
public interface ThirdUserAuthRoleApi {

    /**
    * @Description 用户授权角色
    * @Author qiuyang
    * @param remoteUserAuthRoleReqDTO
    * @return AppResultDto
    * @Date 2022/1/4 15:46
    **/
    AppResultDto remoteUserAuthRole(RemoteUserAuthRoleReqDTO remoteUserAuthRoleReqDTO);
}
