package com.zyaud.idata.iam.biz.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zyaud.idata.iam.biz.model.entity.Menu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MenuCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 父级编号
     */
    @TableField(value = "parent_id", condition = LIKE)
    @Length(max = 64, message = "父级编号长度不能超过64")
    private String parentId;

    /**
     * 所有父级编号
     */
    @TableField(value = "parent_ids", condition = LIKE)
    @Length(max = 128, message = "所有父级编号长度不能超过128")
    private String parentIds;

    /**
     * 系统应用id
     */
    @TableField(value = "app_id", condition = LIKE)
    @Length(max = 64, message = "系统应用id长度不能超过64")
    private String appId;

    /**
     * 名称
     */
    @TableField(value = "name", condition = LIKE)
    @Length(max = 128, message = "名称长度不能超过128")
    private String name;

    /**
     * 菜单路径
     */
    @TableField(value = "path", condition = LIKE)
    @Length(max = 255, message = "菜单路径长度不能超过255")
    private String path;

    /**
     * 菜单链接
     */
    @TableField("urls")
    private String urls;

    /**
     * 菜单地址
     */
    @TableField("site")
    private String site;

    /**
     * 打开方式
     */
    @TableField("open_type")
    private String openType;

    /**
     * 菜单参数
     */
    @TableField(Menu.MENUPARAM)
    private String menuParam;

    /**
     * 菜单类型(1目录2菜单3按钮)
     */
    @TableField(value = "type", condition = LIKE)
    @Length(max = 1, message = "菜单类型(1目录2菜单3按钮)长度不能超过1")
    private String type;

    /**
     * 排序
     */
    @TableField("order_index")
    private Integer orderIndex;

    /**
     * 图标
     */
    @TableField(value = "icon", condition = LIKE)
    @Length(max = 128, message = "图标长度不能超过128")
    private String icon;

    /**
     * 图片
     */
    @TableField(value = "img", condition = LIKE)
    @Length(max = 255, message = "图片长度不能超过255")
    private String img;

    /**
     * 是否显示(0显示1隐藏)
     */
    @TableField(value = "hidden", condition = LIKE)
    @Length(max = 1, message = "是否显示(0显示1隐藏)长度不能超过1")
    private String hidden;

    /**
     * 权限标识
     */
    @TableField(value = "perms", condition = LIKE)
    @Length(max = 255, message = "权限标识长度不能超过255")
    private String perms;


}
