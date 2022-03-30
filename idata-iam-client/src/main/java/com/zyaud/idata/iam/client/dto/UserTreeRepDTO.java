package com.zyaud.idata.iam.client.dto;

import com.zyaud.fzhx.common.model.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class UserTreeRepDTO extends TreeNode<String, UserTreeRepDTO> {
    /**
     * 账号名称
     */
    @ApiModelProperty(value = "账号名称")
    private String name;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户id")
    private String userId;
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;
    /**
     * office或user或role
     */
    @ApiModelProperty(value = "节点类型，机构、用户或角色,office或user或role")
    private String type;
    /**
     * 账号类型
     */
    @ApiModelProperty(value = "账号类型")
    private String userCodeType;
}
