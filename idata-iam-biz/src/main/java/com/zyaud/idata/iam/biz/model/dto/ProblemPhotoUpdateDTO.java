package com.zyaud.idata.iam.biz.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 问题反馈图片关联表
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class ProblemPhotoUpdateDTO implements Serializable {

    private static final long serialVersionUID=1L;
    /**
     * 组名
     */
    @TableField(value = "group_name", condition = LIKE)
    @Length(max = 255, message = "组名长度不能超过64")
    private String groupName;



    /**
     * 文件id
     */
    @TableField(value = "file_id", condition = LIKE)
    @Length(max = 255, message = "文件id长度不能超过100")
    private String fileId;



    /**
     * 问题反馈id
     */
    @TableField(value = "issue_id", condition = LIKE)
    @Length(max = 255, message = "问题反馈id长度不能超过64")
    private String issueId;


}
