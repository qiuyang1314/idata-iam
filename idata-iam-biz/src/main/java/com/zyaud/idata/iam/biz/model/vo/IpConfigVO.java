package com.zyaud.idata.iam.biz.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
public class IpConfigVO {

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

    private List<String> ips;

    private Set<IpSettingVO> ipconfig;

}
