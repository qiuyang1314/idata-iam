package com.zyaud.idata.iam.biz.model.vo;

import com.zyaud.fzhx.common.model.TreeNode;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OfficeTreeVO extends TreeNode<String, OfficeTreeVO> {
    private String name;

    private String code;

    /**
     * 父级编号
     */
    private String parentOfficeId;
    private String parentName;

    /**
     * 机构类型
     */
    private String otype;
    private String otypeName;

    /**
     * 机构级别
     */
    private String levels;
    private String levelsName;

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
     * 邮箱
     */
    private String email;

    /**
     * 传真
     */
    private String fax;

    /**
     * 是否启用
     */
    private String useable;

    /**
     * 行政区划id
     */
    private String districtId;
    /**
     * 行政区划名称
     */
    private String districtName;
}
