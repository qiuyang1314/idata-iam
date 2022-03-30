package com.zyaud.idata.iam.client.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : dong
 * @ClassName: DataSourceQueryReqDTO
 * @date : 2022-02-23 15:03
 * @Description :
 * @Version :
 **/
@Data
@ApiModel("领域数据源查询DTO")
public class DataSourceQueryReqDTO {


    @ApiModelProperty("配置库类型类型")
    private String dbConfigType;

    @ApiModelProperty("领域类型")
    private String industry;
}
