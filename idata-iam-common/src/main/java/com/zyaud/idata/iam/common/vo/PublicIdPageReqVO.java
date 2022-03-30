package com.zyaud.idata.iam.common.vo;

import com.zyaud.fzhx.core.model.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "公共idVO")
public class PublicIdPageReqVO extends PageParam {

    @ApiModelProperty(value = "id")
    private String id;
}
