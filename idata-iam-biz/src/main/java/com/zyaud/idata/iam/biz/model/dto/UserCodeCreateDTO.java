package com.zyaud.idata.iam.biz.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Date;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.zyaud.idata.iam.biz.model.entity.UserCode.DEFAULTROLES;

/**
 * <p>
 * 用户账号
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserCodeCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @TableField(value = "user_id", condition = LIKE)
    @Length(max = 64, message = "用户id长度不能超过64")
    private String userId;

    /**
     * 登录名
     */
    @TableField(value = "login_name", condition = LIKE)
    @Length(max = 32, message = "登录名长度不能超过32")
    @NotBlank(message = "请输入登录名")
    private String loginName;

    /**
     * 密码
     */
    @TableField(value = "passwd", condition = LIKE)
    @Length(max = 32, message = "密码长度不能超过32")
    @NotBlank(message = "请输入密码")
    private String passwd;

    /**
     * 盐值
     */
    @TableField(value = "salt", condition = LIKE)
    @Length(max = 6, message = "盐值长度不能超过6")
    private String salt;

    /**
     * 账号类型
     */
    @TableField(value = "type", condition = EQUAL)
    @Length(max = 10, message = "账号类型长度不能超过10")
    @NotBlank(message = "请选择账户类型")
    private String type;

    /**
     * 状态 (0正常1锁定2禁用)
     */
    @TableField(value = "status", condition = LIKE)
    @Length(max = 1, message = "状态 (0正常1锁定2禁用)长度不能超过1")
    @NotBlank(message = "请选择账户状态")
    private String status;

    /**
     * 密码修改时间
     */
    @TableField(value = "pwd_update_time", condition = LIKE)
    private Date pwdUpdateTime;


    /**
     * 默认角色
     */
    @TableField(DEFAULTROLES)
    @Length(max = 3072, message = "默认角色id长度不能超过3072")
    private String defaultRoles;
}
