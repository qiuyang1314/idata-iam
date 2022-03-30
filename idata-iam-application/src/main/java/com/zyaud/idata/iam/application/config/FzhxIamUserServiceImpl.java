package com.zyaud.idata.iam.application.config;

import cn.hutool.core.util.ObjectUtil;
import com.zyaud.fzhx.cache.service.CacheService;
import com.zyaud.fzhx.common.enums.ResultStatus;
import com.zyaud.fzhx.common.exception.IamException;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.iam.core.FzhxIamUser;
import com.zyaud.fzhx.iam.core.FzhxIamUserService;
import com.zyaud.idata.iam.biz.service.IUserCodeService;
import com.zyaud.idata.iam.biz.service.IUserService;
import com.zyaud.idata.iam.common.utils.Constants;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class FzhxIamUserServiceImpl implements FzhxIamUserService {

    @Resource
    private CacheService cacheService;

    @Override
    public FzhxIamUser loadUserByUserId(String loginName) throws IamException {
        FzhxIamUser fzhxIamUser = cacheService.get(Constants.IAM_LOGIN, Constants.USER_KEY + loginName);
        if (ObjectUtil.isEmpty(fzhxIamUser)) {
            BizAssert.fail(ResultStatus.JWT_TOKEN_EXPIRED);
        }
        return fzhxIamUser;
    }
}
