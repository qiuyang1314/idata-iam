package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class RoleUserCodeDeleteDTO  implements Serializable {
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
