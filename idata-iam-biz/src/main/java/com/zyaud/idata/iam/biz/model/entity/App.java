package com.zyaud.idata.iam.biz.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zyaud.fzhx.core.model.TimeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 系统应用
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(App.APP)
public class App extends TimeEntity<String> {

    private static final long serialVersionUID = 1L;
    public static final String APP = "iam_app";
    public static final String APPNAME = "app_name";
    public static final String APPCODE = "app_code";
    public static final String URL = "url";
    public static final String IMG = "img";
    public static final String ORDERINDEX = "order_index";
    public static final String APPKEY = "app_key";
    public static final String APPSECRET = "app_secret";
    public static final String USEABLE = "useable";
    public static final String OPENTYPE = "open_type";

    /**
     * 系统应用名字
     */
    @TableField(APPNAME)
    @Length(max = 64, message = "系统应用名字长度不能超过64")
    private String appName;


    /**
     * 系统应用地址
     */
    @TableField(URL)
    @Length(max = 64, message = "系统应用地址长度不能超过64")
    private String url;

    /**
     * 应用图片
     */
    @TableField(IMG)
    @Length(max = 64, message = "应用图片长度不能超过64")
    private String img;
    /**
     * 排序
     */
    @TableField(ORDERINDEX)
    private Integer orderIndex;

    /**
     * 系统应用公钥(账号)
     */
    @TableField(APPKEY)
    private String appKey;


    /**
     * 系统应用私钥(密码)
     */
    @TableField(APPSECRET)
    private String appSecret;


    /**
     * 是否生效
     */
    @TableField(USEABLE)
    @Length(max = 1, message = "是否生效长度不能超过1")
    private String useable;

    /**
     * 打开方式
     */
    @TableField(OPENTYPE)
    @Length(max = 10, message = "是否生效长度不能超过10")
    private String openType;

    /**
     * 应用编码
     */
    @TableField(APPCODE)
    @Length(max = 20, message = "应用编码长度不能超过20")
    private String appCode;

}
