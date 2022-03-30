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
 * 机构部门
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(Office.OFFICE)
public class Office extends TimeEntity<String> {

    private static final long serialVersionUID=1L;
    public static final String OFFICE = "iam_office";
    public static final String PARENTID = "parent_id";
    public static final String PARENTIDS = "parent_ids";
    public static final String NAME = "name";
    public static final String CODE = "code";
    public static final String PATHS = "paths";
    public static final String ORDERINDEX = "order_index";
    public static final String APPID = "app_id";
    public static final String DISTRICTID = "district_id";
    public static final String OTYPE = "otype";
    public static final String LEVELS = "levels";
    public static final String ADDRESS = "address";
    public static final String SUPERVISOR = "supervisor";
    public static final String PHONE = "phone";
    public static final String FAX = "fax";
    public static final String EMAIL = "email";
    public static final String USEABLE = "useable";


    /**
     * 父级编号
     */
    @TableField(PARENTID)
    @Length(max = 128, message = "父级编号长度不能超过128")
    private String parentId;


    /**
     * 所有父级编号
     */
    @TableField(PARENTIDS)
    @Length(max = 1024, message = "所有父级编号长度不能超过1024")
    private String parentIds;


    /**
     * 名称
     */
    @TableField(NAME)
    @Length(max = 255, message = "名称长度不能超过255")
    private String name;

    /**
     * 机构编码
     */
    @TableField(CODE)
    @Length(max = 255, message = "名称长度不能超过255")
    private String code;

    /**
     * 全路径名称
     */
    @TableField(PATHS)
    private String paths;


    /**
     * 排序
     */
    @TableField(ORDERINDEX)
        private Integer orderIndex;


    /**
     * 系统编码
     */
    @TableField(APPID)
    @Length(max = 64, message = "系统编码长度不能超过64")
    private String appId;


    /**
     * 行政区划
     */
    @TableField(DISTRICTID)
    @Length(max = 32, message = "行政区划长度不能超过32")
    private String districtId;


    /**
     * 机构类型
     */
    @TableField(OTYPE)
    @Length(max = 32, message = "机构类型长度不能超过32")
    private String otype;


    /**
     * 机构级别
     */
    @TableField(LEVELS)
    @Length(max = 32, message = "机构级别长度不能超过32")
    private String levels;


    /**
     * 地址
     */
    @TableField(ADDRESS)
    @Length(max = 128, message = "地址长度不能超过128")
    private String address;


    /**
     * 负责人
     */
    @TableField(SUPERVISOR)
    @Length(max = 64, message = "负责人长度不能超过64")
    private String supervisor;


    /**
     * 电话
     */
    @TableField(PHONE)
    @Length(max = 64, message = "电话长度不能超过64")
    private String phone;


    /**
     * 传真
     */
    @TableField(FAX)
    @Length(max = 64, message = "传真长度不能超过64")
    private String fax;


    /**
     * 邮箱
     */
    @TableField(EMAIL)
    @Length(max = 128, message = "邮箱长度不能超过128")
    private String email;


    /**
     * 是否启用
     */
    @TableField(USEABLE)
    @Length(max = 128, message = "是否启用长度不能超过128")
    private String useable;



}
