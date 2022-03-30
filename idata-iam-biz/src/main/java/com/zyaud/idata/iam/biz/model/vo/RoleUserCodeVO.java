package com.zyaud.idata.iam.biz.model.vo;

import com.zyaud.fzhx.binder.annotation.BindField;
import com.zyaud.idata.iam.biz.model.entity.RoleUserCode;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RoleUserCodeVO extends RoleUserCode {
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 用户名
     */
    @BindField(bindTo = "userName", method = "getUserNameById", api = "userServiceImpl")
    private String userId;
    private String userName;

}
