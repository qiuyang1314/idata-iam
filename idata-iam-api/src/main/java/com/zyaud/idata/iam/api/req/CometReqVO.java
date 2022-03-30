package com.zyaud.idata.iam.api.req;

import com.zyaud.fzhx.common.model.Result;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class CometReqVO {
    /**
     * 用户ID
     */
    @NotNull
    private String userId;

    /**
     * 时间戳
     */
    @NotNull
    private String tno;

    /**
     * 消息类型
     */
    @NotNull
    private String method;
    /**
     * 负载数据
     */
    @NotNull
    private Result result;

    public CometReqVO(@NotNull String userId, @NotNull String method, Result result) {
        this.userId = userId;
        this.method = method;
        this.result = result;
    }
}
