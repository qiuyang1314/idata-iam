package com.zyaud.idata.iam.biz.service;

import java.io.IOException;
import java.util.List;

public interface IExternalLangService {
    String login() throws IOException;

    List<String> app(String authorization) throws IOException;

    void relevanceRole(List<String> userrIds, String appId);

    /** 
    * @Description: 通过票据浪潮cas单点登录返回浪潮登录用户
    * @Author: dong 
    * @param ticket
    * @return: java.lang.String
    * @Date: 2022/1/14 11:42
    */ 
    String casLogin(String ticket);
}
