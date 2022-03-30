package com.zyaud.idata.iam.biz.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 字典管理
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StdTypeCreateDTO implements Serializable {

    private static final long serialVersionUID=1L;
    /**
     * 字典编号
     */
    @TableField(value = "std_num", condition = LIKE)
    @Length(max = 100, message = "字典编号长度不能超过100")
    @ApiModelProperty(value = "字典编号")
    private String stdNum;

    /**
     * 字典名称
     */
    @TableField(value = "std_name", condition = LIKE)
    @Length(max = 100, message = "字典名称长度不能超过100")
    @ApiModelProperty(value = "字典名称")
    private String stdName;

    /**
     * 字典类型 (0内置 1自定义)
     */
    @TableField(value = "std_type", condition = LIKE)
    @Length(max = 64, message = "字典类型 (0内置 1自定义)长度不能超过64")
    @ApiModelProperty(value = "字典类型(0内置 1自定义)")
    private String stdType;

    /**
     * 状态 (0正常1停用)
     */
    @TableField(value = "useable", condition = LIKE)
    @Length(max = 1, message = "状态 (0正常1停用)长度不能超过1")
    @ApiModelProperty(value = "状态(0正常1停用)")
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
    @TableField("order_index")
    @ApiModelProperty(value = "排序")
    private Integer orderIndex;




}
