package com.zyaud.idata.iam.biz.third.resourceItem;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : dong
 * @ClassName: RemoteResourceItemCreateReqDto
 * @date : 2021-12-27 20:32
 * @Description :远程资源添加资源项
 * @Version :
 **/

@Data
public class RemoteResourceItemUpdateReqDto {


    @ApiModelProperty("资源项id")
    private String   id;

    @ApiModelProperty("资源项名称")
    private String   resourceName;

    @ApiModelProperty("资源项编码")
    private String   resourceItemCode;


}
