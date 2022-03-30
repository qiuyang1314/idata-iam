package com.zyaud.idata.iam.biz.service;

import com.zyaud.idata.iam.biz.model.vo.UserCodeAndUserAndAppAndOfficeInfoVO;

import java.util.List;

public interface IExternalApiService {
    List<UserCodeAndUserAndAppAndOfficeInfoVO> getUserCodeAndUserAndAppAndOfficeInfoByUserCodeIds(List<String> userCodeIds);
}
