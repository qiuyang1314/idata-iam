package com.zyaud.idata.iam.biz.third.org;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : dong
 * @ClassName: RemoteOrgCreateRqeDTO
 * @date : 2021-12-27 15:47
 * @Description :远程调用新增机构
 * @Version :
 **/

@Data
public class RemoteOrgDeleteRqeDTO {

      @ApiModelProperty("机构编码")
      private String orgCode;


}
