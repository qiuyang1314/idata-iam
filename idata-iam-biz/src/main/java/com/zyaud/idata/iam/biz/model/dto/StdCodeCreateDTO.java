package com.zyaud.idata.iam.biz.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 字典编码
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StdCodeCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    @TableField(value = "code_name", condition = LIKE)
    @Length(max = 100, message = "名称长度不能超过100")
    @ApiModelProperty(value = "字典项名称", required = true)
    @NotBlank(message = "字典项名称不能为空")
    private String codeName;

    /**
     * 编码
     */
    @TableField(value = "code_value", condition = LIKE)
    @Length(max = 100, message = "编码长度不能超过100")
    @ApiModelProperty(value = "字典项编码", required = true)
    @NotBlank(message = "字典项编码不能为空")
    private String codeValue;

    /**
     * 所属字典
     */
    @TableField(value = "std_type", condition = LIKE)
    @Length(max = 64, message = "所属字典长度不能超过64")
    @ApiModelProperty(value = "所属字典", required = true)
    @NotBlank(message = "所属字典不能为空")
    private String stdType;

    /**
     * 状态 (0正常1停用)
     */
    @TableField(value = "useable", condition = LIKE)
    @Length(max = 1, message = "状态 (0正常1停用)长度不能超过1")
    @ApiModelProperty(value = "状态(0正常1停用)", required = true)
    @NotBlank(message = "状态不能为空")
    private String useable;

    /**
     * 备注
     */
    @TableField(value = "remark", condition = LIKE)
    @Length(max = 500, message = "备注长度不能超过500")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 排序
     */
    @TableField(value = "order_index", condition = LIKE)
    @Length(max = 255, message = "排序长度不能超过255")
    @ApiModelProperty(value = "排序", required = true)
    @NotNull(message = "排序不能为空")
    private String orderIndex;


}
