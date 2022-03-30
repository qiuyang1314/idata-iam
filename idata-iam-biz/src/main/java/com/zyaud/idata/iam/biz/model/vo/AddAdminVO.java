package com.zyaud.idata.iam.biz.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Accessors(chain = true)
public class AddAdminVO {
    //角色名
    @NotBlank(message = "角色名不能为空")
    private String roleName;
    //账号ids
    private List<String> userCodeIds;
}
