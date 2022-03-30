
-- 基础表结构
--Table Comment
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for iam_app
-- ----------------------------
DROP TABLE IF EXISTS `iam_app`;
CREATE TABLE `iam_app`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `app_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '系统应用名字',
  `app_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '系统应用编码',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `img` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用图片',
  `order_index` int(11) NOT NULL DEFAULT 1 COMMENT '排序',
  `app_key` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '系统应用公钥(账号)',
  `app_secret` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '系统应用私钥(密码)',
  `open_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '打开方式(0默认 1跳转)',
  `useable` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '是否生效',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '删除标识',
  `create_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统应用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iam_config
-- ----------------------------
DROP TABLE IF EXISTS `iam_config`;
CREATE TABLE `iam_config`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '主键',
  `ctype` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '配置类型（0：系统配置）',
  `ccode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置项编码（0：密码过期时间，1：ip配置）',
  `citem` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置项（配置中文名称）',
  `cvalue` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置项值',
  `order_index` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '1' COMMENT '排序',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '是否删除 (0否1是)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建id',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '修改id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iam_download
-- ----------------------------
DROP TABLE IF EXISTS `iam_download`;
CREATE TABLE `iam_download`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `app_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '系统应用编码',
  `task_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务主键',
  `task_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务名称 用于与任务对应',
  `dtype` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '下载类型',
  `fpath` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '存放全路径 要下载的文件存放路径',
  `md5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '文件较验值',
  `fsize` bigint(20) NULL DEFAULT NULL COMMENT '文件大小',
  `status` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '下载状态 0-未开始 1-进行中 2-成功 3-失败',
  `create_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '删除标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '下载中心' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for iam_menu
-- ----------------------------
DROP TABLE IF EXISTS `iam_menu`;
CREATE TABLE `iam_menu`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `parent_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所有父级编号',
  `app_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统应用id',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `urls` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '菜单链接',
  `site` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单地址(页面物理地址)',
  `open_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '打开方式',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单类型(1顶菜单2左菜单3资源/按钮)',
  `menu_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '菜单参数',
  `order_index` int(11) NOT NULL COMMENT '排序',
  `icon` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片',
  `hidden` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否显示(0显示1隐藏)',
  `perms` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `create_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '删除标识',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单编码(为动态菜单而添加，常存业务ID)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iam_office
-- ----------------------------
DROP TABLE IF EXISTS `iam_office`;
CREATE TABLE `iam_office`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `parent_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所有父级编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '机构名称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '机构编码',
  `paths` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '全路径名称',
  `order_index` int(10) NOT NULL COMMENT '排序',
  `app_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '系统编码',
  `district_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '行政区划',
  `otype` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '机构类型',
  `levels` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '机构级别',
  `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '地址',
  `supervisor` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '电话',
  `fax` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '传真',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱',
  `useable` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '是否启用',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '删除标识',
  `create_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '机构部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iam_problem_feedback
-- ----------------------------
DROP TABLE IF EXISTS `iam_problem_feedback`;
CREATE TABLE `iam_problem_feedback`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `opinion_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '意见标题',
  `issue_system` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '问题系统',
  `issue_details` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '问题详情',
  `state` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '任务状态',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '是否删除 (0否1是)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建id',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '修改id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '问题反馈表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iam_problem_photo
-- ----------------------------
DROP TABLE IF EXISTS `iam_problem_photo`;
CREATE TABLE `iam_problem_photo`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '组名',
  `file_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '文件id',
  `issue_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '问题反馈id',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '是否删除 (0否1是)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建id',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '修改id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '问题反馈图片关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iam_role
-- ----------------------------
DROP TABLE IF EXISTS `iam_role`;
CREATE TABLE `iam_role`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `app_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '系统应用id',
  `role_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
  `role_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色编码',
  `role_type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色类型(1内置2自定义)',
  `useable` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '是否可用',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '删除标识',
  `create_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统应用角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iam_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `iam_role_menu`;
