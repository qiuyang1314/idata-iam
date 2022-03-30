package com.zyaud.idata.iam.common.utils;

import cn.hutool.core.util.ObjectUtil;
import com.zyaud.idata.foundation.util.utils.FileUploadUtil;
import org.apache.commons.codec.binary.Base64;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Constants {

    //账号锁定
    public static final String USERCODE_LOCK = "1";
    //账号停止
    public static final String USERCODE_STOP = "2";

    public static final Integer USERCODE_RANDOM_NUMBER = 6;
    public static final String TOKENKEY = "tokenKey_";
    public static final String LOGINERR = "login_";
    public static final String USER_APP = "user_appId_";
    public static final String CDI_IAM = "统一权限系统";
    public static final String CDI_IPS = "基础信息管理平台";
    public static final String CDI_IRS = "整改督察子系统";
    public static final String CDI_AAS = "档案管理子系统";
    public static final String CDI_DAS = "数据分析子系统";
    public static final String CDI_DATAV = "大屏展示系统";
    // 业务系统编码
    public static final String DAS_CODE = "sjfxzxt";  // 数据分析子系统
    public static final String DAVS_CODE = "dpzszxt"; // 大屏展示系统  (待定)
    public static final String IPS_CODE = "jcxxglpt"; // 基础信息管理平台
    public static final String AAS_CODE = "daglzxt";  // 档案管理子系统
    public static final String IRS_CODE = "zgdczxt";  // 整改督察子系统
    public static final String OWS_CODE = "xcgzzxt";  // 现场工作子系统
    public static final String IAM_CODE = "tyqxxt";   // 统一权限系统
    public static final String DATAEX_CODE = "zwxxgxpt"; //政务信息管理系统


    public static final String COMET_METHOD_LOGIN = "tyqxxt_login";


    public static final Long ACCESS_TOKEN_EXPIRE = 3 * 60 * 60L;
    public static final Long REFRESH_TOKEN_EXPIRE = 24 * 60 * 60L;
    public static final Long IP_CONFIG_EXPIRE = 60 * 60L;
    public static final Long CAPTCHA_EXPIRE = 30L;
    public static final String FZHX_IAM_USER = "FzhxIamUser_";

    public static final Integer PASSWORD_SALTLEN = 6;
    public static final String PASSWORD_ENCRYPTALGO = "sha-256";

    // 以下为字典名称定义
    public static final String DICTNAME_ORGTYPE = "orgType";     //机构类型
    public static final String DICTNAME_ORGRANK = "orgRank";     //机构级别

    //字典是否启用状态
    public static final String USEABLE_NORMAL = "0"; //正常
    public static final String USEABLE_STOP = "1";  //停用

    //字典内置、自定义
    //内置
    public static final String STD_TYPE_IN = "0";
    //自定义
    public static final String STD_TYPE_OUT = "1";

    //树顶级的父级id
    public static final String ROOTID = "-1";

    // 菜单相关
    //顶级菜单的父级id
    public static final String MENU_TOPPARENT = "-1";
    //菜单类型
    //顶级菜单
    public static final String MENUTYPE_DIRECTORY = "1";
    //左菜单
    public static final String MENUTYPE_MENU = "2";
    //按钮
    public static final String MENUTYPE_BUTTON = "3";
    //菜单是否显示
    //是
    public static final String MENU_HIDDEN_YES = "0";
    //否
    public static final String MENU_HIDDEN_NO = "1";

    public static final String MENUHIDDEN_NO = "0";
    public static final String MENUHIDDEN_YES = "1";

    //缓存的名称以及key
    //登录用户信息
    public static final String IAM_LOGIN = "iam-login";
    //ip配置信息
    public static final String IP_CONFIG = "ip-config";
    public static final String APP_CODE_KEY = "appCode";
    public static final String USER_KEY = "user";

    // 角色相关
    // 内置
    public static final String BUILT_IN = "0";
    // 自定义
    public static final String USER_DEFINED = "1";

    //角色是否可用
    //启用
    public static final String ART_USING = "0";
    //停用
    public static final String BLOCK_UP = "1";

    //应用是否生效
    //生效
    public static final String APP_IS_USE = "0";
    //停用
    public static final String APP_NO_USE = "1";

    // 菜单打开方式
    public static final String MENUOPENTYPE_DEFAULT = "1";
    public static final String MENUOPENTYPE_IFRAME = "2";

    //树状节点类型
    public static final String TREE_TYPE_OFFICE = "office"; //机构
    public static final String TREE_TYPE_ROLE = "role";  //角色
    public static final String TREE_TYPE_USER = "user";  //用户

    //日志类型
    //操作日志
    public static final String LOG_TYPE_OPERATE = "0";
    //错误日志
    public static final String LOG_TYPE_ERROR = "1";

    //登录日志状态
    //成功
    public static final String LOGIN_STATUS_SUCCEED = "0";
    //失败
    public static final String LOGIN_STATUS_ERROR = "1";


    /**
     * redis 图形验证码键
     */
    public static final String CAPTCHA_SESSION_KEY = "zyaud_captcha";

    /**
     * 统一权限系统后端配置
     */
    public static final String IAM_HD_CONFIG = "0";

    /**
     * 数据分析工作台系统配置
     */
    public static final String WORKBENCH_SYSTEM_CONFIG = "2";

    /**
     * 密码默认超时时间(天)
     * 0天表示永不过期
     */
    public static final Integer DEFAULT_PWD_PAST_DUE_TIME = 0;
    /**
     * 回收站默认超时时间(天)
     */
    public static final Integer DEFAULT_RECYCLE_PAST_DUE_TIME = 30;
    /**
     * 慢日志查询阈值(秒)
     */
    public static final Integer DEFAULT_SLOW_SQL_TIME = 60;
    /**
     * 默认关闭单终端登录
     */
    public static final String DEFAULT_ONE_WAY_LOGIN = "0";
    /**
     * 默认不向syslog服务发送登录失败日志
     */
    public static final String DEFAULT_NO_SEND_SYSLOG = "0";

    /**
     * 开启推送日志服务
     */
    public static final String CONFIG_SEND_SYSLOG = "1";

    /**
     * 系统配置
     */
    public static final String SYS_CONFIG = "0";
    /**
     * IP配置
     */
    public static final String SYS_IPCONFIG = "1";
    /**
     * 系统布局
     */
    public static final String SYS_DISPLAY = "2";
    /**
     * 系统配置及安扫配置
     */
    public static final String SYS_SCAN_CONFIG = "3";
    /**
     * 登录页配置
     */
    public static final String LOGIN_PAGE_CONFIG = "4";


    // 下载任务状态
    public static final String DOWNLOADSTATUS_INIT = "0";
    public static final String DOWNLOADSTATUS_ING = "1";
    public static final String DOWNLOADSTATUS_SUCC = "2";
    public static final String DOWNLOADSTATUS_FAIL = "3";

    // 菜单创建方
    public static final String CREATORTYPE_INNER = "0";

    // 默认密码
    public static final String DEFAULT_PASSWORD = "1caf46b88231758fb27b942a20a22f68";

    public static final String NO = "0";
    public static final String YES = "1";


    //文件格式
    public static final String ZIP = "zip";
    public static final String PDF = "pdf";
    public static final String DOC = "doc";
    public static final String DOCX = "docx";
    public static final String XLSX = "xlsx";
    public static final String XLS = "xls";
    public static final String JPG = "jpg";
    public static final String BMP = "bmp";
    public static final String PNG = "png";
    public static final String JPEG = "jpeg";
    public static final String TXT = "txt";
    public static final String CSV = "csv";
    public static final String IMG = "img";
    public static final String UNKNOW = "unknow";

    //appframe成功调用
    public static final String appFrameRsp = "200";

    //不存在的机构标识
    public static final String NULL_OFFICE = "NULL_OFFICE";

    //导入数据库数据支持的文件格式
    public static final Set<String> IMPORT_FILE_SUFFIX = new HashSet<String>() {{
        add(CSV);
        add(XLS);
        add(XLSX);
    }};

    //可直接预览的文件类型
    public final static Set<String> IMAGES = new HashSet<String>() {{
        add(JPG);
        add(JPEG);
        add(PNG);
        add(BMP);
    }};

    //可直接预览的文件类型
    public final static Set<String> VIEWS = new HashSet<String>() {{
        add(PDF);
        add(JPG);
        add(JPEG);
        add(PNG);
        add(BMP);
    }};

    //需要转换才能预览的文件类型
    public final static Set<String> TOVIEWS = new HashSet<String>() {{
        add(DOC);
        add(DOCX);
        add(XLS);
        add(XLSX);
        add(TXT);
        add(CSV);
    }};


    //文件不支持预览html
    public static final String NOTSUPPORT = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>Document</title>\n" +
            "    <style>\n" +
            "        .notSupport {\n" +
            "            width:100%;\n" +
            "            height:500px;\n" +
            "            background: #fff;\n" +
            "            background-image: url('/static/img/NotSupport.png');\n" +
            "            background-repeat: no-repeat;\n" +
            "            background-position: 50% 50%;\n" +
            "            padding-top:30px;\n" +
            "        }\n" +
            "        .tips_title {\n" +
            "            font-weight: bold;\n" +
            "            font-size:30px;\n" +
            "            text-align: center;\n" +
            "            letter-spacing: 2px;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <div class=\"notSupport\">\n" +
            "        <p class=\"tips_title\">该文件暂不支持预览！</p>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>";

    //文件不支持预览html
    public static final String START_NOTSUPPORT = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>Document</title>\n" +
            "    <style>\n" +
            "        .notSupport {\n" +
            "            width:100%;\n" +
            "            height:500px;\n" +
            "            background: #fff;\n" +
            "            background-image: url('/static/img/NotSupport.png');\n" +
            "            background-repeat: no-repeat;\n" +
            "            background-position: 50% 50%;\n" +
            "            padding-top:30px;\n" +
            "        }\n" +
            "        .tips_title {\n" +
            "            font-weight: bold;\n" +
            "            font-size:30px;\n" +
            "            text-align: center;\n" +
            "            letter-spacing: 2px;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <div class=\"notSupport\">\n" +
            "        <p class=\"tips_title\">";

    //文件不支持预览html
    public static final String END_NOTSUPPORT = "</p>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>";

    public static List<String> getList() {
        List<String> dictTypes = new ArrayList<>();
        dictTypes.add("dasTreeType");
        dictTypes.add("standardType");
        return dictTypes;
    }

    public static final String BASE64 = "data:image;base64,";

    public static String getImageUrl(String groupName, String fileId) {
        byte[] bytes = null;
        try {
            bytes = FileUploadUtil.getBytes(groupName, fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ObjectUtil.isEmpty(bytes)) {
            return "";
        }
        Base64 base64 = new Base64();
        return BASE64 + base64.encodeToString(bytes);
    }
}
