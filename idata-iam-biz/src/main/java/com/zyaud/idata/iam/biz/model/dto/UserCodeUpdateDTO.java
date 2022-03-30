package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import com.zyaud.fzhx.core.model.IdEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class UserCodeUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull(message = "id不能为空", groups = IdEntity.Update.class)
    private String id;
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
    @NotBlank(message = "请输入登录名")
    private String loginName;

    /**
     * 账号类型
     */
    @BindQuery(field = "type", comparison = Comparison.EQ)
    @Length(max = 10, message = "账号类型长度不能超过10")
    @NotBlank(message = "请选择账号类型")
    private String type;

    /**
     * 状态 (0正常1锁定2禁用)
     */
    @BindQuery(field = "status", comparison = Comparison.LIKE)
    @Length(max = 1, message = "状态 (0正常1锁定2禁用)长度不能超过1")
    @NotBlank(message = "请选择状态")
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
