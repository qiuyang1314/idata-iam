package com.zyaud.idata.iam.biz.model.vo;

import com.zyaud.fzhx.common.model.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserTreeVO extends TreeNode<String, UserTreeVO> {
    // TreeNo的id即为账号ID
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
    @ApiModelProperty(value = "节点类型，机构、用户或角色")
    private String type;
    /**
     * 账号类型
     */
    @ApiModelProperty(value = "账号类型")
    private String userCodeType;
}
