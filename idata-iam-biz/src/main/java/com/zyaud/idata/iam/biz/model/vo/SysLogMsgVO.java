package com.zyaud.idata.iam.biz.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class SysLogMsgVO {
    /**
     * 登录ip
     */
    private String ip;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 登录状态
     */
    private String success;
    /**
     * 登录次数
     */
    private Integer loginCount;
    /**
     * 登录时间
     */
    private LocalDateTime loginTime;
    /**
     * 系统名称
     */
    private String sysName;
}
