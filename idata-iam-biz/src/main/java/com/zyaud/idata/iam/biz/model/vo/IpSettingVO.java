package com.zyaud.idata.iam.biz.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IpSettingVO {
    private String frist;
    private String second;
    private String third;
    private String fourth;
    private String fifth;
}
