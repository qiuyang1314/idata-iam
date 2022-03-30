package com.zyaud.idata.iam.biz.third.appResultDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: idata-iam
 * @description: appframe响应接口
 * @author: qiuyang
 * @create: 2022-01-04 10:47
 **/
@Data
public class AppResultDto {

    @ApiModelProperty("状态码")
    private String code;

    @ApiModelProperty("返回数据")
    private String data;

    @ApiModelProperty("提示信息")
    private String errMsg;

}
