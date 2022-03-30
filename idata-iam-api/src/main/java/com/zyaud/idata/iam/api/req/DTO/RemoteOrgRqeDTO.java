package com.zyaud.idata.iam.api.req.DTO;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
* @Description: 机构同步Dto
* @Author: dong
* @Date: 2021/12/27 19:56
*/
@Data
public class RemoteOrgRqeDTO {


      @ApiModelProperty(value = "机构ID")
      @NotBlank(message = "机构id不能为空")
      private String organId;

      @ApiModelProperty(value = "机构编码")
      private String organCode;

      @ApiModelProperty(value = "机构名称")
      private String organName;

      @ApiModelProperty(value = "机构类型")
      private String organType;

      @ApiModelProperty(value = "机构类型名")
      private String organTypeName;

      @ApiModelProperty(value = "行政区划")
      private String districtId;


      @ApiModelProperty(value = "父机构ID")
      private String parentId;

      @ApiModelProperty(value = "是否有效，0启用，1关闭")
      private String inUse;

      @ApiModelProperty(value = "机构顺序")
      private Integer sort;

}
