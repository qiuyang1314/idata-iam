package com.zyaud.idata.iam.common.errorcode;

import com.zyaud.fzhx.common.exception.BaseException;

/**
 * 资源管理
 */
public enum MenuMngErrorEnum implements BaseException {
    PARENT_MENU_IS_NULL(1, "上级菜单不存在"),
    PARENT_MENU_IS_BUTTON(2, "上级菜单为按钮，不可添加下级"),
    OPENTYPE_IS_NULL(3, "打开方式不能为空"),
    MENU_ID_CANNOT_BE_NULL(4, "菜单id不能为空"),
    MENU_IS_NULL(5,"菜单获取失败:请给当前用户角色绑定菜单")
    ;

    private int code;
    private String message;

    MenuMngErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return ErrorCodeUtil.ERROR_APP_CODE + ErrorCodeUtil.MODULE_MENU_MNG + code;
    }

    public String getMessage() {
        return message;
    }
}
