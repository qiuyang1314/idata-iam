package com.zyaud.idata.iam.biz.service;

import com.zyaud.idata.iam.api.req.DTO.RemoteLoginReqDTO;
import com.zyaud.idata.iam.api.req.DTO.RemoteOrgRqeDTO;
import com.zyaud.idata.iam.api.req.DTO.RemoteUserRqeDTO;
import com.zyaud.idata.iam.api.req.FindAppsReqVO;
import com.zyaud.idata.iam.api.req.LoginReqVO;
import com.zyaud.idata.iam.api.req.TokenAnalysisReqVO;
import com.zyaud.idata.iam.api.resp.FindAppsRespVO;
import com.zyaud.idata.iam.api.resp.LoginRespVO;
import com.zyaud.idata.iam.api.resp.RemoteLoginRspVO;
import com.zyaud.idata.iam.api.resp.TokenAnalysisRespVO;

import java.util.List;

public interface ILangCaoService {
    LoginRespVO login(LoginReqVO requestVO);

    TokenAnalysisRespVO tokenAnalysis(TokenAnalysisReqVO requestVO);

    List<FindAppsRespVO> findApps(FindAppsReqVO requestVO);
    
    /** 
    * @Description: 新增机构
    * @Author: dong 
    * @param remoteOrgRqeDTO
    * @return: void
    * @Date: 2022/1/7 15:00
    */ 
    void  addOrg(RemoteOrgRqeDTO remoteOrgRqeDTO);
    
    /** 
    * @Description: 删除机构
    * @Author: dong 
    * @param remoteOrgRqeDTO
    * @return: void
    * @Date: 2022/1/7 15:00
    */ 
    void  deleteOrg(RemoteOrgRqeDTO remoteOrgRqeDTO);
    
    
    /** 
    * @Description: 修改机构
    * @Author: dong 
    * @param remoteOrgRqeDTO
    * @return: void
    * @Date: 2022/1/7 15:00
    */ 
    void  updateOrg(RemoteOrgRqeDTO remoteOrgRqeDTO);



    /**
    * @Description: 新增用户
    * @Author: dong
    * @param
    * @return: void
    * @Date: 2022/1/7 16:16
    */
    void  addUser(RemoteUserRqeDTO remoteUserRqeDTO);

    /**
    * @Description: 删除用户
    * @Author: dong
    * @param
    * @return: void
    * @Date: 2022/1/7 16:16
    */
    void  deleteUser(RemoteUserRqeDTO remoteUserRqeDTO);

    /**
    * @Description: 更新用户
    * @Author: dong
    * @param
    * @return: void
    * @Date: 2022/1/7 16:16
    */
    void  updateUser(RemoteUserRqeDTO remoteUserRqeDTO);

    
    /** 
    * @Description: 登录iam
    * @Author: dong 
    * @param remoteLoginReqDTO
    * @return: com.zyaud.idata.iam.api.resp.RemoteLoginRspVO
    * @Date: 2022/1/8 16:16
    */ 
    RemoteLoginRspVO loginIam(RemoteLoginReqDTO remoteLoginReqDTO);
}
