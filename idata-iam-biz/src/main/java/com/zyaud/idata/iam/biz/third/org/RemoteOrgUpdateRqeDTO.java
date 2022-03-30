package com.zyaud.idata.iam.biz.third.org;

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
public class RemoteOrgUpdateRqeDTO {

    @ApiModelProperty("机构编码")
    private String orgCode;

    @ApiModelProperty("机构名称")
    private String orgName;


    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("地址")
    private String orgAddr;

}
