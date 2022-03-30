package com.zyaud.idata.iam.common.errorcode;

import com.zyaud.fzhx.common.exception.BaseException;

/**
 * 账号密码
 */
public enum AccountMngErrorEnum implements BaseException {
    USER_CODE_OR_PASSWORD_IS_FALSE(1, "账号或密码错误"),
    USER_CODE_IDS_CANNOT_BE_EMPTY(2, "账号id不能为空"),
    USER_CODE_IS_EXIST_CREATE_FAIL(3, "新增失败:账号已存在"),
    USER_CODE_IS_EXIST_UPDATE_FAIL(4, "编辑失败:账号已存在"),
    ID_CANNOT_BE_NULL_UPDATE_PWD_FAIL(5, "修改密码失败：主键不能为空"),
    NEW_PWD_CANNOT_BE_NULL_UPDATE_PWD_FAIL(6,"修改密码失败：新密码不能为空"),
    TWO_PWD_IS_NOT_EQUAL_UPDATE_PWD_FAIL(7,"修改密码失败:两次输入密码不一致"),
    USER_CODE_IS_NOT_EXIST_UPDATE_PWD_FAIL(8,"修改密码失败:账号不存在"),
    OLD_PWD_IS_NOT_CORRECT_UPDATE_PWD_FAIL(9,"修改密码失败:原密码不正确"),
    USER_CODE_IS_NOT_EXIST_BIND_USER(10,"绑定用户:账号不存在"),
    USER_IS_NOT_EXIST_BIND_USER(11,"绑定用户:用户不存在"),
    ALREADY_BIND_USER_CODE_BIND_USER(12,"绑定用户:该用户已经绑定一个账号！"),
    USER_CODE_IS_NOT_EXIST_UNBIND_USER(13,"用户解绑:账号不存在"),
    USER_CODE_IS_NOT_EXIST(14,"账号不存在"),
    USER_IS_NOT_EXIST(15,"用户不存在"),
    ROLE_IS_BIND(16,"角色已绑定"),
    ROLE_IS_NOT_EXIST_IN_THIS_USER_CODE(17,"该账号此角色不存在"),
    USER_CODE_IS_EXIST(18,"账号已存在"),
    CURRENT_USER_ID_IS_NULL(19,"获取当前登录用户失败,请重新登录")
    ;

    private int code;
    private String message;

    AccountMngErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return ErrorCodeUtil.ERROR_APP_CODE + ErrorCodeUtil.MODULE_ACCOUNT_MNG + code;
    }

    public String getMessage() {
        return message;
    }
}
