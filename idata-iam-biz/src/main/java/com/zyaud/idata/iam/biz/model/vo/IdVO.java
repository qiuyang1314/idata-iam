package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class IdVO {

    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空")
    private String id;
}
