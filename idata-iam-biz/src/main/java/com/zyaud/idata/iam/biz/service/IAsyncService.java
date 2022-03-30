package com.zyaud.idata.iam.biz.service;

import com.zyaud.fzhx.iam.token.Token;

public interface IAsyncService {
    void verifyOneTerminal(String loginName, String ip, Token accessToken, Token refreshToken);

    void singleTerminal(String loginName, String userCodeId, String ip, String accessToken, String refreshToken);

    void sysLog(String msg);
}
