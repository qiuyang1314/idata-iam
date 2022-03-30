package com.zyaud.idata.iam.biz.model.vo;

import com.zyaud.fzhx.binder.annotation.BindField;
import com.zyaud.idata.iam.biz.model.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserVO extends User {
    @BindField(bindTo = "dictLabel", dict = "thickGrade", method = "getDictData", api = "stdCodeServiceImpl")
    private String secirityFlag;
    private String dictLabel;
    @BindField(bindTo = "officeName", method = "getOfficeNameById", api = "officeServiceImpl")
    private String officeId;
    private String officeName;

    private String loginName;
    private String userCodeId;
}
