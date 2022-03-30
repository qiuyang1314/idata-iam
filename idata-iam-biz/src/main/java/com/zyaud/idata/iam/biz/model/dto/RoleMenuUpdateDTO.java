package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * <p>
 * 角色菜单
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
public class RoleMenuUpdateDTO implements Serializable {

    private static final long serialVersionUID=1L;
    /**
     * 系统id
     */
    @BindQuery(field = "app_id", comparison = Comparison.LIKE)
    @Length(max = 64, message = "系统id长度不能超过64")
    private String appId;

    /**
     * 角色id
     */
    @BindQuery(field = "role_id", comparison = Comparison.LIKE)
    @Length(max = 64, message = "角色id长度不能超过64")
    private String roleId;

    /**
     * 菜单id
     */
    @BindQuery(field = "menu_id", comparison = Comparison.LIKE)
    @Length(max = 64, message = "菜单id长度不能超过64")
    private String menuId;




}
