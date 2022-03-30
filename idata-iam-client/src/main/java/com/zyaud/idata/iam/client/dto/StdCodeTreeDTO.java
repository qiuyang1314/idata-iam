package com.zyaud.idata.iam.client.dto;

import com.zyaud.fzhx.common.model.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : dong
 * @ClassName: StdCodeTreeVO
 * @date : 2021-12-19 17:14
 * @Description :
 * @Version :
 **/
@Data
@ApiModel("字典树返回VO")
public class StdCodeTreeDTO extends TreeNode<String, StdCodeTreeDTO>{

    @ApiModelProperty(value = "目录名称")
    private String name;


}
