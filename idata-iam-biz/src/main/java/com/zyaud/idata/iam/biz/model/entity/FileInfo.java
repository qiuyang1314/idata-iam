package com.zyaud.idata.iam.biz.model.entity;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zyaud.fzhx.core.model.TimeEntity;
import com.zyaud.idata.iam.biz.model.vo.FileGroupNameAndFileIdVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 文件信息
 * </p>
 *
 * @author luohuixiang
 * @since 2021-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("iam_file_info")
@ApiModel(value="FileInfo对象", description="文件信息")
public class FileInfo extends TimeEntity<String> {

    private static final long serialVersionUID = 1L;

    public static final String ID = "id";
    public static final String BIZ_ID = "biz_id";
    public static final String BIZ_TYPE = "biz_type";
    public static final String FNAME = "fname";
    public static final String FTYPE = "ftype";
    public static final String FSIZE = "fsize";
    public static final String FURL = "furl";
    public static final String PREVIEW_URL = "preview_url";
    public static final String MD5 = "md5";
    public static final String FILEBIZTYPE = "file_biz_type";

    @ApiModelProperty(value = "业务主键")
    @TableField(BIZ_ID)
    private String bizId;

    @ApiModelProperty(value = "业务类型 0--用户头像文件；1--首页轮播图")
    @TableField(BIZ_TYPE)
    private String bizType;

    @ApiModelProperty(value = "文件名")
    @TableField(FNAME)
    private String fname;

    @ApiModelProperty(value = "文件类型 doc,xls")
    @TableField(FTYPE)
    private String ftype;

    @ApiModelProperty(value = "文件大小 unsigned long")
    @TableField(FSIZE)
    private Long fsize;

    @ApiModelProperty(value = "文件存放地址")
    @TableField(FURL)
    private String furl;

    @ApiModelProperty(value = "预览路径")
    @TableField(PREVIEW_URL)
    private String previewUrl;

    @ApiModelProperty(value = "较验值")
    @TableField(MD5)
    private String md5;

    @ApiModelProperty(value = "文件业务分类")
    @TableField(FILEBIZTYPE)
    @Length(max = 32, message = "文件业务分类长度不能超过32")
    private String fileBizType;

    /**
     * 获取文件组名和文件id
     */
    public static FileGroupNameAndFileIdVO getFileGroupNameAndFileId(String fileUrl) {
        FileGroupNameAndFileIdVO fileGroupNameAndFileIdVO = new FileGroupNameAndFileIdVO();
        if (ObjectUtil.isNotEmpty(fileUrl)) {
            String groupName = fileUrl.substring(0, fileUrl.indexOf("/"));
            fileGroupNameAndFileIdVO.setGroupName(groupName).setFileId(fileUrl.substring(groupName.length() + 1));
        }
        return fileGroupNameAndFileIdVO;
    }
}
