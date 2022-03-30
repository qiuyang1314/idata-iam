package com.zyaud.idata.iam.common.errorcode;

import com.zyaud.fzhx.common.exception.BaseException;

public enum DictMngErrorEnum implements BaseException {
    STD_CODE_NAME_IS_EXIST(1, "操作失败:字典项名称已存在"),
    STD_CODE_VALUE_IS_EXIST(2, "操作失败:字典项值已存在"),
    STD_CODE_IS_NULL(3, "操作失败:该字典项已被删除"),
    STD_CODE_NAME_IS_SYS_DATA_CANNOT_DELETE(4, "字典值是系统数据，不能删除！"),
    STD_TYPE_STD_NAME_IS_EXIST(5, "字典名称已存在，请修改"),
    STD_TYPE_STD_NUM_IS_EXIST(6, "字典编号已存在，请修改"),
    STD_TYPE_IS_DELETE_UPDATE_FAIL(7, "编辑失败:该字典已被删除"),
    STD_TYPE_IS_ZERO_UPDATE_FAIL(8, "编辑失败,内置字典不可编辑"),
    STD_TYPE_IS_ZERO_DELETE_FAIL(9, "删除失败:内置字典不可删除"),
    STD_TYPE_IS_NOT_EXIST_BY_STD_NUM(10, "该字典值对应的字典不存在"),
    STD_CODE_VALUE_EXIST_NON_INTEGER(11, "字典值存在非数字，请调整后重试！"),
    STD_CODE_ID_CANNOT_BE_NULL(12,"id不能为空")
    ;

    private int code;
    private String message;

    DictMngErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return ErrorCodeUtil.ERROR_APP_CODE + ErrorCodeUtil.MODULE_DICT_MNG + code;
    }

    public String getMessage() {
        return message;
    }
}
