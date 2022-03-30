package com.zyaud.idata.iam.biz.model.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MenuDelInDTO对象", description="内部业务删除菜单")
public class MenuDelInDTO implements Serializable {
    @ApiModelProperty(value = "菜单ID", required = true)
    @NotNull(message = "菜单ID不能为空")
    private List<String> menuIds;
}
