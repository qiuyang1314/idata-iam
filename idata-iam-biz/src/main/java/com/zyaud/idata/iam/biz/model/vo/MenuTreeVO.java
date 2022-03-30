package com.zyaud.idata.iam.biz.model.vo;

import com.zyaud.fzhx.common.model.TreeNode;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MenuTreeVO extends TreeNode<String, MenuTreeVO> {
    /**
     * 名称
     */
    private String name;
    /**
     * 系统应用id
     */
    private String appId;
    /**
     * 菜单路径
     */
    private String path;
    /**
     * 菜单链接
     */
    private String urls;

    /**
     * 打开方式
     */
    private String openType;
    private String openTypeName;

    /**
     * 菜单参数
     */
    private String menuParam;

    /**
     * 菜单类型(1目录2菜单3按钮)
     */
    private String type;
    /**
     * 图标
     */
    private String icon;
    /**
     * 图片
     */
    private String img;

    /**
     * 是否显示(0显示1隐藏)
     */
    private String hidden;
    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单地址
     */
    private String site;
}
