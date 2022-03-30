package com.zyaud.idata.iam.api.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class FindAppsRespVO implements Serializable {
    /**
     * 系统应用名字
     */
    private String appName;


    /**
     * 系统应用地址
     */
    private String url;
}
