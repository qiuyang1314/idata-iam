package com.zyaud.idata.iam.biz.model.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="MenuCreateParamInDTO对象", description="内部业务挂载菜单参数")
public class MenuCreateParamInDTO implements Serializable {
    @ApiModelProperty(value = "父节点", required = true)
    @NotBlank(message = "父节点不能为空")
    private String parentId;

    @ApiModelProperty(value = "菜单名称", required = true)
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    @ApiModelProperty(value = "菜单类型(1目录2菜单3按钮)", required = true)
    @NotBlank(message = "菜单类型不能为空")
    private String type;

    @ApiModelProperty(value = "打开方式(0-默认；1-iframe)", required = true)
    @NotBlank(message = "打开方式不能为空")
    private String openType;

    @ApiModelProperty(value = "菜单路径(打开方式为非Iframe时必传)")
    private String path;

    @ApiModelProperty(value = "菜单地址(打开方式为非Iframe时必传)")
    private String site;

    @ApiModelProperty(value = "菜单参数(打开方式为Iframe时按需传)")
    private String menuParam;

    @ApiModelProperty(value = "菜单编码", required = true)
    @NotBlank(message = "菜单编码不能为空")
    private String menuCode;
}
