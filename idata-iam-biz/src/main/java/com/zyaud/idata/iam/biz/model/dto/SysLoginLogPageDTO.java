package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import com.zyaud.fzhx.core.model.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysLoginLogPageDTO extends PageParam {

    /**
     * 操作ip地址
     */
    @BindQuery(field = "remote_ip", comparison = Comparison.LIKE)
    @Length(max = 128, message = "操作ip地址长度不能超过128")
    private String remoteIp;


    /**
     * 账号
     */
    @BindQuery(field = "login_name", comparison = Comparison.LIKE)
    @Length(max = 64, message = "账号长度不能超过64")
    private String loginName;


    /**
     * 状态（0成功 1失败）
     */
    @BindQuery(field = "status", comparison = Comparison.EQ)
    @Length(max = 1, message = "状态长度不能超过1")
    private String status;

}
