package com.zyaud.idata.iam.api.client;


import com.zyaud.idata.iam.api.req.DTO.*;
import com.zyaud.idata.iam.api.resp.DTO.AppResultRspDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

/**
 * @author : qiuyang
 * @ClassName: IAppFrameClient
 * @date : 2022/1/5 15:21
 * @Description : 第三方机构同步接口
 * @Version :
 **/
@FeignClient(name = "appframe")
public interface IAppFrameClient {



    /**
    * @Description: 新增机构信息
    * @Author: dong
    * @param
    * @return: com.zyaud.fzhx.common.model.Result
    * @Date: 2021/12/27 16:21
    */
    @PostMapping("/appframe/v1/org/remote/iam")
    AppResultRspDto remoteAddOrg(RemoteOrgCreateReqDTO remoteOrgCreateRqeDTO);



    /**
    * @Description: 删除机构数据
    * @Author: dong
    * @param remoteOrgDeleteRqeDTO
    * @return: java.lang.Boolean
    * @Date: 2021/12/28 14:31
    */
    @DeleteMapping("/appframe/v1/org/remote/iam")
    AppResultRspDto remoteDeleteOrg(RemoteOrgDeleteReqDTO remoteOrgDeleteRqeDTO);





    /**
     * @Description: 修改机构数据
     * @Author: dong
     * @param remoteOrgUpdateRqeDTO
     * @return: java.lang.Boolean
     * @Date: 2021/12/28 14:31
     */
    @PutMapping("/appframe/v1/org/remote/iam")
    AppResultRspDto remoteUpdateOrg(RemoteOrgUpdateReqDTO remoteOrgUpdateRqeDTO);


    /**
     * @Description: 新增用户信息
     * @Author: dong
     * @param remoteUserInfoCreateDTO
     * @return: java.lang.Boolean
     * @Date: 2021/12/28 14:50
     */
    @PostMapping("/appframe/v1/user/remote/iam")
    AppResultRspDto remoteAddUser(RemoteUserInfoCreateReqDTO remoteUserInfoCreateDTO);


    /**
     * @Description: 修改用户信息
     * @Author: dong
     * @param remoteUserInfoUpdateDTO
     * @return: java.lang.Boolean
     * @Date: 2021/12/28 14:50
     */
    @PutMapping("/appframe/v1/user/remote/iam")
    AppResultRspDto  remoteUpdateUser(RemoteUserInfoUpdateReqDTO remoteUserInfoUpdateDTO);



    /**
     * @Description: 删除用户信息
     * @Author: dong
     * @param remoteUserInfoDeleteDTO
     * @return: java.lang.Boolean
     * @Date: 2021/12/28 14:51
     */
    @DeleteMapping("/appframe/v1/user/remote/iam")
    AppResultRspDto remoteDeleteUser(RemoteUserInfoDeleteReqDTO remoteUserInfoDeleteDTO);


    /**
    * @Description 批量同步用户信息
    * @Author qiuyang
    * @param remoteUserInfoCreateReqDTOs
    * @return
    * @Date 2022/1/7 18:49
    **/
    @PostMapping("/appframe/v1/user/remote/iam/batch")
    AppResultRspDto remoteBatchUser(List<RemoteUserInfoCreateReqDTO> remoteUserInfoCreateReqDTOs);

    /**
     * @Description 批量同步机构信息
     * @Author qiuyang
     * @param remoteOrgCreateReqDTO
     * @return
     * @Date 2022/1/7 18:49
     **/
    @PostMapping("/appframe/v1/org/remote/iam/batch")
    AppResultRspDto remoteBatchOrg(List<RemoteOrgCreateReqDTO> remoteOrgCreateReqDTO);
}
