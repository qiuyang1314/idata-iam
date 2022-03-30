package com.zyaud.idata.iam.biz.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 角色账号
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class RoleUserCodeUpdateDTO implements Serializable {

    private static final long serialVersionUID=1L;
    /**
     * 系统id
     */
    @TableField(value = "app_id", condition = LIKE)
    @NotBlank(message = "请选择应用")
    @Length(max = 64, message = "系统id长度不能超过64")
    private String appId;

    /**
     * 角色编码
     */
    @TableField(value = "role_id", condition = LIKE)
    @NotBlank(message = "请选择角色")
    @Length(max = 64, message = "角色编码长度不能超过64")
    private String roleId;

    /**
     * 账号编码
     */
    @TableField(value = "user_code_id", condition = LIKE)
    @NotBlank(message = "请选择账号")
    @Length(max = 64, message = "账号编码长度不能超过64")
    private String userCodeId;




}
