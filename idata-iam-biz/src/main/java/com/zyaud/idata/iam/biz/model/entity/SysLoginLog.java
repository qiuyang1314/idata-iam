package com.zyaud.idata.iam.biz.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zyaud.fzhx.core.model.TimeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(SysLoginLog.SYSLOGINLOG)
public class SysLoginLog extends TimeEntity<String> {

    private static final long serialVersionUID = 1L;
    public static final String SYSLOGINLOG = "iam_sys_login_log";
    public static final String LOGINNAME = "login_name";
    public static final String REMOTEIP = "remote_ip";
    public static final String BROWSER = "browser";
    public static final String OS = "os";
    public static final String STATUS = "status";
    public static final String MSG = "msg";

    /**
     * 账号
     */
    @TableField(LOGINNAME)
    @Length(max = 64, message = "账号长度不能超过64")
    private String loginName;


    /**
     * 操作ip地址
     */
    @TableField(REMOTEIP)
    @Length(max = 128, message = "操作ip地址长度不能超过128")
    private String remoteIp;


    /**
     * 浏览器
     */
    @TableField(BROWSER)
    @Length(max = 64, message = "浏览器长度不能超过64")
    private String browser;


    /**
     * 操作系统
     */
    @TableField(OS)
    @Length(max = 64, message = "操作系统长度不能超过64")
    private String os;

    /**
     * 状态（0成功 1失败）
     */
    @TableField(STATUS)
    @Length(max = 1, message = "状态长度不能超过1")
    private String status;

    /**
     * 提示
     */
    @TableField(MSG)
    private String msg;

}
