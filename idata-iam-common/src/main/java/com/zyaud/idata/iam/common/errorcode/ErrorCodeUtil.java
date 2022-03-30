package com.zyaud.idata.iam.common.errorcode;

import cn.hutool.core.util.ObjectUtil;
import com.zyaud.fzhx.common.exception.BaseException;
import com.zyaud.fzhx.common.model.Result;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ErrorCodeUtil {
    public final static int ERROR_APP_CODE = 1000000;

    /**
     * 模块定义
     */
    /**
     * 应用管理
     */
    public final static int MODULE_APP_MNG = 1000;

    /**
     * 机构管理
     */
    public final static int MODULE_OFFICE_MNG = 2000;

    /**
     * 角色管理
     */
    public final static int MODULE_ROLE_MNG = 3000;

    /**
     * 用户管理
     */
    public final static int MODULE_USER_MNG = 4000;

    /**
     * 账号管理
     */
    public final static int MODULE_ACCOUNT_MNG = 5000;

    /**
     * 菜单管理
     */
    public final static int MODULE_MENU_MNG = 6000;

    /**
     * 权限管理
     */
    public final static int MODULE_RIGHT_MNG = 7000;

    /**
     * 字典管理
     */
    public final static int MODULE_DICT_MNG = 8000;

    /**
     * 日志管理
     */
    public final static int MODULE_LOG_MNG = 9000;

    /**
     * 意见
     */
    public final static int MODULE_SUGGEST_MNG = 10000;

    /**
     * 系统问题
     */
    public final static int MODULE_SYSTEM = 11000;
    /**
     * 浪潮
     */
    public final static int MODULE_LANG_CAO = 12000;


    public static Result genFailResult(BaseException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    public static Result genFailResult(BaseException e, String message) {
        return Result.fail(e.getCode(), ObjectUtil.isEmpty(message) ? e.getMessage() : message);
    }
}
