package com.zyaud.idata.iam.common.errorcode;

import com.zyaud.fzhx.common.exception.BaseException;

/**
 * 账号密码
 */
public enum UserMngErrorEnum implements BaseException {
    IDCARD_IS_EXIST(1, "身份证号已存在"),
    USER_IS_NOT_EXIST_IN_THIS_OFFICE(2, "该机构下无用户"),
    USERCODE_NOTEXIST(3, "未查找到相应用户账号信息"),
    USER_NOTSET(4, "未配置相应的用户"),
    EMPTY_ROLE(5, "角色不能为空");

    private int code;
    private String message;

    UserMngErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return ErrorCodeUtil.ERROR_APP_CODE + ErrorCodeUtil.MODULE_USER_MNG + code;
    }

    public String getMessage() {
        return message;
    }
}
