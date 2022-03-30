package com.zyaud.idata.iam.biz.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.zyaud.idata.iam.biz.model.entity.Config.*;

/**
 * <p>
 *
 * </p>
 *
 * @author fzhx-aicode
 * @since 2021-02-08
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ConfigCreateDTO implements Serializable {

    private static final long serialVersionUID=1L;
    /**
     * 配置类型（0：系统配置）
     */
    @TableField(value = CTYPE, condition = LIKE)
    @Length(max = 10, message = "配置类型（0：系统配置）长度不能超过10")
    private String ctype;

    /**
     * 配置项编码（0：密码过期时间，1：ip配置,2:系统布局;3:系统配置及安扫配置）
     */
    @TableField(value = CCODE, condition = LIKE)
    @Length(max = 255, message = "配置项编码（0：密码过期时间，1：ip配置）长度不能超过255")
    private String ccode;

    /**
     * 配置项（配置中文名称）
     */
    @TableField(value = CITEM, condition = LIKE)
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
    @TableField(value = ORDERINDEX, condition = LIKE)
    @Length(max = 255, message = "排序长度不能超过255")
    private Integer orderIndex;




}
