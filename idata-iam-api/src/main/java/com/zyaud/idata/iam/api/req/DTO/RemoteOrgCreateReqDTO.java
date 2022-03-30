package com.zyaud.idata.iam.api.req.DTO;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @Description: 远程删除机构DTO
* @Author: dong
* @Date: 2021/12/27 19:56
*/
@Data
public class RemoteOrgCreateReqDTO {


      @ApiModelProperty("机构编码")
      private String orgCode;

      @ApiModelProperty("机构名称")
      private String orgName;

      @ApiModelProperty("父级编码    最高父级 为空 即可")
      private String parentOrgCode;

      @ApiModelProperty("邮箱")
      private String email;


      @ApiModelProperty("地址")
      private String orgAddr;
}
