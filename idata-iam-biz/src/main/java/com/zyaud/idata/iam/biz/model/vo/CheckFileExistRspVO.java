package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "CheckFileExistRspVO对象", description = "检测文件上传应答VO")
public class CheckFileExistRspVO extends FileUploadRspVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "是否已存在")
    private Boolean exist = false;

    @ApiModelProperty(value = "文件类型")
    private String  fileType;
}
