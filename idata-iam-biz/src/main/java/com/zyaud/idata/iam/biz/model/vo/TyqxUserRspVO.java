package com.zyaud.idata.iam.biz.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @program: idata-iam
 * @description: 统一权限用户信息类
 * @author: qiuyang
 * @create: 2022-02-18 17:28
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TyqxUserRspVO对象", description="统一权限用户信息类")
public class TyqxUserRspVO implements Serializable {

    private static final long serialVersionUID = 1L;

    //private String last_updated;
    private String key_status_zw;
    private String gender;
    private String signature;
    //private String date_created;
    private String ca_e;
    private int sort;
    private String classification;
    private String type;
    private String domain_id;
    private String zhiji;
    private String domain_name;
    private String cengci;
    private String login_name;
    private String org_id;
    private String idcard;
    private String name;
    private String id;
    private String org_name;
    private String email;
    private String secret_code;
    private String key_status_fxw;
    private String status;

    private String telephone;
    private String native_place;
    private String post;
    private String mobilephone;
    private String certificate_type;
    private String nation;
}
