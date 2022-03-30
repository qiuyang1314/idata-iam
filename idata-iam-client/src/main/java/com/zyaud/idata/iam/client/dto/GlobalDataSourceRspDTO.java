
package com.zyaud.idata.iam.client.dto;

import cn.hutool.core.util.ObjectUtil;
import com.zyaud.fzhx.jdbc.enums.DbType;
import com.zyaud.fzhx.jdbc.model.DataSourceInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("配置数据源返回DTO")
public class GlobalDataSourceRspDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty("业务id")
    private String businessId;
    @ApiModelProperty("数据源名称")
    private String dsName;
    @ApiModelProperty("数据库服务名")
    private String dbName;
    @ApiModelProperty("数据库模式")
    private String dbSchema;
    @ApiModelProperty("数据库用户")
    private String dbUser;
    @ApiModelProperty("密码")
    private String dbUserPwd;
    @ApiModelProperty("数据库类型")
    private Integer dbType;
    @ApiModelProperty("连接配置")
    private String dbMode;
    @ApiModelProperty("数据库路径")
    private String dbPath;
    @ApiModelProperty("端口")
    private Integer dbPort;
    @ApiModelProperty("数据库ip")
    private String dbIp;
    @ApiModelProperty("数据库配置")
    private String dbConfig;
    @ApiModelProperty("库类型")
    private String dataConfigType;
    @ApiModelProperty("启用状态 0关闭，1,启用")
    private Integer status;

    public DataSourceInfo wrapperDataSourceInfo() {
        DbType dbType = DbType.toDbType(this.dbType);
        if (ObjectUtil.isEmpty(this.dbSchema) && dbType.equals(DbType.ORACLE)) {
            this.dbSchema = this.dbUser;
        }

        DataSourceInfo dataSourceInfo = (new DataSourceInfo()).setDbtype(dbType).setDbip(this.dbIp).setDbpath(this.dbPath).setDbport(this.dbPort).setDbname(this.dbName).setDbschema(this.dbSchema).setDbuser(this.dbUser).setDbuserPwd(this.dbUserPwd);
        if (DbType.H2.equals(dbType)) {
            dataSourceInfo.setDbconfig(this.dbConfig);
        }

        if (DbType.MYSQL.equals(dbType)) {
            dataSourceInfo.setDbschema(this.dbName);
        }

        return dataSourceInfo;
    }


}
