package com.zyaud.idata.iam.api.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class ExternalAppRespVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 机构信息
     */
    private List<Corporation> corporation;

    /**
     * 部门信息
     */
    private List<Department> department;

    /**
     * 用户信息
     */
    private List<Staff> staff;




    @Data
    @Accessors(chain = true)
    public static class Corporation {
        @ApiModelProperty(value = "机构ID")
        private String organId;
        @ApiModelProperty(value = "机构编码")
        private String organCode;
        @ApiModelProperty(value = "机构名称")
        private String organName;
        @ApiModelProperty(value = "机构简称")
        private String shortName;
        @ApiModelProperty(value = "机构类型")
        private String organType;
        @ApiModelProperty(value = "机构类型名")
        private String organTypeName;
        @ApiModelProperty(value = "父机构ID")
        private String parentId;
        @ApiModelProperty(value = "是否有效，0无效，1有效")
        private String inUse;
        @ApiModelProperty(value = "机构顺序")
        private Integer sort;
    }

    @Data
    @Accessors(chain = true)
    public static class Department {
        @ApiModelProperty(value = "机构ID")
        private String organId;
        @ApiModelProperty(value = "机构编码")
        private String organCode;
        @ApiModelProperty(value = "机构名称")
        private String organName;
        @ApiModelProperty(value = "机构简称")
        private String shortName;
        @ApiModelProperty(value = "机构类型")
        private String organType;
        @ApiModelProperty(value = "机构类型名")
        private String organTypeName;
        @ApiModelProperty(value = "父机构ID")
        private String parentId;
        @ApiModelProperty(value = "单位ID")
        private String corporationId;
        @ApiModelProperty(value = "是否有效，0无效，1有效")
        private String inUse;
        @ApiModelProperty(value = "机构顺序")
        private Integer sort;
    }

    @Data
    @Accessors(chain = true)
    public static class Staff {
        @ApiModelProperty(value = "人员id")
        private String userId;
        @ApiModelProperty(value = "用户姓名")
        private String userName;
        @ApiModelProperty(value = "状态是否有效，11正常，12停用 13 注销")
        private String userStatus;
        @ApiModelProperty(value = "用户所在的部门ID")
        private String departmentId;
        @ApiModelProperty(value = "用户所在的部门名称")
        private String departmentName;
        @ApiModelProperty(value = "用户所在的机构ID")
        private String corporationId;
        @ApiModelProperty(value = "用户所在的机构名称")
        private String corporationName;
        @ApiModelProperty(value = "职务")
        private String post;
        @ApiModelProperty(value = "职级")
        private String rank;
        @ApiModelProperty(value = "身份证号码")
        private String idCard;
        @ApiModelProperty(value = "性别")
        private String gender;
        @ApiModelProperty(value = "账号有效期")
        private String expiredTime;
        @ApiModelProperty(value = "工作证号码")
        private String identityCard;
        @ApiModelProperty(value = "手机号")
        private String phoneNumber;
        @ApiModelProperty(value = "座机号")
        private String telephone;
        @ApiModelProperty(value = "邮箱地址")
        private String mailbox;
        @ApiModelProperty(value = "用户排序")
        private String sort;
    }


}
