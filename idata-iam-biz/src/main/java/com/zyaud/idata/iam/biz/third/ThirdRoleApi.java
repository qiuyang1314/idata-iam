package com.zyaud.idata.iam.biz.third;

import com.zyaud.idata.iam.biz.third.appResultDto.AppResultDto;
import com.zyaud.idata.iam.biz.third.role.RemoteRoleCreateReqDto;
import com.zyaud.idata.iam.biz.third.role.RemoteRoleDeleteReqDto;
import com.zyaud.idata.iam.biz.third.role.RemoteRoleUpdateReqDto;

/**
 * @program: idata-iam
 * @description: 第三方角色同步接口
 * @author: qiuyang
 * @create: 2022-01-04 15:11
 **/
public interface ThirdRoleApi {

    /**
    * @Description 新增角色
    * @Author qiuyang
    * @param remoteRoleCreateReqDto
    * @return AppResultDto
    * @Date 2022/1/4 15:12
    **/
    AppResultDto remoteAddRole(RemoteRoleCreateReqDto remoteRoleCreateReqDto);

    /**
    * @Description 更新角色
    * @Author qiuyang
    * @param remoteRoleUpdateReqDto
    * @return AppResultDto
    * @Date 2022/1/4 15:13
    **/
    AppResultDto remoteUpdateRole(RemoteRoleUpdateReqDto remoteRoleUpdateReqDto);

    /**
    * @Description 删除角色
    * @Author qiuyang
    * @param remoteRoleDeleteReqDto
    * @return AppResultDto
    * @Date 2022/1/4 15:14
    **/
    AppResultDto remoteDeleteRole(RemoteRoleDeleteReqDto remoteRoleDeleteReqDto);

}
