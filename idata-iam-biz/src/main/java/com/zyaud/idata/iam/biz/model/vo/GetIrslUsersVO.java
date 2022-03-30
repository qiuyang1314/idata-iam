package com.zyaud.idata.iam.biz.model.vo;

import com.zyaud.fzhx.core.model.PageParam;
import lombok.Data;

@Data
public class GetIrslUsersVO extends PageParam {
    /**
     * 账号类型
     */
    private String type;
    /**
     * 账号
     */
    private String loginName;
    /**
     * 状态(0正常1锁定2禁用)
     */
    private String status;
}
