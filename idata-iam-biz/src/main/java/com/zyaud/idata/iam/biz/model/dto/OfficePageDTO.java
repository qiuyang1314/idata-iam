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
 * 机构部门
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OfficePageDTO extends PageParam {

    private static final long serialVersionUID=1L;

    /**
     * 名称
     */
    @BindQuery(field = "name", comparison = Comparison.LIKE)
    @Length(max = 255, message = "名称长度不能超过255")
    private String name;

    /**
     * 机构类型
     */
    @BindQuery(field = "otype", comparison = Comparison.LIKE)
    @Length(max = 32, message = "机构类型长度不能超过32")
    private String otype;

}
