
-- 基础表结构

CREATE TABLE IDATA_IAM.IAM_APP  (
  ID CHARACTER VARYING(64) NOT NULL,
  APP_NAME CHARACTER VARYING(64) NOT NULL ,
  APP_CODE CHARACTER VARYING(20) NOT NULL,
  URL CHARACTER VARYING(255) DEFAULT NULL,
  IMG CHARACTER VARYING(64) ,
  ORDER_INDEX INTEGER NOT NULL DEFAULT 1,
  APP_KEY TEXT ,
  APP_SECRET TEXT ,
  OPEN_TYPE CHARACTER VARYING(10) ,
  USEABLE CHARACTER(1) ,
  DEL_FLAG CHARACTER(1) DEFAULT '0',
  CREATE_ID CHARACTER VARYING(64),
  CREATE_TIME TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  UPDATE_ID CHARACTER VARYING(64) ,
  UPDATE_TIME TIMESTAMP WITHOUT TIME ZONE NOT NULL ,
  PRIMARY KEY (ID)
)
    BINLOG ON ;

COMMENT ON TABLE IDATA_IAM.IAM_APP IS '系统应用';
COMMENT ON COLUMN IDATA_IAM.IAM_APP.ID IS '主键ID';
COMMENT ON COLUMN IDATA_IAM.IAM_APP.APP_NAME IS '系统应用名字';
COMMENT ON COLUMN IDATA_IAM.IAM_APP.APP_CODE IS '系统应用编码';
COMMENT ON COLUMN IDATA_IAM.IAM_APP.URL IS 'URL';
COMMENT ON COLUMN IDATA_IAM.IAM_APP.IMG IS '应用图片';
COMMENT ON COLUMN IDATA_IAM.IAM_APP.ORDER_INDEX IS '排序';
COMMENT ON COLUMN IDATA_IAM.IAM_APP.APP_KEY IS '系统应用公钥(账号)';
COMMENT ON COLUMN IDATA_IAM.IAM_APP.APP_SECRET IS '系统应用私钥(密码)';
COMMENT ON COLUMN IDATA_IAM.IAM_APP.OPEN_TYPE IS '打开方式(0默认 1跳转)';
COMMENT ON COLUMN IDATA_IAM.IAM_APP.USEABLE IS '是否生效';
COMMENT ON COLUMN IDATA_IAM.IAM_APP.DEL_FLAG IS '删除标记';
COMMENT ON COLUMN IDATA_IAM.IAM_APP.CREATE_ID IS '创建人';
COMMENT ON COLUMN IDATA_IAM.IAM_APP.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN IDATA_IAM.IAM_APP.UPDATE_ID IS '更新人';
COMMENT ON COLUMN IDATA_IAM.IAM_APP.UPDATE_TIME IS '更新时间';


