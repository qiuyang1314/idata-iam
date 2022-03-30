package com.zyaud.idata.iam.common.errorcode;

/**
 * @program: fiaud-das
 * @description: 登录注册服务枚举
 * @author: qiuyang
 * @create: 2021-03-25 10:23
 **/
public enum LoginServiceEnum {
    USER_ID("user_id"),
    ORG_ID("org_id"),
    EMAIL("email"),
    ORG_NAME("org_name"),
    LOGIN_NAME("login_name"),
    MOBILEPHONE("mobilephone"),
    STATUS("status"),
    ACTIVE("ACTIVE"),
    NORMAL("normal"),
    IDCARD("idcard"),
    ID("id"),
    NAME("name"),
    TYPE("type"),
    SORT("sort"),
    PARENT("parent"),
    SSOTOKEN("ssotoken"),
    RESOURCE_TYPE("resource_type"),
    PT_APP("pt_app"),
    NULL(""),
    PRIVILEGE_CODE("privilege_code"),
    ACCESS("access"),
    RESOURCE_ID("resource_id"),
    TOTAL("total"),
    UNIT("unit"),
    PRINCIPAL_CLASS("principal_class"),
    PT_ORGANIZATION("pt_organization"),
    PRINCIPAL_VALUE1("principal_value1"),
    ORDINARY("ordinary");

    private final String name;

    LoginServiceEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
