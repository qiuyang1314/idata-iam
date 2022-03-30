package com.zyaud.idata.iam.biz.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zyaud.fzhx.core.model.TimeEntity;
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
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(StdType.IAMSTDTYPE)
public class StdType extends TimeEntity<String> {

    private static final long serialVersionUID=1L;
    public static final String IAMSTDTYPE = "iam_std_type";
    public static final String STDNUM = "std_num";
    public static final String STDNAME = "std_name";
    public static final String STDTYPE = "std_type";
    public static final String USEABLE = "useable";
    public static final String REMARK = "remark";
    public static final String ORDERINDEX = "order_index";


    /**
     * 字典编号
     */
    @TableField(STDNUM)
    @Length(max = 100, message = "字典编号长度不能超过100")
    private String stdNum;


    /**
     * 字典名称
     */
    @TableField(STDNAME)
    @Length(max = 100, message = "字典名称长度不能超过100")
    private String stdName;


    /**
     * 字典类型 (0内置 1自定义)
     */
    @TableField(STDTYPE)
    @Length(max = 64, message = "字典类型 (0内置 1自定义)长度不能超过64")
    private String stdType;


    /**
     * 状态 (0正常1停用)
     */
    @TableField(USEABLE)
    @Length(max = 1, message = "状态 (0正常1停用)长度不能超过1")
    private String useable;


    /**
     * 备注
     */
    @TableField(REMARK)
    @Length(max = 500, message = "备注长度不能超过500")
    private String remark;


    /**
     * 排序
     */
    @TableField(ORDERINDEX)
        private Integer orderIndex;



}
