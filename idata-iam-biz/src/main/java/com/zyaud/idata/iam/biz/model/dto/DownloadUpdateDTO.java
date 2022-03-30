package com.zyaud.idata.iam.biz.model.dto;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

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
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value="DownloadUpdateDTO对象", description="下载中心")
public class DownloadUpdateDTO implements Serializable {

    private static final long serialVersionUID=1L;
//    @ApiModelProperty(value = "主键")
//    @NotNull(message = "id不能为空", groups = IdEntity.Update.class)
//    private String id;
//
    @ApiModelProperty(value = "应用编码", required = true)
    @BindQuery(field = APPCODE, comparison = Comparison.LIKE)
    @Length(max = 64, message = "应用编码长度不能超过64")
    @NotBlank(message = "应用编码不能为空")
    private String appCode;

    @ApiModelProperty(value = "任务主键", required = true)
    @Length(max = 64, message = "任务主键长度不能超过64")
    @NotBlank(message = "任务主键不能为空")
    private String taskId;

//    @ApiModelProperty(value = "任务名称 用于与任务对应")
//    @BindQuery(field = TASKNAME, comparison = Comparison.LIKE)
//    @Length(max = 128, message = "任务名称 用于与任务对应长度不能超过128")
//    private String taskName;

//    @ApiModelProperty(value = "下载类型")
//    @BindQuery(field = DTYPE, comparison = Comparison.LIKE)
//    @Length(max = 64, message = "下载类型长度不能超过64")
//    private String dtype;

    @ApiModelProperty(value = "存放全路径 要下载的文件存放路径")
    @BindQuery(field = FPATH, comparison = Comparison.LIKE)
    @Length(max = 512, message = "存放全路径 要下载的文件存放路径长度不能超过512")
    private String fpath;

    @ApiModelProperty(value = "文件较验值")
    @BindQuery(field = MD5, comparison = Comparison.LIKE)
    @Length(max = 32, message = "文件较验值长度不能超过32")
    private String md5;

    @ApiModelProperty(value = "文件大小")
    @BindQuery(field = FSIZE)
    private Long fsize;

    @ApiModelProperty(value = "下载状态", required = true)
    @BindQuery(field = STATUS, comparison = Comparison.LIKE)
    @Length(max = 64, message = "下载状态长度不能超过64")
    @NotBlank(message = "下载状态不能为空")
    private String status;




}
