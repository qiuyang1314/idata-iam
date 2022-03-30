package com.zyaud.idata.iam.biz.controller;

import com.bjsasc.drap.pt.context.ContextUser;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.idata.iam.api.client.ILangCaoClient;
import com.zyaud.idata.iam.api.client.ITyqxClient;
import com.zyaud.idata.iam.api.req.DTO.OnlyTokenReqDto;
import com.zyaud.idata.iam.biz.service.ITyqxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: idata-iam
 * @description: 神软统一权限
 * @author: qiuyang
 * @create: 2022-02-18 11:23
 **/
@Slf4j
@RestController
public class TyqxController extends SuperController implements ITyqxClient {

    @Resource
    private ITyqxService tyqxService;

    @Override
    public Result<ContextUser> getUserInfo(String sso_code) {
        return ok(tyqxService.getUserInfo(sso_code));
    }

    @Override
    public Result<ContextUser> registerUser(ContextUser contextUser, String passWord) {
        return ok(tyqxService.registerUser(contextUser,passWord));
    }

    @Override
    public Result<String> registerUserTotal(OnlyTokenReqDto tokenDto) {
        return ok(tyqxService.registerUserTotal(tokenDto));
    }
}
