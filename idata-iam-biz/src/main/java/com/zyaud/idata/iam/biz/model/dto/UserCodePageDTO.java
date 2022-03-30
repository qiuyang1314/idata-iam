package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import com.zyaud.fzhx.core.model.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.sql.Date;


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
public class UserCodePageDTO extends PageParam {

    private static final long serialVersionUID=1L;
    /**
     * 用户id
     */
    @BindQuery(field = "user_id", comparison = Comparison.LIKE)
    @Length(max = 64, message = "用户id长度不能超过64")
    private String userId;

    /**
     * 登录名
     */
    @BindQuery(field = "login_name", comparison = Comparison.LIKE)
    @Length(max = 32, message = "登录名长度不能超过32")
    private String loginName;

    /**
     * 密码
     */
    @BindQuery(field = "passwd", comparison = Comparison.LIKE)
    @Length(max = 32, message = "密码长度不能超过32")
    private String passwd;

    /**
     * 盐值
     */
    @BindQuery(field = "salt", comparison = Comparison.LIKE)
    @Length(max = 6, message = "盐值长度不能超过6")
    private String salt;

    /**
     * 账号类型
     */
    @BindQuery(field = "type", comparison = Comparison.EQ)
    @Length(max = 10, message = "账号类型长度不能超过10")
    private String type;


    /**
     * 状态 (0正常1锁定2禁用)
     */
    @BindQuery(field = "status", comparison = Comparison.LIKE)
    @Length(max = 1, message = "状态 (0正常1锁定2禁用)长度不能超过1")
    private String status;


    /**
     * 密码修改时间
     */
    @BindQuery(field = "pwd_update_time", comparison = Comparison.LIKE)
    private Date pwdUpdateTime;


    /**
     * 默认角色
     */
    @BindQuery(field = DEFAULTROLES)
    @Length(max = 3072, message = "默认角色id长度不能超过3072")
    private String defaultRoles;

}
