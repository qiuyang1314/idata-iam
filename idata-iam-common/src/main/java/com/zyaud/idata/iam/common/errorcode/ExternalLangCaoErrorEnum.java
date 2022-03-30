package com.zyaud.idata.iam.common.errorcode;

import com.zyaud.fzhx.common.exception.BaseException;

public enum ExternalLangCaoErrorEnum implements BaseException {
    LOGIN_LANG_CAO_SYSTEM_ERROR(1, "登录浪潮系统异常！异常提示："),
    GET_LANG_CAO_USER_INFO_ERROR(2, "获取浪潮用户信息异常！异常提示："),
    SYNCHRONIZE_LANG_CAO_USER_INFO_ERROR(3, "同步浪潮用户信息异常！异常提示："),
    SYNCHRONIZE_LANG_CAO_CORPORATION_ERROR(4, "同步浪潮机构信息异常！异常提示："),
    SYNCHRONIZE_LANG_CAO_ROLE_ERROR(5, "同步浪潮角色信息异常！异常提示：")
    ;

    private int code;
    private String message;

    ExternalLangCaoErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return ErrorCodeUtil.ERROR_APP_CODE + ErrorCodeUtil.MODULE_LANG_CAO + code;
    }

    public String getMessage() {
        return message;
    }
}
