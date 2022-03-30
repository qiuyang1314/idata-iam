package com.zyaud.idata.iam.biz.strategy;

import com.zyaud.idata.iam.biz.model.vo.AuthParam;
import com.zyaud.idata.iam.biz.model.vo.LoginRspVO;

public interface LoginStrategy {
    void login(LoginRspVO loginRspVO, AuthParam authParam);

    boolean isSupport(LoginRspVO loginRspVO, AuthParam authParam);
}
