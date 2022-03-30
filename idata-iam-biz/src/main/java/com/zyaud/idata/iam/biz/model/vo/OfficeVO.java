package com.zyaud.idata.iam.biz.model.vo;

import com.zyaud.fzhx.binder.annotation.BindField;
import com.zyaud.idata.iam.biz.model.entity.Office;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OfficeVO extends Office {
    @BindField(bindTo = "appName", method = "getAppNameById", api = "appServiceImpl")
    private String appId;
    private String appName;

    /**
     * 机构类型
     */
    @BindField(bindTo="otypeName", dict = "orgType", method="getDictData", api = "stdCodeServiceImpl")
    private String otype;
    private String otypeName;

    /**
     * 机构级别
     */
    @BindField(bindTo="levelsName", dict = "orgRank", method="getDictData", api = "stdCodeServiceImpl")
    private String levels;
    private String levelsName;
}
