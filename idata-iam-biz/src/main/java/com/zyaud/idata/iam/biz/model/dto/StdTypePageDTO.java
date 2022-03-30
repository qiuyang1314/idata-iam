package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import com.zyaud.fzhx.core.model.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

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
public class StdTypePageDTO extends PageParam {

    private static final long serialVersionUID=1L;
    /**
     * 字典编号
     */
    @BindQuery(field = "std_num", comparison = Comparison.LIKE)
    @Length(max = 100, message = "字典编号长度不能超过100")
    private String stdNum;

    /**
     * 字典名称
     */
    @BindQuery(field = "std_name", comparison = Comparison.LIKE)
    @Length(max = 100, message = "字典名称长度不能超过100")
    private String stdName;

    /**
     * 字典类型 (0内置 1自定义)
     */
    @BindQuery(field = "std_type", comparison = Comparison.LIKE)
    @Length(max = 64, message = "字典类型 (0内置 1自定义)长度不能超过64")
    private String stdType;

    /**
     * 状态 (0正常1停用)
     */
    @BindQuery(field = "useable", comparison = Comparison.LIKE)
    @Length(max = 1, message = "状态 (0正常1停用)长度不能超过1")
    private String useable;

    /**
     * 备注
     */
    @BindQuery(field = "remark", comparison = Comparison.LIKE)
    @Length(max = 500, message = "备注长度不能超过500")
    private String remark;

    /**
     * 排序
     */
    @BindQuery(field = "order_index")
    private Integer orderIndex;




}
