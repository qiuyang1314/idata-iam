package com.zyaud.idata.iam.common.utils;

import cn.hutool.core.util.ObjectUtil;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.iam.core.FzhxIam;
import com.zyaud.fzhx.iam.core.FzhxIamUser;

import java.util.Map;
import java.util.Set;

public class MngUtil {

    public static String getDomain() {
        Map<String, Object> extMap = FzhxIam.getUser().getExt();
        if (ObjectUtil.isEmpty(extMap) || ObjectUtil.isEmpty(extMap.get("domain"))) {
            BizAssert.fail("当前账号未绑定用户,请先绑定用户");
        }
        return (String) extMap.get("domain");
    }

    public static FzhxIamUser getFzhxIamUser() {
        return FzhxIam.getUser();
    }

    public static String getUserId() {
        FzhxIamUser user = FzhxIam.getUser();
        user.getExt();
        BizAssert.isFalse(ObjectUtil.isEmpty(user), "获取当前登录用户失败,请重新登录");
        return user.getUserId();
    }

    public static String getUserName() {
        FzhxIamUser user = FzhxIam.getUser();
        BizAssert.isFalse(ObjectUtil.isEmpty(user), "获取当前登录用户失败,请重新登录");
        return user.getName();
    }

    private static boolean matchRole(String roleCnName) {
        Set<String> roles = FzhxIam.getUser().getRoles();
        if (ObjectUtil.isNotEmpty(roles)) {
            return roles.contains(roleCnName);
        }

        return false;
    }

    public static boolean isSystemAdmin() {
        return matchRole("档案管理角色");
    }
}

