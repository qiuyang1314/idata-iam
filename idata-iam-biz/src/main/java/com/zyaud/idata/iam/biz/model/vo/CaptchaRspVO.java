package com.zyaud.idata.iam.biz.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CaptchaRspVO {

    private String key;

    private String image;
}
