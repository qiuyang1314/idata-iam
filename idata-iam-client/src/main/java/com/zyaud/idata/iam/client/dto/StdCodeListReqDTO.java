package com.zyaud.idata.iam.client.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典接口间调用 请求对象
 *
 * @author shine
 * @date 2022/1/26 17:06
 */
@Data
public class StdCodeListReqDTO implements Serializable {
    private static final long serialVersionUID = -4935605312802688675L;
    @ApiModelProperty(value = "字典类型")
    private String stdType;

    @ApiModelProperty(value = "字典项值")
    private String value;
}
