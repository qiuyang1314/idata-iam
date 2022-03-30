package com.zyaud.idata.iam.client.dto;

import com.zyaud.fzhx.core.model.TimeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 用户账号
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */

@Data
@Accessors(chain = true)
@ApiModel(value = "内部系统用户账号返回类")
public class UserCodeInDTO extends TimeEntity<String> {
    private static final long serialVersionUID=1L;
    /**
     * 用户id
     */
    @Length(max = 64, message = "用户id长度不能超过64")
    @ApiModelProperty(value = "用户id")
    private String userId;


    /**
     * 登录名
     */
    @Length(max = 32, message = "登录名长度不能超过32")
    @ApiModelProperty(value = "登录名")
    private String loginName;

    /**
     * 密码
     */
    @Length(max = 32, message = "密码长度不能超过32")
    @ApiModelProperty(value = "密码")
    private String passwd;


    /**
     * 盐值
     */
    @Length(max = 6, message = "盐值长度不能超过6")
    @ApiModelProperty(value = "盐值")
    private String salt;


    /**
     * 账号类型
     */
    @Length(max = 10, message = "账号类型长度不能超过10")
    @ApiModelProperty(value = "账号类型")
    private String type;


    /**
     * 状态 (0正常1锁定2禁用)
     */
    @Length(max = 1, message = "状态 (0正常1锁定2禁用)长度不能超过1")
    @ApiModelProperty(value = "状态 (0正常1锁定2禁用)")
    private String status;

    /**
     * 默认角色
     */
    @ApiModelProperty(value = "默认角色")
    private String defaultRoles;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;

}
