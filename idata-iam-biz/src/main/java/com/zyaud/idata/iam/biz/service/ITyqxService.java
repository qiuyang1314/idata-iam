package com.zyaud.idata.iam.biz.service;

import com.bjsasc.drap.pt.context.ContextUser;
import com.zyaud.idata.iam.api.req.DTO.OnlyTokenReqDto;

/**
 * @program: idata-iam
 * @description: 神软统一权限处理接口
 * @author: qiuyang
 * @create: 2022-02-18 11:39
 **/
public interface ITyqxService {
    ContextUser getUserInfo(String sso_code);

    ContextUser registerUser(ContextUser contextUser, String passWord);

    String registerUserTotal(OnlyTokenReqDto tokenDto);
}
