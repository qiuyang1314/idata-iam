package com.zyaud.idata.iam.biz.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserCodeAASVO {
    /**
     * 账号id
     */
    private String id;

    /**
     * 账号名称
     */
    private String name;

    /**
     * 账号对应userId
     */
    private String userId;

    /**
     * 账号对应用户所属机构信息
     */
    private String userName;
    private String officeId;

    /**
     * 账号类型
     */
    private String userCodeType;
}
