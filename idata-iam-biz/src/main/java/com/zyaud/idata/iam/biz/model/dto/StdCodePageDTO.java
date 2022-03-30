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
 * 字典编码
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StdCodePageDTO extends PageParam {

    private static final long serialVersionUID=1L;
    /**
     * 名称
     */
    @BindQuery(field = "code_name", comparison = Comparison.LIKE)
    @Length(max = 100, message = "名称长度不能超过100")
    private String codeName;

    /**
     * 编码
     */
    @BindQuery(field = "code_value", comparison = Comparison.LIKE)
    @Length(max = 100, message = "编码长度不能超过100")
    private String codeValue;

    /**
     * 所属字典
     */
    @BindQuery(field = "std_type", comparison = Comparison.LIKE)
    @Length(max = 64, message = "所属字典长度不能超过64")
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
    @BindQuery(field = "order_index", comparison = Comparison.LIKE)
    @Length(max = 255, message = "排序长度不能超过255")
    private String orderIndex;




}
