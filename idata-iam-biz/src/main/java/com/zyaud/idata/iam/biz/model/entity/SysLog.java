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
@TableName(SysLog.SYSLOG)
public class SysLog extends TimeEntity<String> {

    private static final long serialVersionUID = 1L;
    public static final String SYSLOG = "iam_sys_log";
    public static final String APPCODE = "app_code";
    public static final String MODULE = "module";
    public static final String LOGTYPE = "log_type";
    public static final String REMOTEIP = "remote_ip";
    public static final String OPERATETYPE = "operate_type";
    public static final String OPERATOR = "operator";
    public static final String INFOS = "infos";
    public static final String BROWSER = "browser";
    public static final String OS = "os";
    public static final String PARAMS = "params";
    public static final String RESULT = "result";
    public static final String ERRMSG = "err_msg";


    /**
     * 应用编码
     */
    @TableField(APPCODE)
    @Length(max = 64, message = "系统应用id长度不能超过64")
    private String appCode;


    /**
     * 模块
     */
    @TableField(MODULE)
    @Length(max = 255, message = "模块长度不能超过255")
    private String module;


    /**
     * 日志类型(1普通2系统3安全4审计)
     */
    @TableField(LOGTYPE)
    @Length(max = 1, message = "日志类型(1普通2系统3安全4审计)长度不能超过1")
    private String logType;


    /**
     * 操作ip地址
     */
    @TableField(REMOTEIP)
    @Length(max = 128, message = "操作ip地址长度不能超过128")
    private String remoteIp;


    /**
     * 操作类型
     */
    @TableField(OPERATETYPE)
    @Length(max = 1, message = "操作类型长度不能超过1")
    private String operateType;


    /**
     * 操作人
     */
    @TableField(OPERATOR)
    @Length(max = 255, message = "操作人长度不能超过255")
    private String operator;


    /**
     * 操作明细
     */
    @TableField(INFOS)
    private String infos;


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
     * 入参
     */
    @TableField(PARAMS)
    private String params;

    /**
     * 出参
     */
    @TableField(RESULT)
    private String result;

    /**
     * 异常信息
     */
    @TableField(ERRMSG)
    private String errMsg;
}
