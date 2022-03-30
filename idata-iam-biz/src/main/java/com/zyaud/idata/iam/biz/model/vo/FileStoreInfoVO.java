package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Objects;

@Data
@Accessors(chain = true)
@ApiModel(value = "FileStoreInfoVO对象", description = "文件存储信息VO")
public class FileStoreInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件名称")
    @Length(max = 128, message = "文件名称长度不能超过128")
    private String fileName;

    @ApiModelProperty(value = "文件大小")
    private Long fileSize;

    @ApiModelProperty(value = "md5")
    @Length(max = 32, message = "md5长度不能超过32")
    private String fileMd5;

    @ApiModelProperty(value = "组名")
    private String groupName;

    @ApiModelProperty(value = "文件id")
    private String fileId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileStoreInfoVO that = (FileStoreInfoVO) o;
        return fileName.equals(that.fileName) && fileSize.equals(that.fileSize) && fileMd5.equals(that.fileMd5)
                && groupName.equals(that.groupName) && fileId.equals(that.fileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, fileSize, fileMd5, groupName, fileId);
    }
}
