package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import com.zyaud.fzhx.core.model.IdEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 系统应用角色
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
public class RoleUpdateDTO implements Serializable {

    private static final long serialVersionUID=1L;
    @NotNull(message = "id不能为空", groups = IdEntity.Update.class)
    private String id;
    /**
     * 系统应用id
     */
    @BindQuery(field = "app_id", comparison = Comparison.LIKE)
    @Length(max = 64, message = "系统应用id长度不能超过64")
    @NotEmpty(message = "系统应用id不能为空")
    private String appId;

    /**
     * 角色名称
     */
    @BindQuery(field = "role_name", comparison = Comparison.LIKE)
    @Length(max = 128, message = "角色名称长度不能超过128")
    @NotEmpty(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色编码
     */
    @BindQuery(field = "role_code", comparison = Comparison.LIKE)
    @Length(max = 128, message = "角色编码长度不能超过128")
    @NotEmpty(message = "角色编码不能为空")
    private String roleCode;

    /**
     * 角色类型(1内置2自定义)
     */
    @BindQuery(field = "role_type", comparison = Comparison.LIKE)
    @Length(max = 128, message = "角色类型(1内置2自定义)长度不能超过128")
    @NotEmpty(message = "角色类型不能为空")
    private String roleType;

    /**
     * 是否可用
     */
    @BindQuery(field = "useable", comparison = Comparison.LIKE)
    @Length(max = 1, message = "是否可用长度不能超过1")
    private String useable;




}
