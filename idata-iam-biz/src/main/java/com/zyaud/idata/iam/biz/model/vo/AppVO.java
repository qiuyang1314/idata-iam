package com.zyaud.idata.iam.biz.model.vo;

import com.zyaud.idata.iam.biz.model.entity.App;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AppVO extends App {
    private boolean isuseable;
}
