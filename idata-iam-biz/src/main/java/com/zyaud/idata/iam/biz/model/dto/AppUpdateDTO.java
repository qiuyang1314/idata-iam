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
 * 系统应用
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class AppUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull(message = "id不能为空", groups = IdEntity.Update.class)
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 系统应用名字
     */
    @BindQuery(field = "app_name", comparison = Comparison.LIKE)
    @Length(max = 64, message = "系统应用名字长度不能超过64")
    @NotBlank(message = "系统应用名字不能为空")
    @ApiModelProperty(value = "系统应用名字")
    private String appName;

    /**
     * 系统应用地址
     */
    @BindQuery(field = "url", comparison = Comparison.LIKE)
    @Length(max = 64, message = "系统应用地址长度不能超过64")
    @NotBlank(message = "系统应用地址不能为空")
    @ApiModelProperty(value = "系统应用地址")
    private String url;
    /**
     * 应用图片
     */
    @BindQuery(field="img",comparison = Comparison.LIKE)
    @Length(max = 64, message = "应用图片长度不能超过64")
//    @NotBlank(message = "应用图片不能为空")
    @ApiModelProperty(value = "应用图片")
    private String img;
    /**
     * 排序
     */
    @BindQuery(field = "order_index")
    @ApiModelProperty(value = "排序")
    private Integer orderIndex;

    /**
     * 是否生效
     */
    @BindQuery(field = "useable", comparison = Comparison.LIKE)
    @Length(max = 1, message = "是否生效长度不能超过1")
    @NotBlank(message = "是否生效不能为空")
    @ApiModelProperty(value = "是否生效")
    private String useable;

    /**
     * 打开方式
     */
    @BindQuery(field = "open_type")
    @Length(max = 10, message = "是否生效长度不能超过10")
    @NotBlank(message = "打开方式不能为空")
    @ApiModelProperty(value = "打开方式")
    private String openType;

    /**
     * 应用编码
     */
    @BindQuery(field = "app_code")
    @Length(max = 20, message = "应用编码长度不能超过20")
    @NotBlank(message = "应用编码不能为空")
    @ApiModelProperty(value = "应用编码")
    private String appCode;
}
