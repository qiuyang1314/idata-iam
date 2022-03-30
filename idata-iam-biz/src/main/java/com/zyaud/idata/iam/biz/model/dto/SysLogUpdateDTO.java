package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import com.zyaud.fzhx.core.model.IdEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class SysLogUpdateDTO implements Serializable {

    private static final long serialVersionUID=1L;
    @NotNull(message = "id不能为空", groups = IdEntity.Update.class)
    private String id;
    /**
     * 系统应用id
     */
    @BindQuery(field = "app_id", comparison = Comparison.LIKE)
    @Length(max = 64, message = "系统应用id长度不能超过64")
    private String appId;

    /**
     * 模块
     */
    @BindQuery(field = "module", comparison = Comparison.LIKE)
    @Length(max = 255, message = "模块长度不能超过255")
    private String module;

    /**
     * 日志类型(1普通2系统3安全4审计)
     */
    @BindQuery(field = "log_type", comparison = Comparison.LIKE)
    @Length(max = 1, message = "日志类型(1普通2系统3安全4审计)长度不能超过1")
    private String logType;

    /**
     * 操作ip地址
     */
    @BindQuery(field = "remote_ip", comparison = Comparison.LIKE)
    @Length(max = 128, message = "操作ip地址长度不能超过128")
    private String remoteIp;

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
    @BindQuery(field = "infos")
    private String infos;




}
