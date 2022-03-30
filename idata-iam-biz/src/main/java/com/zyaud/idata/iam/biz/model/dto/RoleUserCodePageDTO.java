package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import com.zyaud.fzhx.core.model.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 角色账号
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoleUserCodePageDTO extends PageParam {

    private static final long serialVersionUID=1L;
    /**
     * 系统id
     */
    @BindQuery(field = "app_id", comparison = Comparison.LIKE)
    @Length(max = 64, message = "系统id长度不能超过64")
    private String appId;

    /**
     * 角色编码
     */
    @BindQuery(field = "role_id", comparison = Comparison.LIKE)
    @Length(max = 64, message = "角色编码长度不能超过64")
    private String roleId;

    /**
     * 账号编码
     */
    @BindQuery(field = "user_code_id", comparison = Comparison.LIKE)
    @Length(max = 64, message = "账号编码长度不能超过64")
    private String userCodeId;




}
