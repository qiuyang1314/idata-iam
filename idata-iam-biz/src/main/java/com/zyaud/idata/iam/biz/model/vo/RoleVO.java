package com.zyaud.idata.iam.biz.model.vo;

import com.zyaud.fzhx.binder.annotation.BindField;
import com.zyaud.idata.iam.biz.model.entity.Role;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RoleVO extends Role {
    @BindField(bindTo = "appName", method = "getAppNameById", api = "appServiceImpl")
    private String appId;
    private String appName;

    private boolean isuseable;
}
