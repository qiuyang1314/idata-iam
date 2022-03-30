package com.zyaud.idata.iam.biz.model.dto.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 岳艺
 * @description 用户类型 请求参数实体
 * @date 2022/1/8  16:28:37
 **/
@Data
@Accessors(chain = true)
public class OfficeTypeInDTO {

    @ApiModelProperty("机构类型")
    private String orgType;
}
