package com.zyaud.idata.iam.biz.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zyaud.fzhx.core.model.TimeEntity;
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
 * @since 2020-06-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(Role.ROLE)
public class Role extends TimeEntity<String> {

    private static final long serialVersionUID=1L;
    public static final String ROLE = "iam_role";
    public static final String APPID = "app_id";
    public static final String ROLENAME = "role_name";
    public static final String ROLECODE = "role_code";
    public static final String ROLETYPE = "role_type";
    public static final String USEABLE = "useable";


    /**
     * 系统应用id
     */
    @TableField(APPID)
    @Length(max = 64, message = "系统应用id长度不能超过64")
    private String appId;


    /**
     * 角色名称
     */
    @TableField(ROLENAME)
    @Length(max = 128, message = "角色名称长度不能超过128")
    private String roleName;


    /**
     * 角色编码
     */
    @TableField(ROLECODE)
    @Length(max = 128, message = "角色编码长度不能超过128")
    private String roleCode;


    /**
     * 角色类型(1内置2自定义)
     */
    @TableField(ROLETYPE)
    @Length(max = 128, message = "角色类型(1内置2自定义)长度不能超过128")
    private String roleType;


    /**
     * 是否可用
     */
    @TableField(USEABLE)
    @Length(max = 1, message = "是否可用长度不能超过1")
    private String useable;



}
