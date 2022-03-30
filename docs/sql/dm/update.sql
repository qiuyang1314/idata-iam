/**
新增行政区划表
蔡凡
2022-01-04
 */
 CREATE TABLE "IDATA-IAM".zyaud_area(
    AREANAME VARCHAR(512) NOT NULL,
    AREACODE VARCHAR(512) NOT NULL,
    PID VARCHAR(512),
    AREALEVEL VARCHAR(64),
    URL VARCHAR(512),
    create_time DATE NOT NULL,
    create_id VARCHAR(64) NOT NULL,
    update_time DATE NOT NULL,
    update_id VARCHAR(64) NOT NULL,
    del_flag CHAR(1) NOT NULL,
    PRIMARY KEY (AREACODE)
);

COMMENT ON TABLE "IDATA-IAM".zyaud_area IS '行政区划表';
COMMENT ON COLUMN "IDATA-IAM".zyaud_area.AREANAME IS '名称';
COMMENT ON COLUMN "IDATA-IAM".zyaud_area.AREACODE IS '编码';
COMMENT ON COLUMN "IDATA-IAM".zyaud_area.PID IS '父ID';
COMMENT ON COLUMN "IDATA-IAM".zyaud_area.AREALEVEL IS '级别';
COMMENT ON COLUMN "IDATA-IAM".zyaud_area.URL IS '路径';
COMMENT ON COLUMN "IDATA-IAM".zyaud_area.create_time IS '创建时间';
COMMENT ON COLUMN "IDATA-IAM".zyaud_area.create_id IS '创建人';
COMMENT ON COLUMN "IDATA-IAM".zyaud_area.update_time IS '更新时间';
COMMENT ON COLUMN "IDATA-IAM".zyaud_area.update_id IS '更新人';
COMMENT ON COLUMN "IDATA-IAM".zyaud_area.del_flag IS '删除标识';
