package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import com.zyaud.fzhx.core.model.IdEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 机构部门
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
public class OfficeUpdateDTO implements Serializable {

    private static final long serialVersionUID=1L;
    @NotNull(message = "id不能为空", groups = IdEntity.Update.class)
    private String id;
    /**
     * 父级编号
     */
    @BindQuery(field = "parent_id", comparison = Comparison.LIKE)
    @Length(max = 128, message = "父级编号长度不能超过128")
    private String parentId;

    /**
     * 所有父级编号
     */
    @BindQuery(field = "parent_ids", comparison = Comparison.LIKE)
    @Length(max = 1024, message = "所有父级编号长度不能超过1024")
    private String parentIds;

    /**
     * 名称
     */
    @Length(max = 255, message = "名称长度不能超过255")
    @NotBlank(message = "请选择输入机构名称")
    private String name;

    /**
     * 机构编码
     */
    @Length(max = 255, message = "机构编码长度不能超过255")
    @NotBlank(message = "请选择输入机构编码")
    private String code;

    /**
     * 排序
     */
    @BindQuery(field = "order_index")
    private Integer orderIndex;

    /**
     * 行政区划
     */
    @BindQuery(field = "district_id", comparison = Comparison.LIKE)
    @Length(max = 32, message = "行政区划长度不能超过32")
    private String districtId;

    /**
     * 机构类型
     */
    @BindQuery(field = "otype", comparison = Comparison.LIKE)
    @Length(max = 32, message = "机构类型长度不能超过32")
    private String otype;

    /**
     * 机构级别
     */
    @BindQuery(field = "levels", comparison = Comparison.LIKE)
    @Length(max = 32, message = "机构级别长度不能超过32")
    private String levels;

    /**
     * 地址
     */
    @BindQuery(field = "address", comparison = Comparison.LIKE)
    @Length(max = 128, message = "地址长度不能超过128")
    private String address;

    /**
     * 负责人
     */
    @BindQuery(field = "supervisor", comparison = Comparison.LIKE)
    @Length(max = 64, message = "负责人长度不能超过64")
    private String supervisor;

    /**
     * 电话
     */
    @BindQuery(field = "phone", comparison = Comparison.LIKE)
    @Length(max = 64, message = "电话长度不能超过64")
    private String phone;

    /**
     * 传真
     */
    @BindQuery(field = "fax", comparison = Comparison.LIKE)
    @Length(max = 64, message = "传真长度不能超过64")
    private String fax;

    /**
     * 邮箱
     */
    @BindQuery(field = "email", comparison = Comparison.LIKE)
    @Length(max = 128, message = "邮箱长度不能超过128")
    private String email;

    /**
     * 是否启用
     */
    @BindQuery(field = "useable", comparison = Comparison.LIKE)
    @Length(max = 128, message = "是否启用长度不能超过128")
    @NotBlank(message = "请选择启用类型")
    private String useable;




}
