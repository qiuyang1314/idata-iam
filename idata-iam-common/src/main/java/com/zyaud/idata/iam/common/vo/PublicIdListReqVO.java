package com.zyaud.idata.iam.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Author CaiFan
 * @Date 2020/8/6 12:10
 */
@Data
public class PublicIdListReqVO {
    @ApiModelProperty(value = "id")
    @NotEmpty(message = "id不能为空")
    private List<String> ids;
}
