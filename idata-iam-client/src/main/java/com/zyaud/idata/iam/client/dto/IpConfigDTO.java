package com.zyaud.idata.iam.client.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class IpConfigDTO {

    /**
     * 应用id
     */
    private String appId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * ip设置
     */
    private List<String> ips;

}
