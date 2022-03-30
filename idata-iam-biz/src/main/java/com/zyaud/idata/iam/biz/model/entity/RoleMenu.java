package com.zyaud.idata.iam.biz.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * <p>
 * 角色菜单
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */

@Data
@Accessors(chain = true)
@TableName(RoleMenu.ROLEMENU)
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String ROLEMENU = "iam_role_menu";
    public static final String APPID = "app_id";
    public static final String ROLEID = "role_id";
    public static final String MENUID = "menu_id";


    /**
     * 系统id
     */
    @TableField(APPID)
    @Length(max = 64, message = "系统id长度不能超过64")
    private String appId;


    /**
     * 角色id
     */
    @TableField(ROLEID)
    @Length(max = 64, message = "角色id长度不能超过64")
    private String roleId;


    /**
     * 菜单id
     */
    @TableField(MENUID)
    @Length(max = 64, message = "菜单id长度不能超过64")
    private String menuId;


}
