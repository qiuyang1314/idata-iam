package com.zyaud.idata.iam.client.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 添加类描述
 *
 * @author shine
 * @date 2022/1/26 17:05
 */
@Data
@ApiModel("字典树返回DTO")
public class StdCodeTreeRepDTO {
    @ApiModelProperty(value = "目录名称")
    private String catalogName;
}
