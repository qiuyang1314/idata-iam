package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "AppCodeVO对象", description = "应用编码VO")
public class AppCodeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用编码", required = true)
    @NotBlank(message = "应用编码不能为空")
    private String appCode;
}
