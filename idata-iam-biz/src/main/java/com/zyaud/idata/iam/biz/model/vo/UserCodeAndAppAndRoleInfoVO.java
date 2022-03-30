package com.zyaud.idata.iam.biz.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserCodeAndAppAndRoleInfoVO {
    /**
     * 应用信息
     */
    private AppInfo appInfo;
    /**
     * 用户信息
     */
    private RoleInfo roleInfo;
    /**
     * 账号信息
     */
    private UserCodeInfo userCodeInfo;

    @Data
    @Accessors(chain = true)
    public static class AppInfo {
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
    public static class RoleInfo {
        /**
         * 角色id
         */
        private String id;
        /**
         * 系统应用id
         */
        private String appId;

        /**
         * 角色名称
         */
        private String roleName;


        /**
         * 角色编码
         */
        private String roleCode;


        /**
         * 角色类型(1内置2自定义)
         */
        private String roleType;
    }

    @Data
    @Accessors(chain = true)
    public static class UserCodeInfo {
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
}
