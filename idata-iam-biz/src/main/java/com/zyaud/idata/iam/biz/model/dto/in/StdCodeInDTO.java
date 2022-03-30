package com.zyaud.idata.iam.biz.model.dto.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class StdCodeInDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String codeName;

    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    private String codeValue;

    /**
     * 所属字典
     */
    @ApiModelProperty(value = "所属字典")
    private String stdType;

    /**
     * 状态 (0正常1停用)
     */
    @ApiModelProperty(value = "状态 (0正常1停用)")
    private String useable;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private String orderIndex;
}
