package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ApiModel(value = "FileUploadRspVO对象", description = "文件上传信息VO")
public class FileUploadRspVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "存放所在组名")
    private String groupName;

    @ApiModelProperty(value = "存放文件编号")
    private String fileId;

    @ApiModelProperty(value = "文件访问路径")
    private String imgUrl;
}
