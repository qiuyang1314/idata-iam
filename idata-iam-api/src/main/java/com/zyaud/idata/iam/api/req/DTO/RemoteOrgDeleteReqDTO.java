package com.zyaud.idata.iam.api.req.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author : dong
 * @ClassName: RemoteOrgCreateRqeDTO
 * @date : 2021-12-27 15:47
 * @Description :远程调用新增机构
 * @Version :
 **/

@Data
public class RemoteOrgDeleteReqDTO {

      @ApiModelProperty("机构编码")
      private List<String> orgCode;


}
