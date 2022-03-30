package com.zyaud.idata.iam.biz.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

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
public class OfficeCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 父级编号
     */
    @TableField(value = "parent_id", condition = LIKE)
    @Length(max = 128, message = "父级编号长度不能超过128")
    private String parentId;

    /**
     * 所有父级编号
     */
    @TableField(value = "parent_ids", condition = LIKE)
    @Length(max = 1024, message = "所有父级编号长度不能超过1024")
    private String parentIds;

    /**
     * 名称
     */
    @TableField(value = "name", condition = LIKE)
    @Length(max = 255, message = "名称长度不能超过255")
    @NotBlank(message = "请选择输入机构名称")
    private String name;

    /**
     * 机构编码
     */
    @TableField(value = "code", condition = LIKE)
    @Length(max = 255, message = "机构编码长度不能超过255")
    @NotBlank(message = "请选择输入机构编码")
    private String code;

    /**
     * 排序
     */
    @TableField("order_index")
    private Integer orderIndex;

    /**
     * 行政区划
     */
    @TableField(value = "district_id", condition = LIKE)
    @Length(max = 32, message = "行政区划长度不能超过32")
    private String districtId;

    /**
     * 机构类型
     */
    @TableField(value = "otype", condition = LIKE)
    @Length(max = 32, message = "机构类型长度不能超过32")
    private String otype;

    /**
     * 机构级别
     */
    @TableField(value = "levels", condition = LIKE)
    @Length(max = 32, message = "机构级别长度不能超过32")
    private String levels;

    /**
     * 地址
     */
    @TableField(value = "address", condition = LIKE)
    @Length(max = 128, message = "地址长度不能超过128")
    private String address;

    /**
     * 负责人
     */
    @TableField(value = "supervisor", condition = LIKE)
    @Length(max = 64, message = "负责人长度不能超过64")
    private String supervisor;

    /**
     * 电话
     */
    @TableField(value = "phone", condition = LIKE)
    @Length(max = 64, message = "电话长度不能超过64")
    private String phone;

    /**
     * 传真
     */
    @TableField(value = "fax", condition = LIKE)
    @Length(max = 64, message = "传真长度不能超过64")
    private String fax;

    /**
     * 邮箱
     */
    @TableField(value = "email", condition = LIKE)
    @Length(max = 128, message = "邮箱长度不能超过128")
    private String email;

    /**
     * 是否启用
     */
    @TableField(value = "useable", condition = LIKE)
    @Length(max = 128, message = "是否启用长度不能超过128")
    @NotBlank(message = "请选择启用类型")
    private String useable;


}
