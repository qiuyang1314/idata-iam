package com.zyaud.idata.iam.biz.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

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
public class RoleMenuCreateDTO implements Serializable {

    private static final long serialVersionUID=1L;
    /**
     * 系统id
     */
    @TableField(value = "app_id", condition = LIKE)
    @Length(max = 64, message = "系统id长度不能超过64")
    private String appId;

    /**
     * 角色id
     */
    @TableField(value = "role_id", condition = LIKE)
    @Length(max = 64, message = "角色id长度不能超过64")
    private String roleId;

    /**
     * 菜单id
     */
    @TableField(value = "menu_id", condition = LIKE)
    @Length(max = 64, message = "菜单id长度不能超过64")
    private String menuId;




}
