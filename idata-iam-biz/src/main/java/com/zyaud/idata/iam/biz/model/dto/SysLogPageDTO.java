package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import com.zyaud.fzhx.core.model.PageParam;
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
 * @since 2020-05-15
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysLogPageDTO extends PageParam {

    private static final long serialVersionUID = 1L;
    /**
     * 系统应用id
     */
    @BindQuery(field = "app_code", comparison = Comparison.EQ)
    @Length(max = 64, message = "应用编码长度不能超过64")
    private String appCode;

    /**
     * 模块
     */
    @BindQuery(field = "module", comparison = Comparison.LIKE)
    @Length(max = 255, message = "模块长度不能超过255")
    private String module;

    /**
     * 操作类型
     */
    @BindQuery(field = "operate_type", comparison = Comparison.LIKE)
    @Length(max = 1, message = "操作类型长度不能超过1")
    private String operateType;

    /**
     * 操作人
     */
    @BindQuery(field = "operator", comparison = Comparison.LIKE)
    @Length(max = 255, message = "操作人长度不能超过255")
    private String operator;

    /**
     * 操作明细
     */
    @BindQuery(field = "infos", comparison = Comparison.LIKE)
    private String infos;

    /**
     * 日志类型(0操作日志，1安全日志)
     */
    @BindQuery(field = "log_type", comparison = Comparison.EQ)
    @Length(max = 1, message = "日志类型(0操作日志，1安全日志)长度不能超过1")
    private String logType;

}
