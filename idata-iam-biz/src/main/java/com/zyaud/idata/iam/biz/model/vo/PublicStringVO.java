package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PublicStringVO {

    @ApiModelProperty(value = "公用字符串入参", required = true)
    private String string;
}
