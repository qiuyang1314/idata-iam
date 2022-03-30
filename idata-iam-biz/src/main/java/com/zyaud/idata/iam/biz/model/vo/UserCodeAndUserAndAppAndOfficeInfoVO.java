package com.zyaud.idata.iam.biz.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserCodeAndUserAndAppAndOfficeInfoVO implements Serializable {
    /**
     * 应用信息
     */
    private AppInfo appInfo;
    /**
     * 用户信息
     */
    private UserInfo userInfo;
    /**
     * 账号信息
     */
    private UserCodeInfo userCodeInfo;
    /**
     * 机构信息
     */
    private OfficeInfo officeInfo;

    @Data
    @Accessors(chain = true)
    public static class AppInfo implements Serializable {
        /**
         * 应用id
         */
        private String id;

        /**
         * 系统应用名字
         */
        private String appName;

        /**
         * 应用编码
         */
        private String appCode;

    }

    @Data
    @Accessors(chain = true)
    public static class UserInfo implements Serializable {
        /**
         * 用户id
         */
        private String id;

        /**
         * 姓名
         */
        private String name;

        /**
         * 部门
         */
        private String officeId;
    }

    @Data
    @Accessors(chain = true)
    public static class UserCodeInfo implements Serializable {
        /**
         * 账号id
         */
        private String id;

        /**
         * 用户id
         */
        private String userId;

        /**
         * 登录名
         */
        private String loginName;

        /**
         * 账号类型
         */
        private String type;
        private String typeName;

        /**
         * 状态 (0正常1锁定2禁用)
         */
        private String status;
    }

    @Data
    @Accessors(chain = true)
    public static class OfficeInfo implements Serializable {
        /**
         * 机构id
         */
        private String id;
        /**
         * 父级编号
         */
        private String parentId;

        /**
         * 名称
         */
        private String name;

        /**
         * 机构编码
         */
        private String code;

        /**
         * 全路径名称
         */
        private String paths;

        /**
         * 行政区划
         */
        private String districtId;

        /**
         * 是否启用
         */
        private String useable;
    }
}
