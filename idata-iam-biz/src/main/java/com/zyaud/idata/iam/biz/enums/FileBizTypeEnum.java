package com.zyaud.idata.iam.biz.enums;

public enum FileBizTypeEnum {
    USER_ICON(0, "用户头像文件"),
    HOME_BANNER(1, "首页轮播图");

    private int bizCode;
    private String desc;

    FileBizTypeEnum(int bizCode, String desc) {
        this.bizCode = bizCode;
        this.desc = desc;
    }

    public int getBizCode() {
        return bizCode;
    }

    public void setBizCode(int bizCode) {
        this.bizCode = bizCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
