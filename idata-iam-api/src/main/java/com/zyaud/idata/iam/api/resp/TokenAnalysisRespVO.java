package com.zyaud.idata.iam.api.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
public class TokenAnalysisRespVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime expirationTime;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户性别
     */
    private String userSex;

    /**
     * 机构名称
     */
    private String officeName;

    /**
     * 机构编码
     */
    private String officeCode;
}
