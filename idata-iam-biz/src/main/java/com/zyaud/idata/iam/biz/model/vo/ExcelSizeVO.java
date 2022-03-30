package com.zyaud.idata.iam.biz.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExcelSizeVO {

    //总行数
    private int lastRowNum;

    //总列数
    private int columnCount;
}
