package com.zyaud.idata.iam.biz.model.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MenuCreateInDTO对象", description="内部业务挂载菜单")
public class MenuCreateInDTO implements Serializable {
    @ApiModelProperty(value = "内部业务挂载菜单参数", required = true)
    @NotEmpty(message = "内部业务挂载菜单参数不能为空")
    private List<MenuCreateParamInDTO> createParams;
}
