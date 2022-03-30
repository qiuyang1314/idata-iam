package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import com.zyaud.fzhx.core.model.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

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
public class ConfigPageDTO extends PageParam {

    private static final long serialVersionUID=1L;
    /**
     * 配置类型（0：系统配置）
     */
    @BindQuery(field = CTYPE, comparison = Comparison.LIKE)
    @Length(max = 10, message = "配置类型（0：系统配置）长度不能超过10")
    private String ctype;

    /**
     * 配置项编码（0：密码过期时间，1：ip配置,2:系统布局;3:系统配置及安扫配置）
     */
    @BindQuery(field = CCODE, comparison = Comparison.LIKE)
    @Length(max = 255, message = "配置项编码（0：密码过期时间，1：ip配置）长度不能超过255")
    private String ccode;

    /**
     * 配置项（配置中文名称）
     */
    @BindQuery(field = CITEM, comparison = Comparison.LIKE)
    @Length(max = 255, message = "配置项（配置中文名称）长度不能超过255")
    private String citem;

    /**
     * 配置项值
     */
    @BindQuery(field = CVALUE)
    private String cvalue;

    /**
     * 排序
     */
    @BindQuery(field = ORDERINDEX, comparison = Comparison.LIKE)
    @Length(max = 255, message = "排序长度不能超过255")
    private Integer orderIndex;




}
