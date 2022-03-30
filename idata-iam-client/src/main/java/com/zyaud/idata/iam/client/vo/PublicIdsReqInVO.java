package com.zyaud.idata.iam.client.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PublicIdsReqInVO {

    private List<String> ids;
}
