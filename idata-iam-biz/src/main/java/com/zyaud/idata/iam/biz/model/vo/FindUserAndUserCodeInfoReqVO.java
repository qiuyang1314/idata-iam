package com.zyaud.idata.iam.biz.model.vo;

import com.zyaud.fzhx.binder.annotation.BindQuery;
import com.zyaud.fzhx.binder.enums.Comparison;
import com.zyaud.idata.iam.biz.model.entity.App;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FindUserAndUserCodeInfoReqVO {
    @ApiModelProperty(value = "应用id")
    @BindQuery(field = App.ID, comparison = Comparison.EQ)
    private String id;

    @ApiModelProperty(value = "应用名字")
    @BindQuery(field = App.APPNAME, comparison = Comparison.EQ)
    private String appName;

    @ApiModelProperty(value = "应用编码")
    @BindQuery(field = App.APPCODE, comparison = Comparison.EQ)
    private String appCode;

    @ApiModelProperty(value = "打开方式")
    @BindQuery(field = App.OPENTYPE, comparison = Comparison.EQ)
    private String openType;

    @ApiModelProperty(value = "是否生效")
    @BindQuery(field = App.USEABLE, comparison = Comparison.EQ)
    private String useable;
}
