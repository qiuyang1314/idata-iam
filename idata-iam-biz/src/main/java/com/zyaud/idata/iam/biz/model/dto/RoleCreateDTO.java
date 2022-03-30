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
 * 系统应用角色
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoleCreateDTO implements Serializable {

    private static final long serialVersionUID=1L;
    /**
     * 系统应用id
     */
    @TableField(value = "app_id", condition = LIKE)
    @Length(max = 64, message = "系统应用id长度不能超过64")
    private String appId;

    /**
     * 角色名称
     */
    @TableField(value = "role_name", condition = LIKE)
    @Length(max = 128, message = "角色名称长度不能超过128")
    private String roleName;

    /**
     * 角色编码
     */
    @TableField(value = "role_code", condition = LIKE)
    @Length(max = 128, message = "角色编码长度不能超过128")
    private String roleCode;

    /**
     * 角色类型(1内置2自定义)
     */
    @TableField(value = "role_type", condition = LIKE)
    @Length(max = 128, message = "角色类型(1内置2自定义)长度不能超过128")
    private String roleType;

    /**
     * 是否可用
     */
    @TableField(value = "useable", condition = LIKE)
    @Length(max = 1, message = "是否可用长度不能超过1")
    private String useable;




}
