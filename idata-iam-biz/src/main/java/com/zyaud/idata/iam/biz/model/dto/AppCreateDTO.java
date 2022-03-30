package com.zyaud.idata.iam.biz.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 系统应用
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AppCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 系统应用名字
     */
    @TableField(value = "app_name", condition = LIKE)
    @Length(max = 64, message = "系统应用名字长度不能超过64")
    @NotBlank(message = "系统应用名字不能为空")
    @ApiModelProperty(value = "系统应用名字")
    private String appName;
    /**
     * 系统应用地址
     */
    @TableField(value = "url", condition = LIKE)
    @Length(max = 64, message = "系统应用地址长度不能超过64")
    @NotBlank(message = "系统应用地址不能为空")
    @ApiModelProperty(value = "系统应用地址")
    private String url;

    /**
     * 应用图片
     */
    @TableField(value="img",condition = LIKE)
    @Length(max = 64, message = "应用图片长度不能超过64")
//    @NotBlank(message = "应用图片不能为空")
    @ApiModelProperty(value = "应用图片")
    private String img;

    /**
     * 排序
     */
    @TableField("order_index")
    @ApiModelProperty(value = "排序")
    private Integer orderIndex;


    /**
     * 是否生效
     */
    @TableField(value = "useable", condition = LIKE)
    @Length(max = 1, message = "是否生效长度不能超过1")
    @NotBlank(message = "是否生效不能为空")
    @ApiModelProperty(value = "是否生效")
    private String useable;

    /**
     * 打开方式
     */
    @TableField("open_type")
    @Length(max = 10, message = "是否生效长度不能超过10")
    @NotBlank(message = "打开方式不能为空")
    @ApiModelProperty(value = "打开方式")
    private String openType;

    /**
     * 应用编码
     */
    @TableField("app_code")
    @Length(max = 20, message = "应用编码长度不能超过20")
    @NotBlank(message = "应用编码不能为空")
    @ApiModelProperty(value = "应用编码")
    private String appCode;


}
