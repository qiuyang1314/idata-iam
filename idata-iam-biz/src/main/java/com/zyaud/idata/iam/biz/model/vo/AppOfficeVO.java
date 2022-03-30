package com.zyaud.idata.iam.biz.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AppOfficeVO implements Serializable {
    private String officeId;

    @NotBlank(message = "应用编码不能为空")
    private String appCode;

    private String userId;
}
