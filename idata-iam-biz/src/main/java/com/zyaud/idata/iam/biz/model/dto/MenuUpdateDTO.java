package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import com.zyaud.fzhx.core.model.IdEntity;
import com.zyaud.idata.iam.biz.model.entity.Menu;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 菜单
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
public class MenuUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull(message = "id不能为空", groups = IdEntity.Update.class)
    private String id;
    /**
     * 父级编号
     */
    @BindQuery(field = "parent_id", comparison = Comparison.LIKE)
    @Length(max = 64, message = "父级编号长度不能超过64")
    private String parentId;

    /**
     * 所有父级编号
     */
    @BindQuery(field = "parent_ids", comparison = Comparison.LIKE)
    @Length(max = 128, message = "所有父级编号长度不能超过128")
    private String parentIds;

    /**
     * 系统应用id
     */
    @BindQuery(field = "app_id", comparison = Comparison.LIKE)
    @Length(max = 64, message = "系统应用id长度不能超过64")
    private String appId;

    /**
     * 名称
     */
    @BindQuery(field = "name", comparison = Comparison.LIKE)
    @Length(max = 128, message = "名称长度不能超过128")
    private String name;

    /**
     * 编码
     */
    @BindQuery(field = "code")
    @Length(max = 64, message = "编码长度不能超过64")
    private String code;

    /**
     * 菜单路径
     */
    @BindQuery(field = "path", comparison = Comparison.LIKE)
    @Length(max = 255, message = "菜单路径长度不能超过255")
    private String path;

    /**
     * 菜单链接
     */
    @BindQuery(field = "urls")
    private String urls;

    /**
     * 菜单地址
     */
    @BindQuery(field = "site")
    private String site;

    /**
     * 打开方式
     */
    @BindQuery(field = "open_type")
    private String openType;

    /**
     * 菜单参数
     */
    @BindQuery(field = Menu.MENUPARAM)
    private String menuParam;

    /**
     * 菜单类型(1目录2菜单3按钮)
     */
    @BindQuery(field = "type", comparison = Comparison.LIKE)
    @Length(max = 1, message = "菜单类型(1目录2菜单3按钮)长度不能超过1")
    private String type;

    /**
     * 排序
     */
    @BindQuery(field = "order_index")
    private Integer orderIndex;

    /**
     * 图标
     */
    @BindQuery(field = "icon", comparison = Comparison.LIKE)
    @Length(max = 128, message = "图标长度不能超过128")
    private String icon;

    /**
     * 图片
     */
    @BindQuery(field = "img", comparison = Comparison.LIKE)
    @Length(max = 255, message = "图片长度不能超过255")
    private String img;

    /**
     * 是否显示(0显示1隐藏)
     */
    @BindQuery(field = "hidden", comparison = Comparison.LIKE)
    @Length(max = 1, message = "是否显示(0显示1隐藏)长度不能超过1")
    private String hidden;

    /**
     * 权限标识
     */
    @BindQuery(field = "perms", comparison = Comparison.LIKE)
    @Length(max = 255, message = "权限标识长度不能超过255")
    private String perms;


}
