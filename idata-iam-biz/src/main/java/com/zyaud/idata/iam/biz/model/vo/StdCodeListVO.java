package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StdCodeListVO {

    @ApiModelProperty(value = "字典类型")
    private String stdType;

    @ApiModelProperty(value = "字典项值")
    private String value;
}
