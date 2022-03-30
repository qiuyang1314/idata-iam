package com.zyaud.idata.iam.biz.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Accessors(chain = true)
public class UserCodeIdsAndAppCodeVO {
    /**
     * 账号ids
     */
//    @NotEmpty(message = "账号id不能为空")
    private List<String> userCodeIds;

    /**
     * 应用编码
     */
    @NotBlank(message = "应用编码不能为空")
    private String appCode;
}
