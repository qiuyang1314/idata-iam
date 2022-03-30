package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import com.zyaud.fzhx.core.model.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */

@Data
@Accessors(chain = true)
public class MenuPageDTO extends PageParam {

    private static final long serialVersionUID=1L;
    /**
     * 系统应用id
     */
    @BindQuery(field = "app_id", comparison = Comparison.LIKE)
    @Length(max = 64, message = "系统应用id长度不能超过64")
    private String appId;

    /**
     * 名称
     */
    @BindQuery(field = "name", comparison = Comparison.LIKE)
    @Length(max = 128, message = "名称长度不能超过128")
    private String name;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id")
    @BindQuery(field = "parentId", comparison = Comparison.LIKE)
    @Length(max = 128, message = "父级id长度不能超过128")
    private String parentId;
}
