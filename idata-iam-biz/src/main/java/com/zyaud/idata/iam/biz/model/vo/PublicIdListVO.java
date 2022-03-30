package com.zyaud.idata.iam.biz.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author CaiFan
 * @Date 2020/8/6 12:10
 */
@Data
public class PublicIdListVO {
    private List<String> ids;

    private String operationName;
}
