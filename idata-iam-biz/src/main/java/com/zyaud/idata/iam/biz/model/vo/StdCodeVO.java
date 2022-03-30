package com.zyaud.idata.iam.biz.model.vo;

import com.zyaud.fzhx.binder.annotation.BindField;
import com.zyaud.idata.iam.biz.model.entity.StdCode;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StdCodeVO extends StdCode {
    @BindField(bindTo = "stdName", method = "getByCodeType", api = "stdTypeServiceImpl")
    private String stdType;
    private String stdName;

    private boolean isuseable;
}
