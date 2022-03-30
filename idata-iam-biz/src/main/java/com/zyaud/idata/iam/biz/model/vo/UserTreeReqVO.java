package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserTreeReqVO {

    @ApiModelProperty(value = "应用编码")
    private List<String> appCodes;

    @ApiModelProperty(value = "指定过滤掉的用户ids")
    private List<String> userIds;
}
