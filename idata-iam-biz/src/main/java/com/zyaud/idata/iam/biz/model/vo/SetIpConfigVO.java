package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Accessors(chain = true)
public class SetIpConfigVO {
    @NotBlank(message = "应用id不能为空")
    @ApiModelProperty(value = "应用id", required = true)
    private String appId;

    @ApiModelProperty(value = "ip设置")
    private Set<IpSettingVO> ipconfig;
}
