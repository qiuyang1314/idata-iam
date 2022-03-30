package com.zyaud.idata.iam.api.client;

import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.log.annotation.SysLog;
import com.zyaud.fzhx.log.enums.OptypeEnum;
import com.zyaud.idata.iam.api.req.DTO.RemoteLoginReqDTO;
import com.zyaud.idata.iam.api.req.DTO.RemoteOrgDTO;
import com.zyaud.idata.iam.api.req.DTO.RemoteUserDTO;
import com.zyaud.idata.iam.api.req.FindAppsReqVO;
import com.zyaud.idata.iam.api.req.LoginReqVO;
import com.zyaud.idata.iam.api.req.TokenAnalysisReqVO;
import com.zyaud.idata.iam.api.resp.FindAppsRespVO;
import com.zyaud.idata.iam.api.resp.LoginRespVO;
import com.zyaud.idata.iam.api.resp.RemoteLoginRspVO;
import com.zyaud.idata.iam.api.resp.TokenAnalysisRespVO;
import com.zyaud.idata.iam.common.vo.PublicIdReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@RequestMapping({"/sdjw"})
@Api(tags = "提供浪潮系统调用服务模块")
public interface ILangCaoClient {
    @ApiOperation(value = "登录浪潮系统")
    @PostMapping({"/login"})
    @SysLog(value = "'登录,应用id:' + #requestVO.appId + ',登录名:' +#requestVO.loginName", optype = OptypeEnum.SELECT)
    Result<LoginRespVO> login(@RequestBody @Validated LoginReqVO requestVO);

    @ApiOperation(value = "token解析")
    @PostMapping({"/tokenAnalysis"})
    @SysLog(value = "'token解析,应用id:' + #requestVO.appId + ", optype = OptypeEnum.SELECT)
    Result<TokenAnalysisRespVO> tokenAnalysis(@RequestBody @Validated TokenAnalysisReqVO requestVO);

    @ApiOperation(value = "获取应用列表")
    @PostMapping({"/findApps"})
    @SysLog(value = "'获取应用列表,应用id:' + #requestVO.appId + ", optype = OptypeEnum.SELECT)
    Result<List<FindAppsRespVO>> findApps(@RequestBody @Validated FindAppsReqVO requestVO);



    /**
     * @Description: 第三方登录iam
     * @Author: dong
     * @param remoteLoginReqDTO
     * @return: com.zyaud.fzhx.common.model.Result<java.util.List<com.zyaud.idata.iam.api.resp.FindAppsRespVO>>
     * @Date: 2022/1/8 16:00
     */
    @ApiOperation(value = "第三方登录iam系统")
    @PostMapping({"/loginIam"})
    Result<RemoteLoginRspVO> loginIam(@RequestBody RemoteLoginReqDTO remoteLoginReqDTO);



    /**
    * @Description: 同步浪潮机构接口
    * @Author: dong
    * @param remoteOrgDTO
    * @return: com.zyaud.fzhx.common.model.Result<java.lang.String>
    * @Date: 2022/1/7 14:47
    */
    @Deprecated
    @ApiOperation(value = "同步浪潮机构接口")
    @PostMapping({"/remoteOrg"})
    @SysLog(value = "'同步浪潮机构接口,机构id' + #remoteOrgDTO.data.organId + '操作类型 :'+ #remoteOrgDTO.type")
    Result<String> remoteOrg(@RequestBody @Validated RemoteOrgDTO remoteOrgDTO);


    /**
    * @Description: 同步浪潮用户接口
    * @Author: dong
    * @param remoteUserDTO
    * @return: com.zyaud.fzhx.common.model.Result<java.lang.String>
    * @Date: 2022/1/7 15:47
    */
    @Deprecated
    @ApiOperation(value = "同步浪潮用户接口")
    @PostMapping({"/remoteUser"})
    @SysLog(value = "'同步浪潮用户接口,机构id' + #remoteUserDTO.data.organId + '操作类型 :'+ #remoteUserDTO.type")
    Result<String> addUser(@RequestBody @Validated RemoteUserDTO remoteUserDTO);



    @ApiOperation(value = "同步浪潮数据")
    @PostMapping(value = "/synchronizationData")
    @SysLog(value = "'同步浪潮数据,应用id:' + #publicIdReqVO.id", optype = OptypeEnum.UPDATE,request = false)
     Result<String> synchronizationData(@Validated @RequestBody PublicIdReqVO publicIdReqVO) throws IOException;

    /**
     * @Description: 浪潮cas登录返回用户id
     * @Author: dong
     * @param publicIdReqVO
     * @return: com.zyaud.fzhx.common.model.Result
     * @Date: 2022/1/13 15:32
     */
    @ApiOperation(value = "浪潮cas登录返回用户id")
    @PostMapping(value = "/casLogin")
    @SysLog(value = "'浪潮cas登录返回用户id,票据id:' + #publicIdReqVO.id",request = false)
     Result<String> casLogin(@Validated @RequestBody PublicIdReqVO publicIdReqVO);



}
