package com.zyaud.idata.iam.biz.model.vo;

import com.zyaud.fzhx.binder.annotation.BindField;
import com.zyaud.fzhx.core.model.TimeEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserCodeVO extends TimeEntity<String> {


    /**
     * 登录名
     */
    private String loginName;


    /**
     * 状态 (0正常1锁定2禁用)
     */
    private String status;


    /**
     * 用户信息
     */
    @BindField(bindTo = "userName", method = "getUserNameById", api = "userServiceImpl")
    private String userId;
    private String userName;
    /**
     * 账号类型
     */
    @BindField(bindTo = "typeName", dict = "accountType", method = "getDictData", api = "stdCodeServiceImpl")
    private String type;
    private String typeName;
    /**
     * 机构
     */
    @BindField(bindTo = "officeName", method = "getOfficeNameById", api = "officeServiceImpl")
    private String officeId;
    private String officeName;
}
