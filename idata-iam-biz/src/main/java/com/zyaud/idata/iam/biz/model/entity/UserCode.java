package com.zyaud.idata.iam.biz.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zyaud.fzhx.core.model.TimeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.sql.Date;

/**
 * <p>
 * 用户账号
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(UserCode.USERCODE)
public class UserCode extends TimeEntity<String> {

    private static final long serialVersionUID=1L;
    public static final String USERCODE = "iam_user_code";
    public static final String USERID = "user_id";
    public static final String LOGINNAME = "login_name";
    public static final String PASSWD = "passwd";
    public static final String SALT = "salt";
    public static final String TYPE = "type";
    public static final String STATUS = "status";
    public static final String PWDUPDATETIME = "pwd_update_time";
    public static final String DEFAULTROLES = "default_roles";


    /**
     * 用户id
     */
    @TableField(USERID)
    @Length(max = 64, message = "用户id长度不能超过64")
    private String userId;



    /**
     * 登录名
     */
    @TableField(LOGINNAME)
    @Length(max = 32, message = "登录名长度不能超过32")
    private String loginName;


    /**
     * 密码
     */
    @TableField(PASSWD)
    @Length(max = 32, message = "密码长度不能超过32")
    private String passwd;


    /**
     * 盐值
     */
    @TableField(SALT)
    @Length(max = 6, message = "盐值长度不能超过6")
    private String salt;


    /**
     * 账号类型
     */
    @TableField(TYPE)
    @Length(max = 10, message = "账号类型长度不能超过10")
    private String type;


    /**
     * 状态 (0正常1锁定2禁用)
     */
    @TableField(STATUS)
    @Length(max = 1, message = "状态 (0正常1锁定2禁用)长度不能超过1")
    private String status;


    /**
     * 密码修改时间
     */
    @TableField(PWDUPDATETIME)
    @Length(max = 64, message = "用户id长度不能超过64")
    private Date pwdUpdateTime;


    /**
     * 默认角色
     */
    @TableField(DEFAULTROLES)
    @Length(max = 3072, message = "默认角色id长度不能超过3072")
    private String defaultRoles;

}
