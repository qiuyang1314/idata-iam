package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import com.zyaud.fzhx.core.model.IdEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class ConfigUpdateDTO implements Serializable {

    private static final long serialVersionUID=1L;
    @NotNull(message = "id不能为空", groups = IdEntity.Update.class)
    private String id;
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
