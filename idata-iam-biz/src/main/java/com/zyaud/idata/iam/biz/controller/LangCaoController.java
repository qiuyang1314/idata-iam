package com.zyaud.idata.iam.biz.controller;


import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.idata.iam.api.client.ILangCaoClient;
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
import com.zyaud.idata.iam.biz.service.IExternalLangService;
import com.zyaud.idata.iam.biz.service.ILangCaoService;
import com.zyaud.idata.iam.biz.utils.SyncAppframe;
import com.zyaud.idata.iam.common.errorcode.ExternalLangCaoErrorEnum;
import com.zyaud.idata.iam.common.vo.PublicIdReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;


@Slf4j
@RestController
public class LangCaoController extends SuperController implements ILangCaoClient {

    public static final String   ADD = "A";  //新增
    public static final String  DELETE = "D"; //删除
    public static final String  UPDATE = "U";  //修改

    @Autowired
    private IExternalLangService externalLangService;


    @Resource
    private ILangCaoService langCaoService;

    @Resource
    private SyncAppframe syncAppframe;

    @Override
    public Result<LoginRespVO> login(LoginReqVO requestVO) {
        return this.ok(langCaoService.login(requestVO));
    }

    @Override
    public Result<TokenAnalysisRespVO> tokenAnalysis(TokenAnalysisReqVO requestVO) {
        return this.ok(langCaoService.tokenAnalysis(requestVO));
    }

    @Override
    public Result<List<FindAppsRespVO>> findApps(FindAppsReqVO requestVO) {
        return ok(langCaoService.findApps(requestVO));
    }

    /**
     * @param remoteLoginReqDTO
     * @Description: 第三方登录iam
     * @Author: dong
     * @return: com.zyaud.fzhx.common.model.Result<java.util.List < com.zyaud.idata.iam.api.resp.FindAppsRespVO>>
     * @Date: 2022/1/8 16:00
     */
    @Override
    public Result<RemoteLoginRspVO> loginIam(RemoteLoginReqDTO remoteLoginReqDTO) {
        return ok(langCaoService.loginIam(remoteLoginReqDTO));
    }



    @Override
    public Result<String> synchronizationData(PublicIdReqVO publicIdReqVO) throws IOException {
        //登录浪潮系统
        String token = externalLangService.login();
        log.info("浪潮登录token->:{}",token);
        //获取浪潮系统机构、用户、账号信息
        List<String> useIds = externalLangService.app(token);
        //新增和绑定角色
        // externalLangService.relevanceRole(useIds, publicIdReqVO.getId());

        //同步appframe用户机构
        syncAppframe.remoteBatchUser();
        syncAppframe.remoteBatchOrg();
        return ok("同步成功");
    }

    /**
     * @param publicIdReqVO
     * @Description: 浪潮cas登录返回用户id
     * @Author: dong
     * @return: com.zyaud.fzhx.common.model.Result
     * @Date: 2022/1/13 15:32
     */
    @Override
    public Result<String> casLogin(PublicIdReqVO publicIdReqVO) {
        return ok(externalLangService.casLogin(publicIdReqVO.getId()));
    }

    /**
     * @param remoteOrgDTO
     * @Description: 同步浪潮机构接口
     * @Author: dong
     * @return: com.zyaud.fzhx.common.model.Result<java.lang.String>
     * @Date: 2022/1/7 14:47
     */
    @Override
    public Result<String> remoteOrg(RemoteOrgDTO remoteOrgDTO) {
        try {
            switch(remoteOrgDTO.getType()){
                case ADD :
                    langCaoService.addOrg(remoteOrgDTO.getData());
                    break;
                case DELETE :
                    langCaoService.deleteOrg(remoteOrgDTO.getData());
                    break;
                case  UPDATE :
                    langCaoService.updateOrg(remoteOrgDTO.getData());
                    break;
            }
        } catch (Exception e) {
            log.error(ExternalLangCaoErrorEnum.SYNCHRONIZE_LANG_CAO_CORPORATION_ERROR.getMessage(),e);
            BizAssert.fail(ExternalLangCaoErrorEnum.SYNCHRONIZE_LANG_CAO_CORPORATION_ERROR.getCode(),
             ExternalLangCaoErrorEnum.SYNCHRONIZE_LANG_CAO_CORPORATION_ERROR.getMessage() + e.getMessage());
        }

        return  ok("同步机构数据成功") ;
    }


    /**
     * @param remoteOrgDTO
     * @Description: 同步浪潮机构接口
     * @Author: dong
     * @return: com.zyaud.fzhx.common.model.Result<java.lang.String>
     * @Date: 2022/1/7 14:47
     */
    @Override
    public Result<String> addUser(RemoteUserDTO remoteOrgDTO) {
        try {
            switch (remoteOrgDTO.getType()){
                case ADD :
                    langCaoService.addUser(remoteOrgDTO.getData());
                    break;
                case UPDATE:
                    langCaoService.updateUser(remoteOrgDTO.getData());
                    break;
                case DELETE:
                    langCaoService.deleteUser(remoteOrgDTO.getData());
                    break;
            }
        } catch (Exception e) {
            log.error(ExternalLangCaoErrorEnum.SYNCHRONIZE_LANG_CAO_USER_INFO_ERROR.getMessage(),e);
            BizAssert.fail(ExternalLangCaoErrorEnum.SYNCHRONIZE_LANG_CAO_USER_INFO_ERROR.getCode(),
                    ExternalLangCaoErrorEnum.SYNCHRONIZE_LANG_CAO_USER_INFO_ERROR.getMessage() + e.getMessage());
        }
        return ok("同步浪潮用户接口");
    }


}
