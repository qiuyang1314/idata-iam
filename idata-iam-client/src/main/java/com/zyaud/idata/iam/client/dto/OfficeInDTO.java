package com.zyaud.idata.iam.client.dto;

import com.zyaud.fzhx.core.model.TimeEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OfficeInDTO extends TimeEntity<String> {
    private static final long serialVersionUID=1L;
    /**
     * 父级编号
     */
    private String parentId;


    /**
     * 所有父级编号
     */
    private String parentIds;


    /**
     * 名称
     */
    private String name;

    /**
     * 机构编码
     */
    private String code;

    /**
     * 全路径名称
     */
    private String paths;


    /**
     * 排序
     */
    private Integer orderIndex;


    /**
     * 系统编码
     */
    private String appId;


    /**
     * 行政区划
     */
    private String districtId;


    /**
     * 机构类型
     */
    private String otype;


    /**
     * 机构级别
     */
    private String levels;


    /**
     * 地址
     */
    private String address;


    /**
     * 负责人
     */
    private String supervisor;


    /**
     * 电话
     */
    private String phone;


    /**
     * 传真
     */
    private String fax;


    /**
     * 邮箱
     */
    private String email;


    /**
     * 是否启用
     */
    private String useable;
}
