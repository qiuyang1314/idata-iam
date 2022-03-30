package com.zyaud.idata.iam.common.errorcode;

import com.zyaud.fzhx.common.exception.BaseException;

public enum ConfigMngErrorEnum implements BaseException {
    PAST_DUE_TIME_CANNOT_LESS_THAN_ZERO(1, "密码过期时间不能小于0"),
    CHOOSE_IF_ONE_WAY_LOGIN(2, "请选择是否开启单终端登录"),
    CHOOSE_IF_SEND_SYSLOG(3, "请选择是否发送登录失败日志到syslog服务"),
    IP_SECTION(4,"ip区间在0-255"),
    FIRST_IP_CANNOT_BE_NULL(5,"第一个ip值不能为空"),
    SECOND_IP_CANNOT_BE_NULL(6,"第二个ip值不能为空"),
    THIRD_IP_CANNOT_BE_NULL(7,"第三个ip值不能为空"),
    FOURTH_IP_CANNOT_BE_NULL(8,"第四个ip值不能为空"),
    FOURTH_IP_CANNOT_GREATER_THAN_OR_EQUALS_FIFTH_IP(9,"第四个ip值不能大于或等于第五个ip值"),
    CONFIG_NOTFOUND(10, "配置项未设置")
    ;

    private int code;
    private String message;

    ConfigMngErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return ErrorCodeUtil.ERROR_APP_CODE + ErrorCodeUtil.MODULE_SYSTEM + code;
    }

    public String getMessage() {
        return message;
    }
}
