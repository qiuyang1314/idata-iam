package com.zyaud.idata.iam.biz.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zyaud.fzhx.core.model.TimeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 问题反馈图片关联表
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(ProblemPhoto.PROBLEMPHOTO)
public class ProblemPhoto extends TimeEntity<String> {

    private static final long serialVersionUID=1L;
    public static final String PROBLEMPHOTO = "iam_problem_photo";
    public static final String GROUPNAME = "group_name";
    public static final String FILEID = "file_id";
    public static final String ISSUEID = "issue_id";


    /**
     * 组名
     */
    @TableField(GROUPNAME)
    @Length(max = 255, message = "组名长度不能超过255")
    private String groupName;


    /**
     * 文件id
     */
    @TableField(FILEID)
    @Length(max = 100, message = "文件id长度不能超过100")
    private String fileId;


    /**
     * 问题反馈id
     */
    @TableField(ISSUEID)
    @Length(max = 64, message = "问题反馈id长度不能超过64")
    private String issueId;



}