CREATE TABLE `iam_role_menu`  (
  `app_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '系统id',
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色id',
  `menu_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`app_id`, `role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iam_role_user_code
-- ----------------------------
DROP TABLE IF EXISTS `iam_role_user_code`;
CREATE TABLE `iam_role_user_code`  (
  `app_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '系统id',
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色编码',
  `user_code_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '账号编码'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色账号' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iam_std_code
-- ----------------------------
DROP TABLE IF EXISTS `iam_std_code`;
CREATE TABLE `iam_std_code`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `code_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
  `code_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '编码',
  `std_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '所属码表',
  `useable` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '状态 (0正常1停用)',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `order_index` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '1' COMMENT '排序',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '是否删除 (0否1是)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建id',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '修改id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '码表编码' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iam_std_type
-- ----------------------------
DROP TABLE IF EXISTS `iam_std_type`;
CREATE TABLE `iam_std_type`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `std_num` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '字典编号',
  `std_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '字典名称',
  `std_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '3' COMMENT '字典类型 (0内置 1自定义)',
  `useable` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '状态 (0正常1停用)',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `order_index` int(11) NOT NULL DEFAULT 1 COMMENT '排序',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '是否删除 (0否1是)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建id',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '修改id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '码表类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iam_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `iam_sys_log`;
CREATE TABLE `iam_sys_log`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `app_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用编码',
  `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '模块',
  `log_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '日志类型(1普通2系统3安全4审计)',
  `remote_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '操作ip地址',
  `operate_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '操作类型',
  `operator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '操作人',
  `browser` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '浏览器',
  `os` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '操作系统',
  `infos` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '操作明细',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '请求参数',
  `result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '响应参数',
  `err_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '异常信息',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '删除标识',
  `create_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iam_sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `iam_sys_login_log`;
CREATE TABLE `iam_sys_login_log`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `login_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '账号',
  `remote_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '操作ip地址',
  `browser` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '浏览器',
  `os` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '操作系统',
  `status` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '状态',
  `msg` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '提示',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '删除标识',
  `create_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '登录日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iam_user
-- ----------------------------
DROP TABLE IF EXISTS `iam_user`;
CREATE TABLE `iam_user`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '姓名',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '性别',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像',
  `nation` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '民族',
  `birthday` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '出生年月',
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手机号码',
  `other_contact` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '其他联系方式',
  `secirity_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密级标识 (0核心涉密人员1重要涉密人员2一般涉密人员3非涉密人员)',
  `office_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '部门',
  `user_post` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '职务',
  `user_level` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '级别',
  `budgeted` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '编制',
  `budgeted_post` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '编制职务',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '删除标识',
  `create_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iam_user_code
-- ----------------------------
DROP TABLE IF EXISTS `iam_user_code`;
CREATE TABLE `iam_user_code`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户id',
  `login_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '登录名',
  `passwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `salt` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '盐值',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '账号类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '状态 (0正常1锁定2禁用)',
  `pwd_update_time` datetime(0) NULL DEFAULT NULL COMMENT '密码修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '删除标识',
  `create_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `default_roles` varchar(3072) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '各应用默认角色',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户账号' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iam_file_info
-- ----------------------------
DROP TABLE IF EXISTS `iam_file_info`;
CREATE TABLE `iam_file_info`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `biz_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务id',
  `biz_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务分类 （0--用户头像文件；1--首页轮播图）',
  `fname` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '文件名称',
  `ftype` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '文件类型',
  `fsize` bigint(20) NOT NULL COMMENT '文件大小',
  `furl` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '文件路径',
  `file_biz_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '文件业务分类',
  `preview_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '预览路径',
  `md5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '文件签名值',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '删除标识 （0否1是）',
  `create_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '整改文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iam_user_experience
-- ----------------------------
DROP TABLE IF EXISTS `iam_user_experience`;
CREATE TABLE `iam_user_experience`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户id',
  `education` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '学历',
  `major` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '专业',
  `job_date` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '参加工作时间',
  `job_experience` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '工作经验',
  `post_experience` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '任职经历',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '删除标识',
  `create_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户教育工作经历' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


-- 基础数据

-- 字典
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('103bf293a179b1b5558b7328493a294f', 'orgType', '机构类型', '0', '0', '', 2, '0', '2020-06-19 10:21:36', '', '2020-06-19 10:21:36', '');
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('1cfc76555f0afd0ea0865a8aa8ff9727', 'accessWay', '接入方式', '1', '0', '', 19, '0', '2021-11-23 10:52:02', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-11-23 10:52:02', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('1e993921ebdf9153cdc2bffe84b18530', 'roleType', '角色类型', '0', '0', '', 3, '0', '2020-06-19 10:22:31', '', '2020-06-19 11:17:28', '');
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('26fa694f68c726d05a00061fcc373af5', 'industry', '所属行业', '1', '0', '', 18, '0', '2021-11-23 10:51:46', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-11-23 10:51:46', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('31daa0b700fde1a776b961b4c0fc7178', 'effectType', '成效类型', '1', '0', '', 13, '0', '2020-12-12 21:00:44', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-12 21:00:44', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('3aabbed9d485fe50f2d4dc59333af70f', 'menuOpenWay', '菜单打开方式', '0', '0', '', 1, '0', '2020-06-28 15:54:13', '', '2020-06-28 15:54:13', '');
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('4c3fe2683d97446caa152694269403f3', 'xsType', '巡视方式', '0', '0', '巡视方式', 12, '0', '2020-12-11 21:02:47', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-11 21:02:47', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('6ad3141b16cd8fea8e218cedf33c7b93', 'finishStatus', '完成情况', '0', '0', '', 9, '0', '2020-12-11 20:33:03', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-11 20:33:03', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('717d87347d73dc0a46ed0c182e2e81ad', 'meetType', '会议类别', '0', '0', '', 14, '0', '2020-12-15 19:28:03', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-15 19:28:03', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('739877de4ca6443be9791568da601d9d', 'thickGrade', '涉密级别', '0', '0', '', 4, '0', '2020-06-19 10:23:45', '', '2020-06-20 13:51:49', '');
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('7bc748b8addf20c3dea2baf11165f629', 'rectType', '整改时限', '0', '0', '整改时限', 10, '0', '2020-12-11 20:32:26', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-11 20:32:26', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('8b1b0444890764665484f3a10aa8599f', 'department', '来源部门', '1', '0', '', 17, '0', '2021-11-23 10:50:38', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-11-23 10:50:50', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('94a07bf04c731a65c25a29996d1b5534', 'position', '岗位', '0', '0', '', 5, '0', '2020-06-19 11:10:25', '', '2020-06-19 11:10:25', '');
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('c700e518a5a783efc86ca317bd951ec7', 'accountType', '账户类型', '0', '0', '', 6, '0', '2020-06-28 16:39:58', '', '2020-06-28 16:39:58', '');
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('cebfa7617bc405dc872cf3ef748bd5a0', 'issueSystemType', '问题反馈系统', '0', '0', '', 6, '0', '2020-06-22 14:36:00', '', '2020-06-22 14:36:00', '');
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('d7f6c6b1b46fb77b9ce506974ce65608', 'pType', '问题类别', '0', '0', '问题类别', 8, '0', '2020-12-11 20:31:50', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-11 20:31:50', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('f4fdf42410025898feac23951b517ad1', 'orgRank', '机构级别', '0', '0', '', 1, '0', '2020-06-19 10:21:09', '', '2020-06-19 10:21:09', '');
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('f4fdf42410025898feac23951b517ad2', 'databaseType', '数据库类型', '0', '0', '', 15, '0', '2020-06-19 10:21:09', '', '2020-06-19 10:21:09', '');
INSERT INTO `iam_std_type`(`id`, `std_num`, `std_name`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('f4fdf42410025898feac23951b517ad3', 'funcType', '函数类型', '0', '0', '', 16, '0', '2020-06-19 10:21:09', '', '2020-06-19 10:21:09', '');


-- 字典项
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('01ce91b4dbbd61b2d65d44460256daf8', '民政厅', '1', 'department', '0', '', '7', '0', '2021-11-23 10:53:31', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-11-23 10:53:31', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('03973ea3b92588ac38314407a1680bd6', 'SQL Server', '3', 'databaseType', '0', 'SQL Server', '3', '0', '2020-07-28 20:12:36', 'a94a3fffb84dad12b42af19605d35a09', '2020-07-28 20:12:36', 'a94a3fffb84dad12b42af19605d35a09');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('0566f12af8282aa1b3a87d51b22cce49', '单位', '4', 'orgType', '0', '单位', '4', '0', '2021-02-05 14:18:10', '8895a0d44a3b63e40ffc63bad6a6ef1b', '2021-02-05 14:18:10', '8895a0d44a3b63e40ffc63bad6a6ef1b');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('07c608a578b91e72d2f5c5ed0f3dd50a', '党的领导弱化方面', '2', 'pType', '0', '', '2', '0', '2020-12-12 14:51:25', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-12 14:51:25', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('0df609dc9773fcc5806ee249a5524aa7', '一般涉密人员', '3', 'thickGrade', '0', '', '3', '0', '2020-06-19 19:13:29', '', '2020-06-19 19:13:29', '');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('11f1f603f45270715052282ac980b28e', '巡视办', '1', 'orgType', '0', '', '1', '0', '2020-12-14 14:42:38', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-14 14:42:38', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('12d5a83ce7f0a318f8c5840c0f8c8595', '基础信息管理平台', '1', 'issueSystemType', '0', '', '1', '0', '2020-06-22 14:38:57', '', '2020-07-29 21:42:04', 'c2415bd24a5a93637de572f698f6eba3');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('1311139ca019ae01cbb773f089e8ec61', '遵守政治纪律和政治规矩方面', '4', 'pType', '0', '', '4', '0', '2020-12-12 14:49:26', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-12 14:49:26', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('1c87d15fedef331e70c4af05fa809abb', '副科长', '6', 'position', '0', '', '6', '0', '2020-08-08 14:47:32', 'ae8ef659d56dc5415cdab94dea7c6a05', '2020-08-08 14:47:32', 'ae8ef659d56dc5415cdab94dea7c6a05');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('20d3ad288b268917925c55f84216a0d7', '数据函数', '3', 'funcType', '0', '', '4', '0', '2021-03-11 11:08:13', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-03-11 11:08:13', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('26708ae5e5a39c0f441369c82366f191', '协查', '4', 'accessWay', '0', '', '4', '0', '2021-11-23 10:56:02', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-11-23 10:56:02', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('29837403940859574e05dde4fb11722a', 'MySQL', '1', 'databaseType', '0', 'MySQL', '1', '0', '2020-07-28 19:15:59', 'a94a3fffb84dad12b42af19605d35a09', '2020-07-28 19:16:14', 'a94a3fffb84dad12b42af19605d35a09');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('2af9e009040795f3eaaeb80d800026f7', '二级机构', '2', 'orgRank', '0', '', '2', '0', '2020-06-22 14:48:29', '', '2020-06-22 14:48:29', '');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('2caf5af60dd8fb16b0a29ef8109a0058', 'PostgreSql', '4', 'databaseType', '0', 'PostgreSQL', '4', '0', '2020-07-28 20:13:32', 'a94a3fffb84dad12b42af19605d35a09', '2020-08-07 09:58:41', 'ae8ef659d56dc5415cdab94dea7c6a05');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('3043be5cbddb19a3fc6e58035188f317', '被巡单位', '4', 'accountType', '0', '', '4', '0', '2020-06-28 16:40:43', '', '2020-06-28 16:40:43', '');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('3639b5b172b508b07b93ee8b3eea7dc0', '整改专题民主生活会议', '2', 'meetType', '0', '', '2', '0', '2020-12-15 19:29:37', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-15 19:29:37', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('37ee9d062f445ed58cf6bac0ef7b8dea', '非涉密人员', '4', 'thickGrade', '0', '', '4', '0', '2020-06-19 19:13:48', '', '2020-06-19 19:13:48', '');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('3c631a82cbdc1883d3246f3cf794f7d5', '部门', '5', 'orgType', '0', '部门', '5', '0', '2021-02-05 14:18:10', '8895a0d44a3b63e40ffc63bad6a6ef1b', '2021-02-05 14:18:10', '8895a0d44a3b63e40ffc63bad6a6ef1b');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('3cb8e7f952c8ae0a6dedf26e4a5baf66', '立行整改', '1', 'rectType', '0', '', '1', '0', '2020-12-11 20:42:59', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-11 20:42:59', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('3dc6ebd3d5c1873931738170f0ae8e19', '副组长', '2', 'position', '0', '', '2', '0', '2020-08-08 14:46:29', 'ae8ef659d56dc5415cdab94dea7c6a05', '2020-08-08 14:46:29', 'ae8ef659d56dc5415cdab94dea7c6a05');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('3fbbfb39ebc0e414c6aab1c5dbe1c106', 'Oracle', '2', 'databaseType', '0', 'Oracle', '2', '0', '2020-07-28 20:12:00', 'a94a3fffb84dad12b42af19605d35a09', '2020-07-28 20:12:00', 'a94a3fffb84dad12b42af19605d35a09');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('41dfb3b1cf9b821734e4cd57ba9f070b', '未完成', '3', 'finishStatus', '0', '', '3', '0', '2020-12-11 20:42:18', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-11 20:42:18', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('44c023fe8ec5ddc413aba03dc8fe53c7', '字符串函数', '2', 'funcType', '0', '', '3', '0', '2021-03-11 11:07:48', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-03-11 11:07:48', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('45628d8eb2d2a6124a354c47382e8ef1', '专项巡察', '3', 'xsType', '0', '', '3', '0', '2020-12-11 21:03:12', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-11 21:03:12', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('45628d8fgj55htj6124a354c47382e8ef1', '机动式巡察', '4', 'xsType', '0', '', '4', '0', '2020-12-11 21:03:12', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-11 21:03:12', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('4669860a988caf163221d776fd3815b4', '移交单位', '5', 'accountType', '0', '', '5', '0', '2020-07-02 15:36:29', 'c2415bd24a5a93637de572f698f6eba3', '2020-07-02 15:36:29', 'c2415bd24a5a93637de572f698f6eba3');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('472007764cfae7cff5e06a9f02eb32f1', '法律', '3', 'industry', '0', '', '3', '0', '2021-11-23 10:55:00', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-11-23 10:55:00', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('485d6c940b901bc2094bc9d9a09ac9c2', '副处长', '8', 'position', '0', '', '8', '0', '2020-08-08 14:48:09', 'ae8ef659d56dc5415cdab94dea7c6a05', '2020-08-08 14:48:09', 'ae8ef659d56dc5415cdab94dea7c6a05');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('48d35f750e5b082bcb141956a48a8f1c', '党委研究整改工作会议', '1', 'meetType', '0', '', '1', '0', '2020-12-15 19:29:07', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-15 19:29:07', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('4f4235c9392f17f860a4941f5af3c469', '组长', '1', 'position', '0', '', '1', '0', '2020-08-08 14:46:11', 'ae8ef659d56dc5415cdab94dea7c6a05', '2020-08-08 14:46:11', 'ae8ef659d56dc5415cdab94dea7c6a05');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('5007c9beed25bb8c46653162d2f7fa70', '常规巡视', '1', 'xsType', '0', '', '1', '0', '2020-12-11 21:03:04', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-11 21:03:04', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('513ad798ef8c680c6da8fe0778f826d9', '默认', '1', 'menuOpenWay', '0', '', '1', '0', '2020-06-28 16:03:09', '', '2021-08-02 14:56:53', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('52dd8c71a3353695d85248020057e3c9', '巡视办', '7', 'accountType', '0', '巡视办账号', '7', '0', '2021-04-06 17:21:33', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-04-06 17:21:33', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('556d66e614cd1540b9ac9082fe9c6ee5', '巡视组', '2', 'accountType', '0', '', '2', '0', '2020-06-28 16:40:19', '', '2020-06-28 16:40:19', '');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('55d4a980a693a89ac0f736faef6eb617', '巡察专员', '4', 'position', '0', '', '4', '0', '2020-08-08 14:46:50', 'ae8ef659d56dc5415cdab94dea7c6a05', '2020-08-08 14:46:50', 'ae8ef659d56dc5415cdab94dea7c6a05');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('594c0ee5f245d67454d5441e7e5005d6', '社保局', '4', 'department', '0', '', '4', '0', '2021-11-23 10:54:17', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-11-23 10:54:17', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('5a166d6c63c80ecd8f4e1158da9a76dd', '聚合函数', '0', 'funcType', '0', '', '1', '0', '2021-03-11 11:07:23', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-03-11 11:07:23', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('5afb440aba032436d92aab505d54b44c', '政府', '1', 'orgType', '0', '', '1', '0', '2020-06-22 14:48:41', '', '2020-06-22 14:48:41', '');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('5bd4db67102c9c8edb30fdc5c2b620d5', '连接数据源', '2', 'accessWay', '0', '', '2', '0', '2021-11-23 10:55:46', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-11-23 10:55:46', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('5d0b62244753c22c82017a3fbd77787b', '离线拷贝', '1', 'accessWay', '0', '', '1', '0', '2021-11-23 10:55:39', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-11-23 10:55:39', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('5f25d44609931aeb356188f39ef3c277', '近期整改', '2', 'rectType', '0', '', '2', '0', '2020-12-11 20:43:30', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-11 20:43:30', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('635aa43b70e338b77887405fd5528917', '长期任务取得阶段性成果', '2', 'finishStatus', '0', '', '2', '0', '2020-12-11 20:42:09', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-11 20:42:09', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('6599311e6d0edf8751730d9a0fb33bbe', '问责追责（人）', '3', 'effectType', '0', '', '3', '0', '2020-12-12 21:01:50', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-12 21:01:50', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('6666bae2c64576179986f9f0faee6089', '副主任', '10', 'position', '0', '', '10', '0', '2020-08-08 14:48:28', 'ae8ef659d56dc5415cdab94dea7c6a05', '2020-08-08 14:48:28', 'ae8ef659d56dc5415cdab94dea7c6a05');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('67c8264cf22c037c974432482897c91f', '金融', '1', 'industry', '0', '', '1', '0', '2021-11-23 10:54:36', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-11-23 10:54:36', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('6821a1a275dc150a27f3de702c0deed6', '核心涉密人员', '1', 'thickGrade', '0', '', '1', '0', '2020-06-19 19:13:00', '', '2020-06-19 19:13:00', '');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('6c2a023b1287f4842b08fa61c3c5e4a5', '档案管理子系统', '3', 'issueSystemType', '0', '', '3', '0', '2020-06-22 14:42:15', '', '2020-07-29 21:42:23', 'c2415bd24a5a93637de572f698f6eba3');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('6e7c47dc80e76bb1af2a0d04ada89468', '数据分析子系统', '2', 'issueSystemType', '0', '', '2', '0', '2020-06-22 14:39:09', '', '2020-07-29 21:42:13', 'c2415bd24a5a93637de572f698f6eba3');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('6e90e51bd7521f23dc69f8d3dac40dd9', '教育', '2', 'industry', '0', '', '2', '0', '2021-11-23 10:54:51', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-11-23 10:54:51', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('7022beb3241efd522bdc3dfb1c571d88', '学校', '2', 'orgType', '0', '', '2', '0', '2020-06-22 14:48:53', '', '2020-06-22 14:48:53', '');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('71a9f86084fbe3fb812ccdc66fd90f13', '党的建设缺少方面', '1', 'pType', '0', '', '1', '0', '2020-12-11 20:40:46', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-11 20:40:46', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('751accc577a88e5f8b3ca5aa488c1fc2', '接口', '3', 'accessWay', '0', '', '3', '0', '2021-11-23 10:55:56', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-11-23 10:55:56', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('83d7c6066d77b8271fc23aebde9f66a3', '一级机构', '1', 'orgRank', '0', '', '1', '0', '2020-06-22 14:48:10', '', '2020-06-22 14:48:10', '');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('84bc3d36701b93003d9b26f2d433a52c', '日期函数', '1', 'funcType', '0', '', '2', '0', '2021-03-11 11:07:36', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-03-11 11:07:36', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('88e1734afe768ca8542bfd3fa681a567', '巡视专员', '3', 'position', '0', '', '3', '0', '2020-08-08 14:46:40', 'ae8ef659d56dc5415cdab94dea7c6a05', '2020-08-08 14:46:40', 'ae8ef659d56dc5415cdab94dea7c6a05');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('951e035d8150cf54c95944a229d9bb76', '整改督察子系统', '4', 'issueSystemType', '0', '', '4', '0', '2020-06-22 14:42:45', '', '2020-07-29 21:42:31', 'c2415bd24a5a93637de572f698f6eba3');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('98167367a299dff66ec38868e71054c2', '内嵌iframe', '2', 'menuOpenWay', '0', '', '2', '0', '2020-06-28 00:00:00', '', '2021-12-16 00:00:00', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('99116adb818305dea4e352e1f96e20de', '主任', '11', 'position', '0', '', '11', '0', '2020-08-08 14:48:40', 'ae8ef659d56dc5415cdab94dea7c6a05', '2020-08-08 14:48:40', 'ae8ef659d56dc5415cdab94dea7c6a05');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('996a46faaca7b9c81abcb706bf2bc92f', '系统', '1', 'accountType', '0', '', '1', '0', '2020-06-28 16:40:10', '', '2020-06-28 16:40:10', '');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('9f0f31c482ff80864d83827afbf47438', '浪潮账号', '6', 'accountType', '0', '浪潮账号', '6', '0', '2021-02-05 14:18:10', '8895a0d44a3b63e40ffc63bad6a6ef1b', '2021-02-05 14:18:10', '8895a0d44a3b63e40ffc63bad6a6ef1b');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('a83228f9d4a65007ee5df0909486b903', '重要涉密人员', '2', 'thickGrade', '0', '', '2', '0', '2020-06-19 19:13:17', '', '2020-06-19 19:13:17', '');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('ab952ef22b07f0e69b728959bffeed66', '已完成', '1', 'finishStatus', '0', '', '1', '0', '2020-12-11 20:41:51', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-11 20:41:51', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('ad679e44cae83091c258e0c5588ca7a1', '处长', '9', 'position', '0', '', '9', '0', '2020-08-08 14:48:17', 'ae8ef659d56dc5415cdab94dea7c6a05', '2020-08-08 14:48:17', 'ae8ef659d56dc5415cdab94dea7c6a05');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('b3816692785f18cd7623cac4fa852289', 'GreenPlum', '5', 'databaseType', '0', 'Greenplum', '5', '0', '2020-07-28 20:14:05', 'a94a3fffb84dad12b42af19605d35a09', '2020-08-07 09:58:47', 'ae8ef659d56dc5415cdab94dea7c6a05');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('b5272861c2df0fdbf8847482ecc832a3', '科长', '7', 'position', '0', '', '7', '0', '2020-08-08 14:47:41', 'ae8ef659d56dc5415cdab94dea7c6a05', '2020-08-08 14:47:41', 'ae8ef659d56dc5415cdab94dea7c6a05');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('bcc8d8eb2d2a6124a354c47382e8ef19', '回头看巡察', '2', 'xsType', '0', '', '2', '0', '2020-12-11 21:03:12', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-11 21:03:12', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('c014ae30fb36785a4a12357b25509c5f', '住建厅', '2', 'department', '0', '', '6', '0', '2021-11-23 10:53:42', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-11-23 10:53:42', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('c7abc1a53e184b39a609bdbeae77d565', '完善制度（项）', '1', 'effectType', '0', '', '1', '0', '2020-12-12 21:01:18', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-12 21:01:18', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('cba38f9d62d85f7bc59a70312702ce1', '窗口函数', '4', 'funcType', '0', '', '5', '0', '2021-03-11 11:08:21', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-03-11 11:08:21', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('cba38f9d62d85f7bc59a70312702ce12', '其他函数', '5', 'funcType', '0', '', '6', '0', '2021-03-11 11:08:21', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-03-11 11:08:21', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('d00140661e561ab7d628615a328d0fe5', '成员', '5', 'position', '0', '', '5', '0', '2020-08-08 14:47:04', 'ae8ef659d56dc5415cdab94dea7c6a05', '2020-08-08 14:47:04', 'ae8ef659d56dc5415cdab94dea7c6a05');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('d438ce3bab821d0ed24c051c00f58d70', '挽回损失（万元）', '2', 'effectType', '0', '', '2', '0', '2020-12-12 21:01:33', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-12 21:01:33', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('d6d5e397b60bcfc26f49f12fb24b03df', '教师', '1', 'position', '0', '', '1', '0', '2020-07-29 21:40:31', 'c2415bd24a5a93637de572f698f6eba3', '2020-07-29 21:40:31', 'c2415bd24a5a93637de572f698f6eba3');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('de6d94c978e3df5d282b18bc55ad8ffb', '遵守六项纪律方面', '3', 'pType', '0', '', '3', '0', '2020-12-12 14:48:53', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-12 14:48:53', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('df904d20bfb6570106d3015fc4d76a4f', '巡察办', '2', 'orgType', '0', '', '2', '0', '2020-12-14 14:42:47', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-14 14:42:47', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('e7dcf12b5f79b76bd01805cf85fe0106', '统一权限系统', '5', 'issueSystemType', '0', '', '5', '0', '2020-06-22 14:43:22', '', '2020-07-29 21:42:41', 'c2415bd24a5a93637de572f698f6eba3');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('e81c2aa7929ff4ee347b746ba266f344', '被巡党组织', '3', 'orgType', '0', '', '3', '0', '2020-12-03 11:21:32', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-03 11:21:32', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('e8422664dac85f73a286324fe5ea5dfc', '落实中央八项规定，纠治“四风”“四气”问题方面', '5', 'pType', '0', '', '5', '0', '2020-12-12 14:50:59', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-12 14:50:59', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('ee971e02c448e990fe9ff099acfcff5d', '公安', '4', 'industry', '0', '', '4', '0', '2021-11-23 10:55:06', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-11-23 10:55:06', 'f900423f19e9eb6a7bd61cf8e6feb4f5');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('f1eb650576323edb21079c3cc1ef95f5', '监督单位', '3', 'accountType', '0', '', '3', '0', '2020-06-28 16:40:29', '', '2020-06-28 16:40:29', '');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('f41fff757a46ef89769357d5216263d9', '人员', '字典', '008', '0', '', '1', '0', '2020-08-22 16:13:16', 'c2415bd24a5a93637de572f698f6eba3', '2020-08-22 16:13:16', 'c2415bd24a5a93637de572f698f6eba3');
INSERT INTO `iam_std_code`(`id`, `code_name`, `code_value`, `std_type`, `useable`, `remark`, `order_index`, `del_flag`, `create_time`, `create_id`, `update_time`, `update_id`) VALUES ('faebf6443b0455b1217f94b717aaac65', '扶贫办', '3', 'department', '0', '', '3', '0', '2021-11-23 10:53:56', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-11-23 10:53:56', 'f900423f19e9eb6a7bd61cf8e6feb4f5');

-- 应用
-- 统一权限系统
INSERT INTO `iam_app`(`id`, `app_name`, `app_code`, `url`, `img`, `order_index`, `app_key`, `app_secret`, `open_type`, `useable`, `del_flag`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '统一权限系统', 'tyqxxt', 'http://localhost:8885/#/dashboard?appCode=tyqxxt', 'unify-6.png', '6', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCydmTtPCuGufOz4JpGMo4agTlqtkDp2I4ikwmXkkADgAyHkYBlJHeppcNkeVSp85Rufza/S5Xrle2UyC/1g3dt9qPRiSn9fW6vTh3Y7qI8RjLiTV7WtsUx/ay8FqRq4nXbLmQnBg+LJ+bSgaNxFvxIdPaPIxXBF48Jf9LyutS0ZwIDAQAB', 'MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALJ2ZO08K4a587PgmkYyjhqBOWq2QOnYjiKTCZeSQAOADIeRgGUkd6mlw2R5VKnzlG5/Nr9LleuV7ZTIL/WDd232o9GJKf19bq9OHdjuojxGMuJNXta2xTH9rLwWpGriddsuZCcGD4sn5tKBo3EW/Eh09o8jFcEXjwl/0vK61LRnAgMBAAECgYEAoTFVKf7OJcux9sgpMLybJA+Sh/OEoHOstkfvQwujbEXti0n+Gd0dhIsn+Imq0jMj38Zh8dlY2ci7JkbHItATPynMR90oOCBruBKRLYkhW1Vb+jRmMWGIH573TEomfEo29eU8uL9ug2qG9wyaffn6kvDqTRuE4nYbpzj9xRPndFkCQQDjRHpM7VdBiL0FAq3JgwnWEXAB71WdOdHYUjSztetpjRgKafT7UVQX9VsF7ll5I/+WQrhSwPj+32QlmPcWcoUrAkEAyQZXEa59wsF09qaH/Y0pyRjJNwjGzU+adsZgeav8lAYheWDttPYwsRwbDbKq/KuB1ScMesnyOkMz8GJSyTkntQJBAM6olDWjlLao6ojF2NXm7GjWTm3T/pwL2b/FcNsBz+GZRYUiuHxXhgqLN4v7Iy0/CMjQPuAxTbol1OFDaVvmIyUCQBJ3JqBVKvAs3RWbDnrX8LKsOC95+uj5GvM2Hb6PcdAXEBQaNzucMbTTJBkKBHLZPj/aKmdNDZcUfTzjiz84pNECQHiakhraVq4eUig06oV/LXRAx/FWp5oNykf4C/jkkc6g/q3NsgBmlug+eAgl0ZEpmE4ZwWGmXhAcn6/dYUPa0lQ=', '0', '0', '0', '', '2020-06-28 16:16:01', 'c2415bd24a5a93637de572f698f6eba3', '2020-11-02 16:22:19');


-- 账号
INSERT INTO `iam_user_code`(`id`, `user_id`, `login_name`, `passwd`, `salt`, `type`, `status`, `pwd_update_time`, `del_flag`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES ('2c6dbe99c5c48df70b95296f1eb46dc7', '056c24ae56556787e62d53d7220a0ddb', 'safety_admin', '6d*11##1!adf60@*c6b!636gd!2f305b3a3bb50b!#@65da#*1a00g4@badc2b4@', '337954', '1', '0', '2021-05-31 14:47:37', '0', 'c2415bd24a5a93637de572f698f6eba3', '2020-07-29 17:47:29', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-05-31 20:21:49');
INSERT INTO `iam_user_code`(`id`, `user_id`, `login_name`, `passwd`, `salt`, `type`, `status`, `pwd_update_time`, `del_flag`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES ('5621a61ddabfb77ee4140757cf73abed', 'fc06475e914409d67ea8ae9bfe3e0e42', 'audit_admin', '51b@dc1g60d3ba6*c10c3g@#2F#6!16@6*!d3@a@@cb35a4gc@2dcF!#3@5a56c!', '906940', '1', '0', '2021-05-31 14:47:48', '0', 'c2415bd24a5a93637de572f698f6eba3', '2020-07-29 17:47:52', 'c2415bd24a5a93637de572f698f6eba3', '2021-05-31 14:47:48');
INSERT INTO `iam_user_code`(`id`, `user_id`, `login_name`, `passwd`, `salt`, `type`, `status`, `pwd_update_time`, `del_flag`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES ('81842a7795700362f79cafb962910169', '058dbcd497c851a15a474287c48317c4', 'system_admin', '2#615!cF2!5cd!5f311a!6@fb@a5#*54#0a0#4c5c2!bbc41bgd31c#0@@c34g0g', '909764', '1', '0', '2021-05-31 14:47:58', '0', 'c2415bd24a5a93637de572f698f6eba3', '2020-07-29 17:46:52', 'c2415bd24a5a93637de572f698f6eba3', '2021-05-31 14:47:58');

-- 用户
INSERT INTO `iam_user` VALUES ('056c24ae56556787e62d53d7220a0ddb', '安全管理员', '0', '', '', '', '429006199912121214', '15167587463', '', '1', '7c97cdfa4e4c561b9ca088cfcdbf51c3', '安全管理员', '一级', '', '', '0', 'c2415bd24a5a93637de572f698f6eba3', '2020-07-29 17:42:39', 'c2415bd24a5a93637de572f698f6eba3', '2020-10-23 20:30:24');
INSERT INTO `iam_user` VALUES ('058dbcd497c851a15a474287c48317c4', '系统管理员', '0', '', '', '', '429006199912121213', '15167654567', '', '1', '7c97cdfa4e4c561b9ca088cfcdbf51c3', '管理员', '一级', '', '', '0', 'c2415bd24a5a93637de572f698f6eba3', '2020-07-29 17:41:54', 'c2415bd24a5a93637de572f698f6eba3', '2020-07-29 17:41:54');
INSERT INTO `iam_user` VALUES ('fc06475e914409d67ea8ae9bfe3e0e42', '审计管理员', '0', '', '', '', '429006199912121215', '15167543678', '', '1', '7c97cdfa4e4c561b9ca088cfcdbf51c3', '审计管理员', '一级', '', '', '0', 'c2415bd24a5a93637de572f698f6eba3', '2020-07-29 17:43:48', 'c2415bd24a5a93637de572f698f6eba3', '2020-07-29 17:43:48');


-- 账号角色关联数据
INSERT INTO `iam_role_user_code` VALUES ('be9deea4c7d2d163fb719b6b500f5766', '3bfdd39f7b2279b3c32bf49198017942', '2c6dbe99c5c48df70b95296f1eb46dc7');
INSERT INTO `iam_role_user_code` VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4337682abd8f2d4762d08c986489fc13', '5621a61ddabfb77ee4140757cf73abed');
INSERT INTO `iam_role_user_code` VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', '81842a7795700362f79cafb962910169');

-- 角色菜单关联数据
-- 系统管理员角色菜单权限
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', '01c8ae6a3d7c7204d398f377dbcf4d9e');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', '123befcd0fb8c6372b89e38797fcfc58');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', '138d25d94c08b214fe7d6b70f193053f');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', '13fc664734941059cae6691b0ec83db4');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', '1c1d099cc76f2645c7cbd5bd3ca155ca');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', '1d421cff44ca50fe2a62c327612031ff');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', '5140bbc89068cb41d0045efc1bfbad29');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', '54087ce8a0f6741131836caec2ae127e');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', '5fc47b78453bc21b090c437b62b0f22b');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', '68e603f26d374084224e5ab0c16b2278');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', '6f754e0c57cd4819f9418cc937046e78');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', '701ac0eadff87ad28deae1b0d1ad7317');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', '758c5d5a11795c13a0eeda0d93903525');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', '88ac71fdaf7c4964ed91f2ae36f0a496');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', '8f3dca6446c18638a016869c45fa992e');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', '94f1b62f604765d2f9236472cfac0f01');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', 'a57a94ae6dec29982e038e23ed0c799e');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', 'a5e72c9400569542d4f7f34f8e7f80f8');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', 'ad936e2b94a32c8bd2e4d355724b9cde');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', 'b91dc1f75e92eaee2c044ac6209ab3c5');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', 'bb85b74922d3a4921df5110741d2b1b4');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', 'bd363e0ca1fccc5c1b89e3290d1311f2');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', 'bd5821da7d5e22cd651d841b101cc7e8');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', 'bdbeb03ad4d060d0485fe93df3ddafc6');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', 'c3dc0fd08a6b2a5e17f2222d64385144');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', 'cb8c8cd1635129f30a0a9b4299d8b581');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', 'cd01d2c621bd4a39afd932531d2ca01d');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', 'd0938c8e3e7093ea67dc9cc4640e6736');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', 'df3770cfe25a6f5113834f05633b0f42');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', 'e8fb8615f88210b45490e632cc80091d');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', 'f33cf3a3127b1ed16c89af1bd8dfe447');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4375788fc142b233a52ac764999e8e38', 'f3ae6a6957feb2296f2e3604e2a4eeb4');

-- 审计管理员角色菜单权限
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4337682abd8f2d4762d08c986489fc13', '47f3ef31b383c1bd6bf65bc920fbab6f');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4337682abd8f2d4762d08c986489fc13', '4bf2e5c68b14b74be714bba01691457a');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4337682abd8f2d4762d08c986489fc13', '9259bb06d06ed5fec9c4aaa93e4d03cd');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4337682abd8f2d4762d08c986489fc13', 'a5b0d3a4470096547a5a1d5e116ca869');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '4337682abd8f2d4762d08c986489fc13', 'e1b615af87b6014f01bccd476c6d5147');

-- 安全管理员角色菜单权限
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '3bfdd39f7b2279b3c32bf49198017942', '051c4a9d4593ac57fd2bcb9d49c09152');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '3bfdd39f7b2279b3c32bf49198017942', '4a05fb0d5250a621a2bd826297a9c8cc');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '3bfdd39f7b2279b3c32bf49198017942', '503c31c1b282caff7d38f2783083ce97');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '3bfdd39f7b2279b3c32bf49198017942', '50f3fdeed88457406fd6495c1e4bbd5b');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '3bfdd39f7b2279b3c32bf49198017942', '6fea46959b1b21f08a0de544695e551c');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '3bfdd39f7b2279b3c32bf49198017942', 'b2ebb5d08d8eff75df00ce47a4ac00c8');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '3bfdd39f7b2279b3c32bf49198017942', 'be86c51e0ee2a855f6fded314dbd6e75');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '3bfdd39f7b2279b3c32bf49198017942', 'ca851b344c16d2df48f0ddfed452750e');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '3bfdd39f7b2279b3c32bf49198017942', 'd0ce64724f06de734e27dcc1cf29667a');


-- 角色信息
-- 统一权限系统
INSERT INTO `iam_role`(`id`, `app_id`, `role_name`, `role_code`, `role_type`, `useable`, `del_flag`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES ('3bfdd39f7b2279b3c32bf49198017942', 'be9deea4c7d2d163fb719b6b500f5766', '安全管理员', '1002', '0', '0', '0', '', '2020-06-28 16:26:45', 'ae8ef659d56dc5415cdab94dea7c6a05', '2020-10-16 09:36:22');
INSERT INTO `iam_role`(`id`, `app_id`, `role_name`, `role_code`, `role_type`, `useable`, `del_flag`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES ('4337682abd8f2d4762d08c986489fc13', 'be9deea4c7d2d163fb719b6b500f5766', '审计管理员', '1003', '0', '0', '0', '', '2020-06-28 16:26:58', 'c2415bd24a5a93637de572f698f6eba3', '2020-08-12 16:42:20');
INSERT INTO `iam_role`(`id`, `app_id`, `role_name`, `role_code`, `role_type`, `useable`, `del_flag`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES ('4375788fc142b233a52ac764999e8e38', 'be9deea4c7d2d163fb719b6b500f5766', '系统管理员', '1001', '0', '0', '0', '', '2020-06-28 16:26:27', '', '2020-06-28 16:26:27');


-- 机构数据
INSERT INTO `iam_office`(`id`, `parent_id`, `parent_ids`, `name`, `code`, `paths`, `order_index`, `app_id`, `district_id`, `otype`, `levels`, `address`, `supervisor`, `phone`, `fax`, `email`, `useable`, `del_flag`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES ('7c97cdfa4e4c561b9ca088cfcdbf51c3', '-1', '', 'XX县纪委', '002', '002/', 2, NULL, '', '1', '1', 'xxx', '**', '123123123', '12321', '123213', '0', '0', '', '2020-06-28 16:19:09', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-12-14 14:43:31');

-- 菜单
-- 统一权限子系统
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('01c8ae6a3d7c7204d398f377dbcf4d9e', 'd0938c8e3e7093ea67dc9cc4640e6736', '', 'be9deea4c7d2d163fb719b6b500f5766', '编辑字典项', '', '', '', '1', '3', NULL, 5, '', '', '0', 'system:std-code:update', '', '2020-06-30 10:51:58', '', '2020-06-30 10:51:58', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('051c4a9d4593ac57fd2bcb9d49c09152', '-1', '', 'be9deea4c7d2d163fb719b6b500f5766', '权限管理', '/authcenter', '', '', '1', '2', NULL, 7, 'auth', '', '0', 'system:authcenter:view', '', '2020-06-29 14:53:19', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-03-05 15:04:45', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('123befcd0fb8c6372b89e38797fcfc58', 'cd01d2c621bd4a39afd932531d2ca01d', '', 'be9deea4c7d2d163fb719b6b500f5766', '编辑角色', '', '', '', NULL, '3', NULL, 2, '', '', '0', 'system:role:get', '', '2020-06-29 23:53:42', '', '2020-06-29 23:53:42', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('138d25d94c08b214fe7d6b70f193053f', 'bd5821da7d5e22cd651d841b101cc7e8', '', 'be9deea4c7d2d163fb719b6b500f5766', '新增应用', '', '', '', NULL, '3', NULL, 1, '', '', '0', 'system:app:create', '', '2020-06-29 23:44:02', '', '2020-06-29 23:44:02', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('13fc664734941059cae6691b0ec83db4', 'bd5821da7d5e22cd651d841b101cc7e8', '', 'be9deea4c7d2d163fb719b6b500f5766', '删除应用', '', '', '', NULL, '3', NULL, 3, '', '', '0', 'system:app:delete', '', '2020-06-29 23:45:09', '', '2020-06-29 23:45:09', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('1c1d099cc76f2645c7cbd5bd3ca155ca', 'd0938c8e3e7093ea67dc9cc4640e6736', '', 'be9deea4c7d2d163fb719b6b500f5766', '删除字典类型', '', '', '', '1', '3', NULL, 3, '', '', '0', 'system:std-type:delete', '', '2020-06-30 10:49:01', '', '2020-06-30 10:49:01', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('1d421cff44ca50fe2a62c327612031ff', '-1', '', 'be9deea4c7d2d163fb719b6b500f5766', '用户管理', '/usermng', '', '@/views/appcenter/usermng', '1', '2', NULL, 4, 'usermng', '', '0', 'system:usermng:view', '', '2020-06-29 14:50:46', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-03-05 15:04:29', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('1e37436c3287d62cce5f6d7b122121d0', '9259bb06d06ed5fec9c4aaa93e4d03cd', '', 'be9deea4c7d2d163fb719b6b500f5766', '异常日志', '/errormng', '', '@/views/record/errormng', '1', '2', NULL, 2, 'menu_error', '', '0', 'system:appcenter:error', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-02-04 14:48:44', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-02-04 14:50:11', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('3b64265882188969da45a0557830b3cd', '-1', '', 'be9deea4c7d2d163fb719b6b500f5766', '首页', '/', '', '@/views/dashboard/index', '1', '2', NULL, 0, 'index', '', '1', 'index', 'c2415bd24a5a93637de572f698f6eba3', '2020-07-18 11:05:09', 'a94a3fffb84dad12b42af19605d35a09', '2020-08-18 00:12:30', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('47f3ef31b383c1bd6bf65bc920fbab6f', 'e1b615af87b6014f01bccd476c6d5147', '', 'be9deea4c7d2d163fb719b6b500f5766', '查看意见反馈', '', '', '', NULL, '3', NULL, 2, '', '', '0', 'system:feedback:get', '', '2020-06-30 17:27:27', '', '2020-06-30 17:27:27', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('4a05fb0d5250a621a2bd826297a9c8cc', '051c4a9d4593ac57fd2bcb9d49c09152', '', 'be9deea4c7d2d163fb719b6b500f5766', '权限管理', '/authmng', '', '@/views/appcenter/authcenter/authmng', '1', '2', NULL, 1, 'auth', '', '0', 'system:authmng:insofar', '', '2020-06-29 14:54:57', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-05-28 17:55:38', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('4bf2e5c68b14b74be714bba01691457a', 'e1b615af87b6014f01bccd476c6d5147', '', 'be9deea4c7d2d163fb719b6b500f5766', '新增意见反馈', '', '', '', NULL, '3', NULL, 1, '', '', '0', 'system:feedback:create', '', '2020-06-30 17:27:05', '', '2020-06-30 17:27:05', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('4d547b194b1727590d06fb3b23d02825', 'f9cdd883c7b92d171e82fda03ced1e7b', '', 'be9deea4c7d2d163fb719b6b500f5766', '个人中心', '/personal-center', '', '@/views/appcenter/personal-center', '1', '2', '', 19, 'auth', '', '1', 'iam', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-05-12 16:23:28', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-05-13 15:52:37', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('503c31c1b282caff7d38f2783083ce97', 'ca851b344c16d2df48f0ddfed452750e', '', 'be9deea4c7d2d163fb719b6b500f5766', '添加安全管理员', '', '', '', NULL, '3', NULL, 2, '', '', '0', 'system:role-user-code:safety', '', '2020-06-30 00:22:40', '', '2020-06-30 00:22:40', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('50f3fdeed88457406fd6495c1e4bbd5b', 'ca851b344c16d2df48f0ddfed452750e', '', 'be9deea4c7d2d163fb719b6b500f5766', '添加审计管理员', '', '', '', NULL, '3', NULL, 3, '', '', '0', 'system:role-user-code:audit', '', '2020-06-30 00:23:29', '', '2020-06-30 00:23:29', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('5140bbc89068cb41d0045efc1bfbad29', 'a5e72c9400569542d4f7f34f8e7f80f8', '', 'be9deea4c7d2d163fb719b6b500f5766', '编辑账号', '', '', '', '1', '3', NULL, 2, '', '', '0', 'system:user-code:get', '', '2020-06-29 23:58:35', '', '2020-06-29 23:59:38', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('54087ce8a0f6741131836caec2ae127e', 'a5e72c9400569542d4f7f34f8e7f80f8', '', 'be9deea4c7d2d163fb719b6b500f5766', '绑定角色', '', '', '', '1', '3', NULL, 5, '', '', '0', 'system:role-user-code:create', '', '2020-06-30 00:02:43', '', '2020-06-30 00:02:43', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('5fc47b78453bc21b090c437b62b0f22b', 'd0938c8e3e7093ea67dc9cc4640e6736', '', 'be9deea4c7d2d163fb719b6b500f5766', '新增字典项', '', '', '', '1', '3', NULL, 4, '', '', '0', 'system:std-code:create', '', '2020-06-30 10:51:05', '', '2020-06-30 10:51:05', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('68e603f26d374084224e5ab0c16b2278', 'f33cf3a3127b1ed16c89af1bd8dfe447', '', 'be9deea4c7d2d163fb719b6b500f5766', '编辑机构', '', '', '', NULL, '3', NULL, 2, '', '', '0', 'system:office:get', '', '2020-06-29 23:47:17', '', '2020-06-29 23:47:17', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('6915e27a2d2b49b5da80beb593272590', '9259bb06d06ed5fec9c4aaa93e4d03cd', '', 'be9deea4c7d2d163fb719b6b500f5766', '操作日志', '/recordmng', '', '@/views/record/recordmng', '1', '2', NULL, 1, 'auth', '', '0', 'system:appcenter:operation', 'c2415bd24a5a93637de572f698f6eba3', '2020-07-02 14:51:18', 'c2415bd24a5a93637de572f698f6eba3', '2020-07-18 11:07:03', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('6c6f02d456b3a17e172ae8eaed1fe951', 'f9cdd883c7b92d171e82fda03ced1e7b', '', 'be9deea4c7d2d163fb719b6b500f5766', '系统配置', '/system-setting', '', '@/views/system-setting/index', '1', '2', NULL, 1, 'dict', '', '0', 'iam', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-02-09 22:18:14', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-02-09 22:18:14', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('6f754e0c57cd4819f9418cc937046e78', '1d421cff44ca50fe2a62c327612031ff', '', 'be9deea4c7d2d163fb719b6b500f5766', '编辑用户', '', '', '', '1', '3', NULL, 2, '', '', '0', 'system:user:get', '', '2020-06-29 23:55:44', '', '2020-06-29 23:55:44', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('6fea46959b1b21f08a0de544695e551c', '4a05fb0d5250a621a2bd826297a9c8cc', '', 'be9deea4c7d2d163fb719b6b500f5766', '保存权限', '', '', '', '1', '3', NULL, 1, '', '', '0', 'system:role-user-code:addOrDelRoleMenu', '', '2020-06-30 00:16:27', '', '2020-06-30 00:16:27', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('701ac0eadff87ad28deae1b0d1ad7317', 'f33cf3a3127b1ed16c89af1bd8dfe447', '', 'be9deea4c7d2d163fb719b6b500f5766', '新增机构', '', '', '', NULL, '3', NULL, 1, '', '', '0', 'system:office:create', '', '2020-06-29 23:47:01', '', '2020-06-29 23:47:01', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('758c5d5a11795c13a0eeda0d93903525', 'a5e72c9400569542d4f7f34f8e7f80f8', '', 'be9deea4c7d2d163fb719b6b500f5766', '删除账号', '', '', '', '1', '3', NULL, 3, '', '', '0', 'system:user-code:delete', '', '2020-06-29 23:59:32', '', '2020-06-29 23:59:42', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('88ac71fdaf7c4964ed91f2ae36f0a496', 'd0938c8e3e7093ea67dc9cc4640e6736', '', 'be9deea4c7d2d163fb719b6b500f5766', '编辑字典类型', '', '', '', '1', '3', NULL, 2, '', '', '0', 'system:std-type:update', '', '2020-06-30 10:48:00', '', '2020-06-30 10:48:05', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('8f3dca6446c18638a016869c45fa992e', 'f33cf3a3127b1ed16c89af1bd8dfe447', '', 'be9deea4c7d2d163fb719b6b500f5766', '删除机构', '', '', '', NULL, '3', NULL, 3, '', '', '0', 'system:office:delete', '', '2020-06-29 23:47:36', '', '2020-06-29 23:47:36', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('9259bb06d06ed5fec9c4aaa93e4d03cd', '-1', '', 'be9deea4c7d2d163fb719b6b500f5766', '日志管理', '/appcenter', '', '', '1', '2', NULL, 10, 'record', '', '0', 'system:log:view', '', '2020-06-29 14:57:46', 'c2415bd24a5a93637de572f698f6eba3', '2020-07-18 11:06:15', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('94f1b62f604765d2f9236472cfac0f01', 'a5e72c9400569542d4f7f34f8e7f80f8', '', 'be9deea4c7d2d163fb719b6b500f5766', '绑定用户', '', '', '', '1', '3', NULL, 4, '', '', '0', 'system:user-code:bindingUser', '', '2020-06-30 00:00:55', '', '2020-06-30 00:00:55', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('a57a94ae6dec29982e038e23ed0c799e', 'd0938c8e3e7093ea67dc9cc4640e6736', '', 'be9deea4c7d2d163fb719b6b500f5766', '新增字典类型', '', '', '', '1', '3', NULL, 1, '', '', '0', 'system:std-type:creat', '', '2020-06-30 10:46:16', '', '2020-06-30 10:49:20', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('a5b0d3a4470096547a5a1d5e116ca869', 'e1b615af87b6014f01bccd476c6d5147', '', 'be9deea4c7d2d163fb719b6b500f5766', '删除意见反馈', '', '', '', NULL, '3', NULL, 3, '', '', '0', 'system:feedback:delete', '', '2020-06-30 17:27:49', '', '2020-06-30 17:27:49', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('a5e72c9400569542d4f7f34f8e7f80f8', '-1', '', 'be9deea4c7d2d163fb719b6b500f5766', '账号管理', '/accountmng', '', '@/views/appcenter/accountmng', '1', '2', NULL, 5, 'account', '', '0', 'system:accountmng:view', '', '2020-06-29 14:51:11', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-03-05 15:04:33', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('aa21ee374e0b555fd71f293a4f059ac7', 'b2ebb5d08d8eff75df00ce47a4ac00c8', '', 'be9deea4c7d2d163fb719b6b500f5766', '新增子集菜单', '', '', '', '1', '3', NULL, 4, '', '', '0', 'system:menu:subset', '', '2020-06-30 00:06:25', '', '2020-06-30 00:06:25', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('ad936e2b94a32c8bd2e4d355724b9cde', 'f33cf3a3127b1ed16c89af1bd8dfe447', '', 'be9deea4c7d2d163fb719b6b500f5766', '新增子集机构', '', '', '', NULL, '3', NULL, 4, '', '', '0', 'system:office:subset', '', '2020-06-29 23:48:33', '', '2020-06-29 23:48:33', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('b2ebb5d08d8eff75df00ce47a4ac00c8', '-1', '', 'be9deea4c7d2d163fb719b6b500f5766', '菜单管理', '/menumng', '', '@/views/appcenter/menumng', '1', '2', NULL, 6, 'menu', '', '0', 'system:menumng:view', '', '2020-06-29 14:52:27', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-05-12 15:28:46', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('b91dc1f75e92eaee2c044ac6209ab3c5', 'cd01d2c621bd4a39afd932531d2ca01d', '', 'be9deea4c7d2d163fb719b6b500f5766', '新增角色', '', '', '', NULL, '3', NULL, 1, '', '', '0', 'system:role:create', '', '2020-06-29 23:53:10', '', '2020-06-29 23:53:10', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('bb85b74922d3a4921df5110741d2b1b4', 'bd5821da7d5e22cd651d841b101cc7e8', '', 'be9deea4c7d2d163fb719b6b500f5766', '重置公钥私钥', '', '', '', NULL, '3', NULL, 4, '', '', '0', 'system:app:reset', '', '2020-06-29 23:45:37', '', '2020-06-29 23:45:37', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('bd363e0ca1fccc5c1b89e3290d1311f2', '1d421cff44ca50fe2a62c327612031ff', '', 'be9deea4c7d2d163fb719b6b500f5766', '删除用户', '', '', '', '1', '3', NULL, 3, '', '', '0', 'system:user:delete', '', '2020-06-29 23:56:04', '', '2020-06-29 23:56:30', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('bd5821da7d5e22cd651d841b101cc7e8', '-1', '', 'be9deea4c7d2d163fb719b6b500f5766', '应用管理', '/appmng', '', '/appcenter/appmng', '1', '2', NULL, 1, 'app', '', '0', 'system:appmng:view', '', '2020-06-29 14:33:45', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-05-28 16:56:07', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('bdbeb03ad4d060d0485fe93df3ddafc6', 'cd01d2c621bd4a39afd932531d2ca01d', '', 'be9deea4c7d2d163fb719b6b500f5766', '删除角色', '', '', '', NULL, '3', NULL, 3, '', '', '0', 'system:role:delete', '', '2020-06-29 23:54:04', '', '2020-06-29 23:54:04', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('be86c51e0ee2a855f6fded314dbd6e75', 'ca851b344c16d2df48f0ddfed452750e', '', 'be9deea4c7d2d163fb719b6b500f5766', '添加系统管理员', '', '', '', NULL, '3', NULL, 1, '', '', '0', 'system:role-user-code:system', '', '2020-06-30 00:21:31', '', '2020-06-30 00:21:31', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('c3dc0fd08a6b2a5e17f2222d64385144', 'd0938c8e3e7093ea67dc9cc4640e6736', '', 'be9deea4c7d2d163fb719b6b500f5766', '删除字典项', '', '', '', '1', '3', NULL, 6, '', '', '0', 'system:std-code:delete', '', '2020-06-30 10:52:32', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-04-06 15:26:08', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('ca851b344c16d2df48f0ddfed452750e', '051c4a9d4593ac57fd2bcb9d49c09152', '', 'be9deea4c7d2d163fb719b6b500f5766', '三员管理', '/systemmng', '', '@/views/appcenter/authcenter/systemmng', '1', '1', NULL, 2, 'three', '', '0', 'system:systemmng:insofar', '', '2020-06-29 14:56:17', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-05-12 15:28:55', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('cb8c8cd1635129f30a0a9b4299d8b581', '1d421cff44ca50fe2a62c327612031ff', '', 'be9deea4c7d2d163fb719b6b500f5766', 'excel导入数据', '', '', '', '1', '3', NULL, 4, '', '', '0', 'system:user:excel', '', '2020-06-29 23:57:22', '', '2020-06-29 23:57:22', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('cd01d2c621bd4a39afd932531d2ca01d', '-1', '', 'be9deea4c7d2d163fb719b6b500f5766', '角色管理', '/rolemng', '', '@/views/appcenter/rolemng', '1', '2', NULL, 3, 'role', '', '0', 'system:rolemng:view', '', '2020-06-29 14:50:25', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-03-05 15:04:24', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('cdb620fb13c3ffc4192b3662c07f1f98', 'b2ebb5d08d8eff75df00ce47a4ac00c8', '', 'be9deea4c7d2d163fb719b6b500f5766', '删除菜单', '', '', '', '1', '3', NULL, 3, '', '', '0', 'system:menu:delete', '', '2020-06-30 00:05:27', '', '2020-06-30 00:05:31', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('d0938c8e3e7093ea67dc9cc4640e6736', '-1', '', 'be9deea4c7d2d163fb719b6b500f5766', '字典管理', '/dictmng', '', '@/views/appcenter/dictmng', '1', '2', NULL, 9, 'dict', '', '0', 'system:appcenter:view', '', '2020-06-29 14:57:18', 'a94a3fffb84dad12b42af19605d35a09', '2020-08-16 23:48:26', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('d0ce64724f06de734e27dcc1cf29667a', 'ca851b344c16d2df48f0ddfed452750e', '', 'be9deea4c7d2d163fb719b6b500f5766', '删除管理员', '', '', '', NULL, '3', NULL, 4, '', '', '0', 'system:role-user-code:delete', 'a94a3fffb84dad12b42af19605d35a09', '2020-06-30 21:40:35', 'a94a3fffb84dad12b42af19605d35a09', '2020-06-30 21:40:35', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('db889efc3159fa6338be745ce67aa2d7', 'b2ebb5d08d8eff75df00ce47a4ac00c8', '', 'be9deea4c7d2d163fb719b6b500f5766', '新增菜单', '', '', '', '1', '3', NULL, 1, '', '', '0', 'system:menu:create', '', '2020-06-30 00:03:55', '', '2020-06-30 00:03:55', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('df3770cfe25a6f5113834f05633b0f42', '1d421cff44ca50fe2a62c327612031ff', '', 'be9deea4c7d2d163fb719b6b500f5766', '新增用户', '', '', '', '1', '3', NULL, 1, '', '', '0', 'system:user:create', '', '2020-06-29 23:55:26', '', '2020-06-29 23:56:26', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('e1b615af87b6014f01bccd476c6d5147', '-1', '', 'be9deea4c7d2d163fb719b6b500f5766', '意见反馈', '/feedback', '', '@/views/appcenter/feedback', '1', '2', NULL, 11, 'feedback', '', '0', 'system:feedback:view', '', '2020-06-30 17:26:16', 'c2415bd24a5a93637de572f698f6eba3', '2020-07-21 16:43:43', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('e8fb8615f88210b45490e632cc80091d', 'a5e72c9400569542d4f7f34f8e7f80f8', '', 'be9deea4c7d2d163fb719b6b500f5766', '新增账号', '', '', '', '1', '3', NULL, 1, '', '', '0', 'system:user-code:create', '', '2020-06-29 23:58:19', '', '2020-06-29 23:58:19', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('e904121f686a887d1e0da8a0ea801d44', '9259bb06d06ed5fec9c4aaa93e4d03cd', '', 'be9deea4c7d2d163fb719b6b500f5766', '登录日志', '/loginRecord', '', '@/views/record/loginRecord', '1', '2', NULL, 2, 'three', '', '0', 'system:appcenter:login', 'c2415bd24a5a93637de572f698f6eba3', '2020-07-02 14:51:44', 'c2415bd24a5a93637de572f698f6eba3', '2020-07-18 11:07:13', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('f094a744fc551a32ddc507d07d60c6b4', 'b2ebb5d08d8eff75df00ce47a4ac00c8', '', 'be9deea4c7d2d163fb719b6b500f5766', '编辑菜单', '', '', '', '1', '3', NULL, 2, '', '', '0', 'system:menu:get', '', '2020-06-30 00:05:01', '', '2020-06-30 00:05:01', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('f33cf3a3127b1ed16c89af1bd8dfe447', '-1', '', 'be9deea4c7d2d163fb719b6b500f5766', '机构管理', '/orgmng', '', '/appcenter/orgmng', '1', '2', NULL, 2, 'org', '', '0', 'system:orgmng:view', '', '2020-06-29 14:48:53', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-05-29 12:17:04', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('f3ae6a6957feb2296f2e3604e2a4eeb4', 'bd5821da7d5e22cd651d841b101cc7e8', '', 'be9deea4c7d2d163fb719b6b500f5766', '编辑应用', '', '', '', NULL, '3', NULL, 2, '', '', '0', 'system:app:get', '', '2020-06-29 23:44:29', 'a94a3fffb84dad12b42af19605d35a09', '2020-07-03 21:02:41', '0', NULL);
INSERT INTO `iam_menu`(`id`, `parent_id`, `parent_ids`, `app_id`, `name`, `path`, `urls`, `site`, `open_type`, `type`, `menu_param`, `order_index`, `icon`, `img`, `hidden`, `perms`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `code`) VALUES ('f9cdd883c7b92d171e82fda03ced1e7b', '-1', '', 'be9deea4c7d2d163fb719b6b500f5766', '系统配置', '/system-setting', '', '', '1', '2', NULL, 15, 'dict', '', '0', 'iam', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-02-09 22:17:10', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-02-09 22:17:19', '0', NULL);

/**
整理时间：2021-06-01
整理人：蔡凡
 */

