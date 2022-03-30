package com.zyaud.idata.iam.common.errorcode;

import com.zyaud.fzhx.common.exception.BaseException;

public enum LogMngErrorEnum implements BaseException {
    ID_CANNOT_BE_NULL(1, "id不能为空")
    ;

    private int code;
    private String message;

    LogMngErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return ErrorCodeUtil.ERROR_APP_CODE + ErrorCodeUtil.MODULE_LOG_MNG + code;
    }

    public String getMessage() {
        return message;
    }
}
