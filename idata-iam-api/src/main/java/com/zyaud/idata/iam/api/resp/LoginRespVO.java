package com.zyaud.idata.iam.api.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class LoginRespVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 机构名
     */
    private String domainName;
    /**
     * token
     */
    private String token;
    /**
     * 过期时间
     */
    private String expirationTime;
}
