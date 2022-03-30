package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.biz.model.entity.Config;
import com.zyaud.idata.iam.biz.model.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2021-02-08
 */
public interface IConfigService extends IService<Config> {
    void saveConfig(List<IpConfigVO> ipConfigs);

    Boolean verifyPwdWhethePastDuer();

    /**
     * 根据配置项编码获取配置
     *
     * @param ctype
     * @param ccode
     * @return
     */
    Config findConfigByCtypeAndCcode(String ctype, String ccode);

    /**
     * 根据配置和appId获取列表数据
     *
     * @param config
     * @param appId
     * @return
     */
    List<IpConfigVO> findIpConfigVOByConfigAndAppId(Config config, String appId);

    List<String> getIps(List<? extends IpSettingVO> ipconfigs);

    void setHDConfig(HDConfigVO configRepVO);

    HDConfigVO getHDConfig();

    BizSysConfigVO getSysCfgItemByCode(String appCode);

    boolean updateAppAndScanConfig(BizSysConfigVO reqVO);

    BizSysConfigVO getSysConfigByAppCode(String appCode);

    boolean updateLoginPageConfig(LoginPageConfigReqVO reqVO);

    LoginPageConfigRspVO getLoginPageConfig();

    CheckFileExistRspVO exist(Serializable md5);

    /**
     * 文件上传
     *
     * @param name  文件名
     * @param md5   文件md5值
     * @param size  文件大小
     * @param total 总片数
     * @param chunk 当前片数
     * @param file  文件
     * @return
     * @throws IOException
     */
    FileUploadRspVO uploadImage(String name,
                                String md5,
                                Long size,
                                Integer total,
                                Integer chunk,
                                MultipartFile file) throws IOException;
}
