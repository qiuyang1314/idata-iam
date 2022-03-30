package com.zyaud.idata.iam.biz.service;

import com.zyaud.idata.iam.biz.model.vo.AuthParam;
import com.zyaud.idata.iam.biz.model.vo.LoginRspVO;

public interface ILoginService {
    LoginRspVO login(AuthParam authParam);
}
