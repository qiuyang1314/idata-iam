package com.zyaud.idata.iam.biz.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * <p>
 * 角色账号
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */

@Data
@Accessors(chain = true)
@TableName(RoleUserCode.ROLEUSERCODE)
public class RoleUserCode implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String ROLEUSERCODE = "iam_role_user_code";
    public static final String APPID = "app_id";
    public static final String ROLEID = "role_id";
    public static final String USERCODEID = "user_code_id";


    /**
     * 系统id
     */
    @TableField(APPID)
    @Length(max = 64, message = "系统id长度不能超过64")
    private String appId;


    /**
     * 角色编码
     */
    @TableField(ROLEID)
    @Length(max = 64, message = "角色编码长度不能超过64")
    private String roleId;


    /**
     * 账号编码
     */
    @TableField(USERCODEID)
    @Length(max = 64, message = "账号编码长度不能超过64")
    private String userCodeId;


}
