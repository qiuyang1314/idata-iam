package com.zyaud.idata.iam.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@ApiModel(value = "公共idVO")
public class PublicIdReqVO {

    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空")
    private String id;
}
