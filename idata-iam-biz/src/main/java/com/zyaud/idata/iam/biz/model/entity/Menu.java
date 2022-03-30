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
 * 菜单
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(Menu.MENU)
public class Menu extends TimeEntity<String> {

    private static final long serialVersionUID = 1L;
    public static final String MENU = "iam_menu";
    public static final String PARENTID = "parent_id";
    public static final String PARENTIDS = "parent_ids";
    public static final String APPID = "app_id";
    public static final String NAME = "name";
    public static final String CODE = "code";
    public static final String PATH = "path";
    public static final String URLS = "urls";
    public static final String SITE = "site";
    public static final String OPENTYPE = "open_type";
    public static final String MENUPARAM = "menu_param";
    public static final String TYPE = "type";
    public static final String ORDERINDEX = "order_index";
    public static final String ICON = "icon";
    public static final String IMG = "img";
    public static final String HIDDEN = "hidden";
    public static final String PERMS = "perms";


    /**
     * 父级编号
     */
    @TableField(PARENTID)
    @Length(max = 64, message = "父级编号长度不能超过64")
    private String parentId;


    /**
     * 所有父级编号
     */
    @TableField(PARENTIDS)
    @Length(max = 128, message = "所有父级编号长度不能超过128")
    private String parentIds;


    /**
     * 系统应用id
     */
    @TableField(APPID)
    @Length(max = 64, message = "系统应用id长度不能超过64")
    private String appId;


    /**
     * 名称
     */
    @TableField(NAME)
    @Length(max = 128, message = "名称长度不能超过128")
    private String name;


    /**
     * 编码
     */
    @TableField(CODE)
    @Length(max = 64, message = "编码长度不能超过64")
    private String code;


    /**
     * 菜单路径
     */
    @TableField(PATH)
    @Length(max = 255, message = "菜单路径长度不能超过255")
    private String path;


    /**
     * 菜单链接
     */
    @TableField(URLS)
    private String urls;

    /**
     * 菜单地址
     */
    @TableField(SITE)
    private String site;

    /**
     * 打开方式
     */
    @TableField(OPENTYPE)
    private String openType;

    /**
     * 菜单参数
     */
    @TableField(MENUPARAM)
    private String menuParam;

    /**
     * 菜单类型(1目录2菜单3按钮)
     */
    @TableField(TYPE)
    @Length(max = 1, message = "菜单类型(1目录2菜单3按钮)长度不能超过1")
    private String type;


    /**
     * 排序
     */
    @TableField(ORDERINDEX)
    private Integer orderIndex;


    /**
     * 图标
     */
    @TableField(ICON)
    @Length(max = 128, message = "图标长度不能超过128")
    private String icon;


    /**
     * 图片
     */
    @TableField(IMG)
    @Length(max = 255, message = "图片长度不能超过255")
    private String img;

    /**
     * 是否显示(0显示1隐藏)
     */
    @TableField(HIDDEN)
    @Length(max = 1, message = "是否显示(0显示1隐藏)长度不能超过1")
    private String hidden;


    /**
     * 权限标识
     */
    @TableField(PERMS)
    @Length(max = 255, message = "权限标识长度不能超过255")
    private String perms;

    @Override
    public String toString() {
        return "Menu{" +
                "parentId='" + parentId + '\'' +
                ", parentIds='" + parentIds + '\'' +
                ", appId='" + appId + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", path='" + path + '\'' +
                ", urls='" + urls + '\'' +
                ", site='" + site + '\'' +
                ", openType='" + openType + '\'' +
                ", menuParam='" + menuParam + '\'' +
                ", type='" + type + '\'' +
                ", orderIndex=" + orderIndex +
                ", icon='" + icon + '\'' +
                ", img='" + img + '\'' +
                ", hidden='" + hidden + '\'' +
                ", perms='" + perms + '\'' +
                ", createTime=" + createTime +
                ", createId=" + createId +
                ", updateTime=" + updateTime +
                ", updateId=" + updateId +
                ", delFlag=" + delFlag +
                ", id=" + id +
                '}';
    }
}
