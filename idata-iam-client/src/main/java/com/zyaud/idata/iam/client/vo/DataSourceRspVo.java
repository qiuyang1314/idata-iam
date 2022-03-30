package com.zyaud.idata.iam.client.vo;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : dong
 * @ClassName: DataSourceRspVo
 * @date : 2021-12-10 10:11
 * @Description :数据源信息返回vo
 * @Version :
 **/
@ApiModel("数据源信息返回vo")
@Data
public class DataSourceRspVo implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "数据源名称")
    private String dsName;

    @ApiModelProperty(value = "驱动id")
    private String driverId;


    @ApiModelProperty(value = "数据库端口")
    private int dbPort;

    @ApiModelProperty(value = "数据库用户")
    private String dbUser;

    @ApiModelProperty(value = "数据库ip")
    private String dbIp;

    @ApiModelProperty(value = "数据库类型")
    private int dbType;

    @ApiModelProperty(value = "数据库服务名")
    private String dbName;

    @ApiModelProperty(value = "数据库版本")
    private String dbVersion;

    @ApiModelProperty(value = "数据库密码")
    private String dbUserPwd;

    @ApiModelProperty(value = "数据库数据类型")
    private String dataConfigType;

    @ApiModelProperty(value = "数据源年度")
    private String year;

    @ApiModelProperty(value = "备注")
    private String remark;



    @ApiModelProperty(value = "数据库模式")
    private String dbSchema;


    @ApiModelProperty(value = "路径")
    private String dbPath;

    @ApiModelProperty(value = "数据库连接类型")
    private String dbMode;



}
