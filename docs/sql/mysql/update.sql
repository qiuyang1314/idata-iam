
/**
新增行政区划表
蔡凡
2022-01-04
 */
DROP TABLE IF EXISTS zyaud_area;
CREATE TABLE zyaud_area(
    AREANAME VARCHAR(512) NOT NULL   COMMENT '名称' ,
    AREACODE VARCHAR(512) NOT NULL   COMMENT '编码' ,
    PID VARCHAR(512)    COMMENT '父ID' ,
    AREALEVEL VARCHAR(64)    COMMENT '级别' ,
    URL VARCHAR(512)    COMMENT '路径' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    create_id VARCHAR(64) NOT NULL   COMMENT '创建人' ,
    update_time DATETIME NOT NULL   COMMENT '更新时间' ,
    update_id VARCHAR(64) NOT NULL   COMMENT '更新人' ,
    del_flag VARCHAR(1) NOT NULL   COMMENT '删除标识' ,
    PRIMARY KEY (AREACODE)
)  COMMENT = '行政区划表';
