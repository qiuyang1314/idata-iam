package com.zyaud.idata.iam.common.errorcode;

import com.zyaud.fzhx.common.exception.BaseException;

/**
 * 资源管理
 */
public enum AppMngErrorEnum implements BaseException {
    APP_NAME_IS_EXIST(1, "应用名已存在"),
    APP_CODE_IS_EXIST(2, "应用编码已存在"),
    APP_IS_NULL(3, "应用不存在！"),
    APP_ID_CANNOT_BE_NULL(4, "应用id不能为空"),
    APP_CODE_IS_NOT_MATCH(5, "应用不符"),
    SIGN_CANNOT_BE_NULL(6, "签名不能为空"),
    PRIVATE_KEY_IS_NOT_EXIST(7, "应用私钥不存在"),
    PUBLIC_KEY_IS_NOT_EXIST(8, "应用公钥不存在"),
    VERIFY_SIGN_FAIL(9, "验签失败"),
    TOKEN_CANNOT_BE_NULL(10, "token不能为空"),
    APP_CODE_IS_NULL(11, "获取应用编码失败,请重新登录"),
    APP_IS_NULL_OR_EXIST_MULTIPLE_SAME(12, "获取应用失败:应用不存在或存在多个相同的应用"),
    APP_CODE_CANNOT_BE_NULL(13, "应用编码不能为空")
    ;

    private int code;
    private String message;

    AppMngErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return ErrorCodeUtil.ERROR_APP_CODE + ErrorCodeUtil.MODULE_APP_MNG + code;
    }

    public String getMessage() {
        return message;
    }
}
