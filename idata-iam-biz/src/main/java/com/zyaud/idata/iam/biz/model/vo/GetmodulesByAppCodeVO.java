package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class GetmodulesByAppCodeVO {
    @NotBlank(message = "应用编码不能为空")
    @ApiModelProperty(value = "应用编码")
    private String appCode;
}
