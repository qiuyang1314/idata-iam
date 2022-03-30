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
 * 系统应用角色
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RolePageDTO extends PageParam {

    private static final long serialVersionUID=1L;
    /**
     * 系统应用id
     */
    @BindQuery(field = "app_id", comparison = Comparison.LIKE)
    @Length(max = 64, message = "系统应用id长度不能超过64")
    private String appId;

    /**
     * 角色名称
     */
    @BindQuery(field = "role_name", comparison = Comparison.LIKE)
    @Length(max = 128, message = "角色名称长度不能超过128")
    private String roleName;

    /**
     * 角色编码
     */
    @BindQuery(field = "role_code", comparison = Comparison.LIKE)
    @Length(max = 128, message = "角色编码长度不能超过128")
    private String roleCode;

    /**
     * 是否可用
     */
    @BindQuery(field = "useable", comparison = Comparison.LIKE)
    @Length(max = 1, message = "是否可用长度不能超过1")
    private String useable;
}
