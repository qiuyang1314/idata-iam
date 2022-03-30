package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import com.zyaud.fzhx.core.model.IdEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 字典编码
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class StdCodeUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull(message = "id不能为空", groups = IdEntity.Update.class)
    @ApiModelProperty(value = "id", required = true)
    private String id;
    /**
     * 名称
     */
    @BindQuery(field = "code_name", comparison = Comparison.LIKE)
    @Length(max = 100, message = "名称长度不能超过100")
    @NotBlank(message = "字典项名称不能为空")
    @ApiModelProperty(value = "字典项名称", required = true)
    private String codeName;

    /**
     * 编码
     */
    @BindQuery(field = "code_value", comparison = Comparison.LIKE)
    @Length(max = 100, message = "编码长度不能超过100")
    @NotBlank(message = "字典项编码不能为空")
    @ApiModelProperty(value = "字典项编码", required = true)
    private String codeValue;

    /**
     * 所属字典
     */
    @BindQuery(field = "std_type", comparison = Comparison.LIKE)
    @Length(max = 64, message = "所属字典长度不能超过64")
    @NotBlank(message = "所属字典不能为空")
    @ApiModelProperty(value = "所属字典", required = true)
    private String stdType;

    /**
     * 状态 (0正常1停用)
     */
    @BindQuery(field = "useable", comparison = Comparison.LIKE)
    @Length(max = 1, message = "状态 (0正常1停用)长度不能超过1")
    @NotBlank(message = "状态不能为空")
    @ApiModelProperty(value = "状态(0正常1停用)", required = true)
    private String useable;

    /**
     * 备注
     */
    @BindQuery(field = "remark", comparison = Comparison.LIKE)
    @Length(max = 500, message = "备注长度不能超过500")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 排序
     */
    @BindQuery(field = "order_index", comparison = Comparison.LIKE)
    @Length(max = 255, message = "排序长度不能超过255")
    @ApiModelProperty(value = "排序", required = true)
    private String orderIndex;


}
