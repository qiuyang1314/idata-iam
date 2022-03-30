package com.zyaud.idata.iam.biz.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zyaud.fzhx.core.model.TimeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 下载中心
 * </p>
 *
 * @author fzhx-aicode
 * @since 2021-05-20
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(Download.DOWNLOAD)
@ApiModel(value="Download对象", description="下载中心")
public class Download extends TimeEntity<String> {

    private static final long serialVersionUID=1L;
    public static final String DOWNLOAD = "iam_download";
    public static final String APPCODE = "app_code";
    public static final String TASKID = "task_id";
    public static final String TASKNAME = "task_name";
    public static final String DTYPE = "dtype";
    public static final String FPATH = "fpath";
    public static final String MD5 = "md5";
    public static final String FSIZE = "fsize";
    public static final String STATUS = "status";


    @ApiModelProperty(value = "应用编码")
    @TableField(APPCODE)
    @Length(max = 64, message = "应用编码长度不能超过64")
    private String appCode;


    @ApiModelProperty(value = "任务主键")
    @TableField(TASKID)
    @Length(max = 64, message = "任务主键长度不能超过64")
    private String taskId;


    @ApiModelProperty(value = "任务名称 用于与任务对应")
    @TableField(TASKNAME)
    @Length(max = 128, message = "任务名称 用于与任务对应长度不能超过128")
    private String taskName;


    @ApiModelProperty(value = "下载类型")
    @TableField(DTYPE)
    @Length(max = 64, message = "下载类型长度不能超过64")
    private String dtype;


    @ApiModelProperty(value = "存放全路径 要下载的文件存放路径")
    @TableField(FPATH)
    @Length(max = 512, message = "存放全路径 要下载的文件存放路径长度不能超过512")
    private String fpath;


    @ApiModelProperty(value = "文件较验值")
    @TableField(MD5)
    @Length(max = 32, message = "文件较验值长度不能超过32")
    private String md5;


    @ApiModelProperty(value = "文件大小")
    @TableField(FSIZE)
        private Long fsize;


    @ApiModelProperty(value = "下载状态 0-未开始 1-进行中 2-成功 3-失败")
    @TableField(STATUS)
    @Length(max = 64, message = "下载状态长度不能超过64")
    private String status;



}
