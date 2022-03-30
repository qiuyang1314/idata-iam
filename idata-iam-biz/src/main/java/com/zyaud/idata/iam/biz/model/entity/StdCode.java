package com.zyaud.idata.iam.biz.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zyaud.fzhx.core.model.TimeEntity;
import io.swagger.annotations.ApiModelProperty;
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
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(StdCode.STDCODE)
public class StdCode extends TimeEntity<String> {

    private static final long serialVersionUID=1L;
    public static final String STDCODE = "iam_std_code";
    public static final String CODENAME = "code_name";
    public static final String CODEVALUE = "code_value";
    public static final String STDTYPE = "std_type";
    public static final String USEABLE = "useable";
    public static final String REMARK = "remark";
    public static final String ORDERINDEX = "order_index";


    /**
     * 名称
     */
    @TableField(CODENAME)
    @Length(max = 100, message = "名称长度不能超过100")
    @ApiModelProperty(value = "字典项名称")
    private String codeName;


    /**
     * 编码
     */
    @TableField(CODEVALUE)
    @Length(max = 100, message = "编码长度不能超过100")
    @ApiModelProperty(value = "字典项编码")
    private String codeValue;


    /**
     * 所属字典
     */
    @TableField(STDTYPE)
    @Length(max = 64, message = "所属字典长度不能超过64")
    @ApiModelProperty(value = "所属字典")
    private String stdType;


    /**
     * 状态 (0正常1停用)
     */
    @TableField(USEABLE)
    @Length(max = 1, message = "状态 (0正常1停用)长度不能超过1")
    @ApiModelProperty(value = "状态 (0正常1停用)")
    private String useable;


    /**
     * 备注
     */
    @TableField(REMARK)
    @Length(max = 500, message = "备注长度不能超过500")
    @ApiModelProperty(value = "备注")
    private String remark;


    /**
     * 排序
     */
    @TableField(ORDERINDEX)
    @Length(max = 255, message = "排序长度不能超过255")
    @ApiModelProperty(value = "排序")
    private String orderIndex;



}
