package com.zyaud.idata.iam.api.req.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : dong
 * @ClassName: RemoteOrgUpdateRqeDTO
 * @date : 2021-12-27 20:00
 * @Description :
 * @Version :
 **/

@Data
public class RemoteOrgUpdateReqDTO {

    @ApiModelProperty("机构编码")
    private String orgCode;

    @ApiModelProperty("机构名称")
    private String orgName;


    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("地址")
    private String orgAddr;

    @ApiModelProperty("父级编码    最高父级 为空 即可")
    private String parentOrgCode;
}
