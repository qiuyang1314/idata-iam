package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @program: idata-iam
 * @description: 统一权限机构信息
 * @author: qiuyang
 * @create: 2022-02-18 18:02
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TyqxOrgRspVO对象", description="统一权限机构信息")
public class TyqxOrgRspVO {
    private String parent;
    private String last_updated;
    private String code;
    private int is_operation;
    private String date_created;
    private String memo;
    private String org_category;
    private int sort;
    private String type;
    private String domain_id;
    private String path;
    private String name;
    private String org_level;
    private String id;
    private String status;
}
