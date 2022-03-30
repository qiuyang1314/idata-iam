package com.zyaud.idata.iam.common.errorcode;

import com.zyaud.fzhx.common.exception.BaseException;

/**
 * 账号密码
 */
public enum RoleMngErrorEnum implements BaseException {
    ROLE_NAME_IS_EXIST(1, "角色名已存在"),
    ROLE_CODE_IS_EXIST(2, "角色编码已存在"),
    ROLE_IS_EXIST(3, "角色已存在"),
    ROLE_IS_NOT_EXIST(4, "角色不存在"),
    BUILT_IN_ROLE_CANNOT_DELETE(5, "内置角色不允许删除"),
    BUILT_IN_ROLE_CANNOT_UPDATE(6, "内置角色不允许编辑"),
    ROLE_EXCLUSION_CANNOT_JOIN(7, "角色互斥，不可继续加入"),
    APP_ROLE_NOT_EXIST(8, "应用不存在该角色");

    private int code;
    private String message;

    RoleMngErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return ErrorCodeUtil.ERROR_APP_CODE + ErrorCodeUtil.MODULE_ROLE_MNG + code;
    }

    public String getMessage() {
        return message;
    }
}
