package com.zyaud.idata.iam.common.errorcode;

import com.zyaud.fzhx.common.exception.BaseException;

/**
 * 资源管理
 */
public enum OfficeMngErrorEnum implements BaseException {
    PARENT_OFFICE_IS_NULL(1, "上级机构不存在"),
    OFFICE_IS_NULL(2, "机构不存在"),
    PARENT_OFFICE_IS_DELETE(3, "操作失败:父级机构已被删除"),
    OFFICE_IS_DELETE(4, "操作失败:该机构已被删除"),
    OFFICE_NAME_IS_EXIST(5, "机构名称已存在"),
    OFFICE_CODE_IS_EXIST(6, "机构编码已存在"),
    ;

    private int code;
    private String message;

    OfficeMngErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return ErrorCodeUtil.ERROR_APP_CODE + ErrorCodeUtil.MODULE_OFFICE_MNG + code;
    }

    public String getMessage() {
        return message;
    }
}
