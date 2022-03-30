package com.zyaud.idata.iam.biz.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.zyaud.idata.iam.biz.model.entity.Download.*;

/**
 * <p>
 * 下载中心
 * </p>
 *
 * @author fzhx-aicode
 * @since 2021-05-20
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DownloadCrateDTO对象", description="下载中心")
public class DownloadCreateDTO implements Serializable {

    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "应用编码", required = true)
    @TableField(value = APPCODE, condition = LIKE)
    @Length(max = 64, message = "应用编码长度不能超过64")
    @NotBlank(message = "应答编码不能为空")
    private String appCode;

    @ApiModelProperty(value = "任务主键", required = true)
    @TableField(value = TASKID, condition = LIKE)
    @Length(max = 64, message = "任务主键长度不能超过64")
    @NotBlank(message = "任务主键不能为空")
    private String taskId;

    @ApiModelProperty(value = "任务名称 用于与任务对应", required = true)
    @TableField(value = TASKNAME, condition = LIKE)
    @Length(max = 128, message = "任务名称 用于与任务对应长度不能超过128")
    @NotBlank(message = "任务名称不能为空")
    private String taskName;

    @ApiModelProperty(value = "下载类型", required = true)
    @TableField(value = DTYPE, condition = LIKE)
    @Length(max = 64, message = "下载类型长度不能超过64")
    @NotBlank(message = "下载类型不能为空")
    private String dtype;

//    @ApiModelProperty(value = "存放全路径 要下载的文件存放路径")
//    @TableField(value = FPATH, condition = LIKE)
//    @Length(max = 512, message = "存放全路径 要下载的文件存放路径长度不能超过512")
//    private String fpath;
//
//    @ApiModelProperty(value = "文件较验值")
//    @TableField(value = MD5, condition = LIKE)
//    @Length(max = 32, message = "文件较验值长度不能超过32")
//    private String md5;
//
//    @ApiModelProperty(value = "文件大小")
//    @TableField(FSIZE)
//    private Long fsize;
//
//    @ApiModelProperty(value = "下载状态")
//    @TableField(value = STATUS, condition = LIKE)
//    @Length(max = 64, message = "下载状态长度不能超过64")
//    private String status;




}
