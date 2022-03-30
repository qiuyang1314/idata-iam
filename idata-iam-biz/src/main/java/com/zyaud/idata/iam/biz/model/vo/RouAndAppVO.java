package com.zyaud.idata.iam.biz.model.vo;

import com.zyaud.idata.iam.biz.model.entity.RoleUserCode;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RouAndAppVO extends RoleUserCode {

    private String appName;

    private String roleName;

}