CREATE TABLE IDATA_IAM.IAM_CONFIG(
    ID character varying(64) NOT NULL DEFAULT  ('0'),
    CTYPE character varying(10) NOT NULL DEFAULT  ('0'),
    CCODE character varying(255),
    CITEM character varying(255),
    CVALUE TEXT NOT NULL,
    ORDER_INDEX character varying(255) NOT NULL DEFAULT  (1),
    DEL_FLAG character(1) NOT NULL DEFAULT  ('0'),
    CREATE_TIME timestamp without time zone NOT NULL,
    CREATE_ID character varying(64) NOT NULL,
    UPDATE_TIME timestamp without time zone NOT NULL,
    UPDATE_ID character varying(64) NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE IDATA_IAM.IAM_CONFIG IS '';
COMMENT ON COLUMN IDATA_IAM.IAM_CONFIG.ID IS '主键';
COMMENT ON COLUMN IDATA_IAM.IAM_CONFIG.CTYPE IS '配置类型;0：系统配置）';
COMMENT ON COLUMN IDATA_IAM.IAM_CONFIG.CCODE IS '配置项编码;0：密码过期时间，1：IP配置）';
COMMENT ON COLUMN IDATA_IAM.IAM_CONFIG.CITEM IS '配置项;配置中文名称）';
COMMENT ON COLUMN IDATA_IAM.IAM_CONFIG.CVALUE IS '配置项值';
COMMENT ON COLUMN IDATA_IAM.IAM_CONFIG.ORDER_INDEX IS '排序';
COMMENT ON COLUMN IDATA_IAM.IAM_CONFIG.DEL_FLAG IS '是否删除 ;0否1是)';
COMMENT ON COLUMN IDATA_IAM.IAM_CONFIG.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN IDATA_IAM.IAM_CONFIG.CREATE_ID IS '创建ID';
COMMENT ON COLUMN IDATA_IAM.IAM_CONFIG.UPDATE_TIME IS '修改时间';
COMMENT ON COLUMN IDATA_IAM.IAM_CONFIG.UPDATE_ID IS '修改ID';

CREATE TABLE IDATA_IAM.IAM_DOWNLOAD(
    ID character varying(64) NOT NULL,
    APP_CODE character varying(64) NOT NULL,
    TASK_ID character varying(64) NOT NULL,
    TASK_NAME character varying(128) NOT NULL,
    DTYPE character varying(64) NOT NULL,
    FPATH character varying(512),
    MD5 character varying(32),
    FSIZE BIGINT,
    STATUS character varying(64) NOT NULL,
    CREATE_ID character varying(64) NOT NULL,
    CREATE_TIME timestamp without time zone NOT NULL,
    UPDATE_ID character varying(64) NOT NULL,
    UPDATE_TIME timestamp without time zone NOT NULL,
    DEL_FLAG character(1) NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE IDATA_IAM.IAM_DOWNLOAD IS '下载中心';
COMMENT ON COLUMN IDATA_IAM.IAM_DOWNLOAD.ID IS '主键';
COMMENT ON COLUMN IDATA_IAM.IAM_DOWNLOAD.APP_CODE IS '系统应用编码';
COMMENT ON COLUMN IDATA_IAM.IAM_DOWNLOAD.TASK_ID IS '任务主键';
COMMENT ON COLUMN IDATA_IAM.IAM_DOWNLOAD.TASK_NAME IS '任务名称;用于与任务对应';
COMMENT ON COLUMN IDATA_IAM.IAM_DOWNLOAD.DTYPE IS '下载类型';
COMMENT ON COLUMN IDATA_IAM.IAM_DOWNLOAD.FPATH IS '存放全路径;要下载的文件存放路径';
COMMENT ON COLUMN IDATA_IAM.IAM_DOWNLOAD.MD5 IS '文件较验值';
COMMENT ON COLUMN IDATA_IAM.IAM_DOWNLOAD.FSIZE IS '文件大小';
COMMENT ON COLUMN IDATA_IAM.IAM_DOWNLOAD.STATUS IS '下载状态;0-未开始 1-进行中 2-成功 3-失败';
COMMENT ON COLUMN IDATA_IAM.IAM_DOWNLOAD.CREATE_ID IS '创建人';
COMMENT ON COLUMN IDATA_IAM.IAM_DOWNLOAD.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN IDATA_IAM.IAM_DOWNLOAD.UPDATE_ID IS '更新人';
COMMENT ON COLUMN IDATA_IAM.IAM_DOWNLOAD.UPDATE_TIME IS '更新时间';
COMMENT ON COLUMN IDATA_IAM.IAM_DOWNLOAD.DEL_FLAG IS '删除标识';


CREATE TABLE IDATA_IAM.IAM_FILE_INFO(
    ID character varying(64) NOT NULL,
    BIZ_ID character varying(64) NOT NULL,
    BIZ_TYPE character varying(32) NOT NULL,
    FNAME character varying(128) NOT NULL,
    FTYPE character varying(32) NOT NULL,
    FSIZE BIGINT NOT NULL,
    FURL character varying(512) NOT NULL,
    FILE_BIZ_TYPE character varying(32),
    PREVIEW_URL character varying(512) NOT NULL,
    MD5 character varying(32) NOT NULL,
    DEL_FLAG character(1) NOT NULL DEFAULT  ('0'),
    CREATE_ID character varying(64) NOT NULL,
    CREATE_TIME timestamp without time zone NOT NULL,
    UPDATE_ID character varying(64) NOT NULL,
    UPDATE_TIME timestamp without time zone NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE IDATA_IAM.IAM_FILE_INFO IS '整改文件表';
COMMENT ON COLUMN IDATA_IAM.IAM_FILE_INFO.ID IS '主键';
COMMENT ON COLUMN IDATA_IAM.IAM_FILE_INFO.BIZ_ID IS '业务ID';
COMMENT ON COLUMN IDATA_IAM.IAM_FILE_INFO.BIZ_TYPE IS '业务分类 ;0--用户头像文件；1--首页轮播图）';
COMMENT ON COLUMN IDATA_IAM.IAM_FILE_INFO.FNAME IS '文件名称';
COMMENT ON COLUMN IDATA_IAM.IAM_FILE_INFO.FTYPE IS '文件类型';
COMMENT ON COLUMN IDATA_IAM.IAM_FILE_INFO.FSIZE IS '文件大小';
COMMENT ON COLUMN IDATA_IAM.IAM_FILE_INFO.FURL IS '文件路径';
COMMENT ON COLUMN IDATA_IAM.IAM_FILE_INFO.FILE_BIZ_TYPE IS '文件业务分类';
COMMENT ON COLUMN IDATA_IAM.IAM_FILE_INFO.PREVIEW_URL IS '预览路径';
COMMENT ON COLUMN IDATA_IAM.IAM_FILE_INFO.MD5 IS '文件签名值';
COMMENT ON COLUMN IDATA_IAM.IAM_FILE_INFO.DEL_FLAG IS '删除标识 ;0否1是）';
COMMENT ON COLUMN IDATA_IAM.IAM_FILE_INFO.CREATE_ID IS '创建人';
COMMENT ON COLUMN IDATA_IAM.IAM_FILE_INFO.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN IDATA_IAM.IAM_FILE_INFO.UPDATE_ID IS '更新人';
COMMENT ON COLUMN IDATA_IAM.IAM_FILE_INFO.UPDATE_TIME IS '更新时间';


CREATE TABLE IDATA_IAM.iam_menu(
    id character varying(64) NOT NULL,
    parent_id character varying(64) NOT NULL,
    parent_ids character varying(128),
    app_id character varying(64) NOT NULL,
    name character varying(128) NOT NULL,
    path character varying(255),
    urls TEXT,
    site character varying(255),
    open_type character varying(10),
    type character(1) NOT NULL,
    menu_param TEXT,
    order_index INT NOT NULL,
    icon character varying(128),
    img character varying(255),
    hidden character(1) NOT NULL,
    perms character varying(255),
    create_id character varying(64) NOT NULL,
    create_time timestamp without time zone NOT NULL,
    update_id VARCHAR(64) NOT NULL,
    update_time timestamp without time zone NOT NULL,
    del_flag character(1) NOT NULL,
    code character varying(64),
    PRIMARY KEY (id)
);

COMMENT ON TABLE IDATA_IAM.iam_menu IS '菜单';
COMMENT ON COLUMN IDATA_IAM.iam_menu.id IS '主键';
COMMENT ON COLUMN IDATA_IAM.iam_menu.parent_id IS '父级编号';
COMMENT ON COLUMN IDATA_IAM.iam_menu.parent_ids IS '所有父级编号';
COMMENT ON COLUMN IDATA_IAM.iam_menu.app_id IS '系统应用id';
COMMENT ON COLUMN IDATA_IAM.iam_menu.name IS '名称';
COMMENT ON COLUMN IDATA_IAM.iam_menu.path IS '菜单路径';
COMMENT ON COLUMN IDATA_IAM.iam_menu.urls IS '菜单链接';
COMMENT ON COLUMN IDATA_IAM.iam_menu.site IS '菜单地址;页面物理地址)';
COMMENT ON COLUMN IDATA_IAM.iam_menu.open_type IS '打开方式';
COMMENT ON COLUMN IDATA_IAM.iam_menu.type IS '菜单类型;1顶菜单2左菜单3资源/按钮)';
COMMENT ON COLUMN IDATA_IAM.iam_menu.menu_param IS '菜单参数';
COMMENT ON COLUMN IDATA_IAM.iam_menu.order_index IS '排序';
COMMENT ON COLUMN IDATA_IAM.iam_menu.icon IS '图标';
COMMENT ON COLUMN IDATA_IAM.iam_menu.img IS '图片';
COMMENT ON COLUMN IDATA_IAM.iam_menu.hidden IS '是否显示;0显示1隐藏)';
COMMENT ON COLUMN IDATA_IAM.iam_menu.perms IS '权限标识';
COMMENT ON COLUMN IDATA_IAM.iam_menu.create_id IS '创建人';
COMMENT ON COLUMN IDATA_IAM.iam_menu.create_time IS '创建时间';
COMMENT ON COLUMN IDATA_IAM.iam_menu.update_id IS '更新人';
COMMENT ON COLUMN IDATA_IAM.iam_menu.update_time IS '更新时间';
COMMENT ON COLUMN IDATA_IAM.iam_menu.del_flag IS '删除标识';
COMMENT ON COLUMN IDATA_IAM.iam_menu.code IS '菜单编码;为动态菜单而添加，常存业务ID)';




CREATE TABLE IDATA_IAM.IAM_OFFICE(
    ID character varying(64) NOT NULL,
    PARENT_ID character varying(128) NOT NULL,
    PARENT_IDS character varying(1024),
    NAME character varying(255) NOT NULL,
    CODE character varying(255) NOT NULL,
    PATHS TEXT NOT NULL,
    ORDER_INDEX INT NOT NULL,
    APP_ID character varying(64),
    DISTRICT_ID character varying(32) NOT NULL,
    OTYPE character varying(32) NOT NULL,
    LEVELS character varying(32) NOT NULL,
    ADDRESS character varying(128),
    SUPERVISOR character varying(64),
    PHONE character varying(64),
    FAX character varying(64),
    EMAIL character varying(128),
    USEABLE character varying(128) NOT NULL,
    DEL_FLAG character(1) NOT NULL,
    CREATE_ID character varying(64) NOT NULL,
    CREATE_TIME timestamp without time zone NOT NULL,
    UPDATE_ID character varying(64) NOT NULL,
    UPDATE_TIME timestamp without time zone NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE IDATA_IAM.IAM_OFFICE IS '机构部门';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.ID IS '主键';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.PARENT_ID IS '父级编号';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.PARENT_IDS IS '所有父级编号';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.NAME IS '机构名称';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.CODE IS '机构编码';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.PATHS IS '全路径名称';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.ORDER_INDEX IS '排序';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.APP_ID IS '系统编码';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.DISTRICT_ID IS '行政区划';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.OTYPE IS '机构类型';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.LEVELS IS '机构级别';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.ADDRESS IS '地址';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.SUPERVISOR IS '负责人';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.PHONE IS '电话';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.FAX IS '传真';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.EMAIL IS '邮箱';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.USEABLE IS '是否启用';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.DEL_FLAG IS '删除标识';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.CREATE_ID IS '创建人';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.UPDATE_ID IS '更新人';
COMMENT ON COLUMN IDATA_IAM.IAM_OFFICE.UPDATE_TIME IS '更新时间';



CREATE TABLE IDATA_IAM.IAM_PROBLEM_FEEDBACK(
    ID character varying(64) NOT NULL,
    OPINION_TITLE character varying(255),
    ISSUE_SYSTEM character varying(100),
    ISSUE_DETAILS character varying(1000),
    STATE character varying(1),
    DEL_FLAG character(1) NOT NULL,
    CREATE_TIME timestamp without time zone NOT NULL,
    CREATE_ID character varying(64) NOT NULL,
    UPDATE_TIME timestamp without time zone NOT NULL,
    UPDATE_ID character varying(64) NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE IDATA_IAM.IAM_PROBLEM_FEEDBACK IS '问题反馈表';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_FEEDBACK.ID IS '主键';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_FEEDBACK.OPINION_TITLE IS '意见标题';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_FEEDBACK.ISSUE_SYSTEM IS '问题系统';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_FEEDBACK.ISSUE_DETAILS IS '问题详情';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_FEEDBACK.STATE IS '任务状态';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_FEEDBACK.DEL_FLAG IS '是否删除 ;0否1是)';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_FEEDBACK.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_FEEDBACK.CREATE_ID IS '创建ID';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_FEEDBACK.UPDATE_TIME IS '修改时间';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_FEEDBACK.UPDATE_ID IS '修改ID';



CREATE TABLE IDATA_IAM.IAM_PROBLEM_PHOTO(
    ID character varying(64) NOT NULL,
    GROUP_NAME character varying(255),
    FILE_ID character varying(100),
    ISSUE_ID character varying(64),
    DEL_FLAG character(1) NOT NULL,
    CREATE_TIME timestamp without time zone NOT NULL,
    CREATE_ID character varying(64) NOT NULL,
    UPDATE_TIME timestamp without time zone NOT NULL,
    UPDATE_ID character varying(64) NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE IDATA_IAM.IAM_PROBLEM_PHOTO IS '问题反馈图片关联表';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_PHOTO.ID IS '主键';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_PHOTO.GROUP_NAME IS '组名';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_PHOTO.FILE_ID IS '文件ID';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_PHOTO.ISSUE_ID IS '问题反馈ID';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_PHOTO.DEL_FLAG IS '是否删除 ;0否1是)';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_PHOTO.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_PHOTO.CREATE_ID IS '创建ID';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_PHOTO.UPDATE_TIME IS '修改时间';
COMMENT ON COLUMN IDATA_IAM.IAM_PROBLEM_PHOTO.UPDATE_ID IS '修改ID';



CREATE TABLE IDATA_IAM.IAM_ROLE(
    ID character varying(64) NOT NULL,
    APP_ID character varying(64) NOT NULL,
    ROLE_NAME character varying(128) NOT NULL,
    ROLE_CODE character varying(128) NOT NULL,
    ROLE_TYPE character varying(128) NOT NULL,
    USEABLE character(1) NOT NULL,
    DEL_FLAG character(1) NOT NULL,
    CREATE_ID character varying(64) NOT NULL,
    CREATE_TIME timestamp without time zone NOT NULL,
    UPDATE_ID character varying(64) NOT NULL,
    UPDATE_TIME timestamp without time zone NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE IDATA_IAM.IAM_ROLE IS '系统应用角色';
COMMENT ON COLUMN IDATA_IAM.IAM_ROLE.ID IS '主键';
COMMENT ON COLUMN IDATA_IAM.IAM_ROLE.APP_ID IS '系统应用ID';
COMMENT ON COLUMN IDATA_IAM.IAM_ROLE.ROLE_NAME IS '角色名称';
COMMENT ON COLUMN IDATA_IAM.IAM_ROLE.ROLE_CODE IS '角色编码';
COMMENT ON COLUMN IDATA_IAM.IAM_ROLE.ROLE_TYPE IS '角色类型;1内置2自定义)';
COMMENT ON COLUMN IDATA_IAM.IAM_ROLE.USEABLE IS '是否可用';
COMMENT ON COLUMN IDATA_IAM.IAM_ROLE.DEL_FLAG IS '删除标识';
COMMENT ON COLUMN IDATA_IAM.IAM_ROLE.CREATE_ID IS '创建人';
COMMENT ON COLUMN IDATA_IAM.IAM_ROLE.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN IDATA_IAM.IAM_ROLE.UPDATE_ID IS '更新人';
COMMENT ON COLUMN IDATA_IAM.IAM_ROLE.UPDATE_TIME IS '更新时间';



CREATE TABLE IDATA_IAM.IAM_ROLE_MENU(
    APP_ID character varying(64) NOT NULL,
    ROLE_ID character varying(64) NOT NULL,
    MENU_ID character varying(64) NOT NULL,
    PRIMARY KEY (APP_ID,ROLE_ID,MENU_ID)
);

COMMENT ON TABLE IDATA_IAM.IAM_ROLE_MENU IS '角色菜单';
COMMENT ON COLUMN IDATA_IAM.IAM_ROLE_MENU.APP_ID IS '系统ID';
COMMENT ON COLUMN IDATA_IAM.IAM_ROLE_MENU.ROLE_ID IS '角色ID';
COMMENT ON COLUMN IDATA_IAM.IAM_ROLE_MENU.MENU_ID IS '菜单ID';

CREATE TABLE IDATA_IAM.IAM_ROLE_USER_CODE(
    APP_ID character varying(64) NOT NULL,
    ROLE_ID character varying(64) NOT NULL,
    USER_CODE_ID character varying(64) NOT NULL
);

COMMENT ON TABLE IDATA_IAM.IAM_ROLE_USER_CODE IS '角色账号';
COMMENT ON COLUMN IDATA_IAM.IAM_ROLE_USER_CODE.APP_ID IS '系统ID';
COMMENT ON COLUMN IDATA_IAM.IAM_ROLE_USER_CODE.ROLE_ID IS '角色编码';
COMMENT ON COLUMN IDATA_IAM.IAM_ROLE_USER_CODE.USER_CODE_ID IS '账号编码';



CREATE TABLE IDATA_IAM.IAM_STD_CODE(
    ID character varying(64) NOT NULL,
    CODE_NAME character varying(100) NOT NULL,
    CODE_VALUE character varying(100) NOT NULL,
    STD_TYPE character varying(64) NOT NULL,
    USEABLE character(1) NOT NULL DEFAULT  ('0'),
    REMARK character varying(500),
    ORDER_INDEX character varying(255) NOT NULL DEFAULT  (1),
    DEL_FLAG character(1) NOT NULL DEFAULT  ('0'),
    CREATE_TIME timestamp without time zone NOT NULL,
    CREATE_ID character varying(64) NOT NULL,
    UPDATE_TIME timestamp without time zone NOT NULL,
    UPDATE_ID character varying(64) NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE IDATA_IAM.IAM_STD_CODE IS '码表编码';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_CODE.ID IS '主键';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_CODE.CODE_NAME IS '名称';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_CODE.CODE_VALUE IS '编码';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_CODE.STD_TYPE IS '所属码表';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_CODE.USEABLE IS '状态 ;0正常1停用)';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_CODE.REMARK IS '备注';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_CODE.ORDER_INDEX IS '排序';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_CODE.DEL_FLAG IS '是否删除 ;0否1是)';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_CODE.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_CODE.CREATE_ID IS '创建ID';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_CODE.UPDATE_TIME IS '修改时间';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_CODE.UPDATE_ID IS '修改ID';



CREATE TABLE IDATA_IAM.IAM_STD_TYPE(
    ID character varying(64) NOT NULL,
    STD_NUM character varying(100) NOT NULL,
    STD_NAME character varying(100) NOT NULL,
    STD_TYPE character varying(64) NOT NULL DEFAULT  (3),
    USEABLE character(1) NOT NULL DEFAULT  ('0'),
    REMARK character varying(500),
    ORDER_INDEX INT NOT NULL DEFAULT  (1),
    DEL_FLAG character(1) NOT NULL DEFAULT  ('0'),
    CREATE_TIME timestamp without time zone NOT NULL,
    CREATE_ID character varying(64) NOT NULL,
    UPDATE_TIME timestamp without time zone NOT NULL,
    UPDATE_ID character varying(64) NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE IDATA_IAM.IAM_STD_TYPE IS '码表类型';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_TYPE.ID IS '主键';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_TYPE.STD_NUM IS '字典编号';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_TYPE.STD_NAME IS '字典名称';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_TYPE.STD_TYPE IS '字典类型 ;0内置 1自定义)';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_TYPE.USEABLE IS '状态 ;0正常1停用)';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_TYPE.REMARK IS '备注';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_TYPE.ORDER_INDEX IS '排序';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_TYPE.DEL_FLAG IS '是否删除 ;0否1是)';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_TYPE.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_TYPE.CREATE_ID IS '创建ID';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_TYPE.UPDATE_TIME IS '修改时间';
COMMENT ON COLUMN IDATA_IAM.IAM_STD_TYPE.UPDATE_ID IS '修改ID';



CREATE TABLE IDATA_IAM.IAM_SYS_LOG(
    ID character varying(64) NOT NULL,
    APP_CODE character varying(64),
    MODULE character varying(255),
    LOG_TYPE character(1),
    REMOTE_IP character varying(128),
    OPERATE_TYPE character varying(255),
    OPERATOR character varying(255),
    BROWSER character varying(64) NOT NULL,
    OS character varying(64) NOT NULL,
    INFOS TEXT,
    PARAMS TEXT,
    RESULT TEXT,
    ERR_MSG TEXT,
    DEL_FLAG character(1) NOT NULL,
    CREATE_ID character varying(64) NOT NULL,
    CREATE_TIME timestamp without time zone NOT NULL,
    UPDATE_ID character varying(64) NOT NULL,
    UPDATE_TIME timestamp without time zone NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE IDATA_IAM.IAM_SYS_LOG IS '系统日志';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOG.ID IS '主键';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOG.APP_CODE IS '应用编码';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOG.MODULE IS '模块';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOG.LOG_TYPE IS '日志类型;1普通2系统3安全4审计)';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOG.REMOTE_IP IS '操作IP地址';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOG.OPERATE_TYPE IS '操作类型';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOG.OPERATOR IS '操作人';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOG.BROWSER IS '浏览器';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOG.OS IS '操作系统';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOG.INFOS IS '操作明细';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOG.PARAMS IS '请求参数';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOG.RESULT IS '响应参数';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOG.ERR_MSG IS '异常信息';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOG.DEL_FLAG IS '删除标识';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOG.CREATE_ID IS '创建人';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOG.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOG.UPDATE_ID IS '更新人';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOG.UPDATE_TIME IS '更新时间';


CREATE TABLE IDATA_IAM.IAM_SYS_LOGIN_LOG(
    ID character varying(64) NOT NULL,
    LOGIN_NAME character varying(64) NOT NULL,
    REMOTE_IP character varying(128) NOT NULL,
    BROWSER character varying(64) NOT NULL,
    OS character varying(64) NOT NULL,
    STATUS character varying(64) NOT NULL,
    MSG TEXT NOT NULL,
    DEL_FLAG character(1) NOT NULL,
    CREATE_ID character varying(64) NOT NULL,
    CREATE_TIME timestamp without time zone NOT NULL,
    UPDATE_ID character varying(64) NOT NULL,
    UPDATE_TIME timestamp without time zone NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE IDATA_IAM.IAM_SYS_LOGIN_LOG IS '登录日志';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOGIN_LOG.ID IS '主键';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOGIN_LOG.LOGIN_NAME IS '账号';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOGIN_LOG.REMOTE_IP IS '操作IP地址';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOGIN_LOG.BROWSER IS '浏览器';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOGIN_LOG.OS IS '操作系统';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOGIN_LOG.STATUS IS '状态';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOGIN_LOG.MSG IS '提示';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOGIN_LOG.DEL_FLAG IS '删除标识';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOGIN_LOG.CREATE_ID IS '创建人';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOGIN_LOG.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOGIN_LOG.UPDATE_ID IS '更新人';
COMMENT ON COLUMN IDATA_IAM.IAM_SYS_LOGIN_LOG.UPDATE_TIME IS '更新时间';



CREATE TABLE IDATA_IAM.IAM_USER(
    ID character varying(64) NOT NULL,
    NAME character varying(255) NOT NULL,
    SEX character(1) NOT NULL,
    AVATAR character varying(255),
    NATION character varying(32),
    BIRTHDAY character varying(32),
    ID_CARD character varying(18),
    PHONE character varying(32),
    OTHER_CONTACT character varying(32),
    SECIRITY_FLAG character(1) NOT NULL,
    OFFICE_ID character varying(32) NOT NULL,
    USER_POST character varying(32),
    USER_LEVEL character varying(32),
    BUDGETED character varying(32),
    BUDGETED_POST character varying(32),
    DEL_FLAG character(1) NOT NULL,
    CREATE_ID character varying(64) NOT NULL,
    CREATE_TIME timestamp without time zone NOT NULL,
    UPDATE_ID character varying(64) NOT NULL,
    UPDATE_TIME timestamp without time zone NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE IDATA_IAM.IAM_USER IS '用户信息';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.ID IS '主键';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.NAME IS '姓名';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.SEX IS '性别';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.AVATAR IS '头像';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.NATION IS '民族';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.BIRTHDAY IS '出生年月';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.ID_CARD IS '身份证号';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.PHONE IS '手机号码';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.OTHER_CONTACT IS '其他联系方式';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.SECIRITY_FLAG IS '密级标识 ;0核心涉密人员1重要涉密人员2一般涉密人员3非涉密人员)';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.OFFICE_ID IS '部门';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.USER_POST IS '职务';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.USER_LEVEL IS '级别';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.BUDGETED IS '编制';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.BUDGETED_POST IS '编制职务';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.DEL_FLAG IS '删除标识';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.CREATE_ID IS '创建人';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.UPDATE_ID IS '更新人';
COMMENT ON COLUMN IDATA_IAM.IAM_USER.UPDATE_TIME IS '更新时间';



CREATE TABLE IDATA_IAM.IAM_USER_CODE(
    ID character varying(64) NOT NULL,
    USER_ID character varying(64),
    LOGIN_NAME character varying(32) NOT NULL,
    PASSWD character varying(255) NOT NULL,
    SALT CHAR(6) NOT NULL,
    TYPE character varying(10),
    STATUS character(1) NOT NULL,
    PWD_UPDATE_TIME DATE,
    DEL_FLAG character(1) NOT NULL,
    CREATE_ID character varying(64) NOT NULL,
    CREATE_TIME timestamp without time zone NOT NULL,
    UPDATE_ID character varying(64) NOT NULL,
    UPDATE_TIME timestamp without time zone NOT NULL,
    DEFAULT_ROLES character varying(3072),
    PRIMARY KEY (ID)
);

COMMENT ON TABLE IDATA_IAM.IAM_USER_CODE IS '用户账号';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_CODE.ID IS '主键';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_CODE.USER_ID IS '用户ID';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_CODE.LOGIN_NAME IS '登录名';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_CODE.PASSWD IS '密码';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_CODE.SALT IS '盐值';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_CODE.TYPE IS '账号类型';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_CODE.STATUS IS '状态 ;0正常1锁定2禁用)';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_CODE.PWD_UPDATE_TIME IS '密码修改时间';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_CODE.DEL_FLAG IS '删除标识';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_CODE.CREATE_ID IS '创建人';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_CODE.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_CODE.UPDATE_ID IS '更新人';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_CODE.UPDATE_TIME IS '更新时间';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_CODE.DEFAULT_ROLES IS '各应用默认角色';

CREATE TABLE IDATA_IAM.IAM_USER_EXPERIENCE(
    ID character varying(64) NOT NULL,
    USER_ID character varying(64) NOT NULL,
    EDUCATION character varying(32),
    MAJOR character varying(32),
    JOB_DATE character varying(32),
    JOB_EXPERIENCE TEXT,
    POST_EXPERIENCE TEXT,
    REMARK character varying(255),
    DEL_FLAG character(1) NOT NULL,
    CREATE_ID character varying(64) NOT NULL,
    CREATE_TIME timestamp without time zone NOT NULL,
    UPDATE_ID character varying(64) NOT NULL,
    UPDATE_TIME timestamp without time zone NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE IDATA_IAM.IAM_USER_EXPERIENCE IS '用户教育工作经历';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_EXPERIENCE.ID IS '主键';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_EXPERIENCE.USER_ID IS '用户ID';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_EXPERIENCE.EDUCATION IS '学历';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_EXPERIENCE.MAJOR IS '专业';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_EXPERIENCE.JOB_DATE IS '参加工作时间';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_EXPERIENCE.JOB_EXPERIENCE IS '工作经验';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_EXPERIENCE.POST_EXPERIENCE IS '任职经历';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_EXPERIENCE.REMARK IS '备注';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_EXPERIENCE.DEL_FLAG IS '删除标识';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_EXPERIENCE.CREATE_ID IS '创建人';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_EXPERIENCE.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_EXPERIENCE.UPDATE_ID IS '更新人';
COMMENT ON COLUMN IDATA_IAM.IAM_USER_EXPERIENCE.UPDATE_TIME IS '更新时间';

