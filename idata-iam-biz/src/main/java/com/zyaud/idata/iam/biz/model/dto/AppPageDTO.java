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
 * 系统应用
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */

@Data
@Accessors(chain = true)
public class AppPageDTO extends PageParam {

    private static final long serialVersionUID = 1L;
    /**
     * 系统应用名字
     */
    @BindQuery(field = "app_name", comparison = Comparison.LIKE)
    @Length(max = 64, message = "系统应用名字长度不能超过64")
    @ApiModelProperty(value = "系统应用名字")
    private String appName;

    /**
     * 是否生效
     */
    @BindQuery(field = "useable", comparison = Comparison.LIKE)
    @Length(max = 1, message = "是否生效长度不能超过1")
    @ApiModelProperty(value = "是否生效")
    private String useable;
}
