package com.zyaud.idata.iam.common.errorcode;

import com.zyaud.fzhx.common.exception.BaseException;

public enum SuggestMngErrorEnum implements BaseException {
    PROBLEM_FEEDBACK_IS_NULL_BY_ERROR_ID(1, "id错误，查询为空")
    ;

    private int code;
    private String message;

    SuggestMngErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return ErrorCodeUtil.ERROR_APP_CODE + ErrorCodeUtil.MODULE_SUGGEST_MNG + code;
    }

    public String getMessage() {
        return message;
    }
}
