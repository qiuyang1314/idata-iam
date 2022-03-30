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
 *
 * </p>
 *
 * @author fzhx-aicode
 * @since 2021-02-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(Config.CONFIG)
public class Config extends TimeEntity<String> {

    private static final long serialVersionUID = 1L;
    public static final String CONFIG = "iam_config";
    public static final String CTYPE = "ctype";
    public static final String CCODE = "ccode";
    public static final String CITEM = "citem";
    public static final String CVALUE = "cvalue";
    public static final String ORDERINDEX = "order_index";


    /**
     * 配置类型（0：系统配置）
     */
    @TableField(CTYPE)
    @Length(max = 10, message = "配置类型（0：系统配置）长度不能超过10")
    private String ctype;


    /**
     * 配置项编码（0：密码过期时间，1：ip配置,2:系统布局;3:系统配置及安扫配置;4:首页登录配置）
     */
    @TableField(CCODE)
    @Length(max = 255, message = "配置项编码（0：密码过期时间，1：ip配置）长度不能超过255")
    private String ccode;


    /**
     * 配置项（配置中文名称）
     */
    @TableField(CITEM)
    @Length(max = 255, message = "配置项（配置中文名称）长度不能超过255")
    private String citem;


    /**
     * 配置项值
     */
    @TableField(CVALUE)
    private String cvalue;


    /**
     * 排序
     */
    @TableField(ORDERINDEX)
    @Length(max = 255, message = "排序长度不能超过255")
    private Integer orderIndex;

    public static final String SYS_SCAN_CONFIG_CACHE_KEY = "SYS_SCAN_CONFIG";

}
