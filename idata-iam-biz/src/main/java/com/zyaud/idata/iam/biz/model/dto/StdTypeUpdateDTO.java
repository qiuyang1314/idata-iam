package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import com.zyaud.fzhx.core.model.IdEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 字典管理
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
public class StdTypeUpdateDTO implements Serializable {

    private static final long serialVersionUID=1L;
    @NotNull(message = "id不能为空", groups = IdEntity.Update.class)
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 字典编号
     */
    @BindQuery(field = "std_num", comparison = Comparison.LIKE)
    @Length(max = 100, message = "字典编号长度不能超过100")
    @ApiModelProperty(value = "字典编号")
    private String stdNum;

    /**
     * 字典名称
     */
    @BindQuery(field = "std_name", comparison = Comparison.LIKE)
    @Length(max = 100, message = "字典名称长度不能超过100")
    @ApiModelProperty(value = "字典名称")
    private String stdName;

    /**
     * 字典类型 (0内置 1自定义)
     */
    @BindQuery(field = "std_type", comparison = Comparison.LIKE)
    @Length(max = 64, message = "字典类型 (0内置 1自定义)长度不能超过64")
    @ApiModelProperty(value = "字典类型 (0内置 1自定义)")
    private String stdType;

    /**
     * 状态 (0正常1停用)
     */
    @BindQuery(field = "useable", comparison = Comparison.LIKE)
    @Length(max = 1, message = "状态 (0正常1停用)长度不能超过1")
    @ApiModelProperty(value = "状态 (0正常1停用)")
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
    @BindQuery(field = "order_index")
    @ApiModelProperty(value = "排序")
    private Integer orderIndex;




}
