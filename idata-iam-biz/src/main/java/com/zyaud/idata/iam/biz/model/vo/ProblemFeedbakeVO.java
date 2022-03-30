package com.zyaud.idata.iam.biz.model.vo;

import com.zyaud.fzhx.binder.annotation.BindField;
import com.zyaud.idata.iam.biz.model.entity.ProblemFeedback;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ProblemFeedbakeVO extends ProblemFeedback {

    @BindField(bindTo = "issueSystemName", dict = "issueSystemType", method = "getDictData", api = "stdCodeServiceImpl")
    private String issueSystem;
    private String issueSystemName;

    private List<String> Urls;

}
