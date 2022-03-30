package com.zyaud.idata.iam.biz.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IpSettingIdxVO extends IpSettingVO{
    private Integer idx;
}
