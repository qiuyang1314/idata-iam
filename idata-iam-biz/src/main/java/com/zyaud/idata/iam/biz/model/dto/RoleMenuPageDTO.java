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
 * 角色菜单
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoleMenuPageDTO extends PageParam {

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
