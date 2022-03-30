package com.zyaud.idata.iam.api.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DownloadGetRspVO对象", description="获取一条下载中心记录")
public class DownloadGetRspVO implements Serializable {
    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "应用编码")
    private String appCode;

    @ApiModelProperty(value = "任务主键")
    private String taskId;

    @ApiModelProperty(value = "任务名称 用于与任务对应")
    private String taskName;

    @ApiModelProperty(value = "下载类型")
    private String dtype;

    @ApiModelProperty(value = "存放全路径 要下载的文件存放路径")
    private String fpath;

    @ApiModelProperty(value = "文件较验值")
    private String md5;

    @ApiModelProperty(value = "文件大小")
    private Long fsize;

    @ApiModelProperty(value = "下载状态")
    private String status;
}
