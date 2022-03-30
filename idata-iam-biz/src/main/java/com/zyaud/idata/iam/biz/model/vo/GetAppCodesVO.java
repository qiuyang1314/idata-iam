package com.zyaud.idata.iam.biz.model.vo;

import com.zyaud.fzhx.binder.annotation.BindField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetAppCodesVO {
    @BindField(bindTo = "appName", method = "getAppNameByAppCodes", api = "appServiceImpl")
    private String appCode;

    @ApiModelProperty(value = "应用名")
    private String appName;
}
