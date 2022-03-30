package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zyaud.fzhx.cache.service.CacheService;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.common.utils.JsonUtils;
import com.zyaud.fzhx.iam.core.FzhxIam;
import com.zyaud.idata.foundation.util.utils.FileUploadUtil;
import com.zyaud.idata.iam.biz.enums.FileBizTypeEnum;
import com.zyaud.idata.iam.biz.mapper.ConfigMapper;
import com.zyaud.idata.iam.biz.model.entity.Config;
import com.zyaud.idata.iam.biz.model.entity.UserCode;
import com.zyaud.idata.iam.biz.model.vo.*;
import com.zyaud.idata.iam.biz.service.IConfigService;
import com.zyaud.idata.iam.biz.service.IFileInfoService;
import com.zyaud.idata.iam.biz.service.IUserCodeService;
import com.zyaud.idata.iam.common.errorcode.ConfigMngErrorEnum;
import com.zyaud.idata.iam.common.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2021-02-08
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

    @Resource
    private IFileInfoService fileInfoService;
    @Resource
    private CacheService cacheService;

    @Override
    public void saveConfig(List<IpConfigVO> ipConfigs) {
        String jsonString = JsonUtils.toJson(ipConfigs);
        Config config = new Config();
        config.setCtype("0")
                .setCcode("1")
                .setCitem("ip配置")
                .setCvalue(jsonString);
        this.save(config);
    }


    @Resource
    private IUserCodeService userCodeService;

    @Override
    public Boolean verifyPwdWhethePastDuer() {
        boolean whethePastDuer = false;

        HDConfigVO hdConfigVO = getHDConfig();

        if (hdConfigVO.getPastDueTime() != 0) {
            String userId = FzhxIam.getUser().getUserId();
            UserCode userCode = userCodeService.getById(userId);
            Date pwdUpdateTime = userCode.getPwdUpdateTime();
            if (ObjectUtil.isEmpty(pwdUpdateTime)) {
                userCode.setPwdUpdateTime(new Date(System.currentTimeMillis()));
                userCodeService.updateById(userCode);
                pwdUpdateTime = userCode.getPwdUpdateTime();
            }
            long nowTimeLong = new Date(System.currentTimeMillis()).getTime();
            long pwdUpdateLong = pwdUpdateTime.getTime();
            long deadline = ((pwdUpdateLong - nowTimeLong) / (1000 * 3600 * 24));
            ;
            if (deadline + hdConfigVO.getPastDueTime() <= 0) {
                whethePastDuer = true;
            }
        }
        return whethePastDuer;
    }


    /**
     * 根据配置项编码获取配置
     *
     * @param ctype
     * @param ccode
     * @return
     */
    @Override
    public Config findConfigByCtypeAndCcode(String ctype, String ccode) {
        return baseMapper.findConfigByCtypeAndCcode(ctype, ccode);
    }

    @Override
    public List<IpConfigVO> findIpConfigVOByConfigAndAppId(Config config, String appId) {
        if (ObjectUtil.isNull(config)) {
            return new ArrayList<>();
        }
        if (StrUtil.isEmpty(config.getCvalue())) {
            return new ArrayList<>();
        }
        List<IpConfigVO> ipConfigVOS = JsonUtils.toList(config.getCvalue(), IpConfigVO.class);
        if (ObjectUtil.isEmpty(ipConfigVOS)) {
            return new ArrayList<>();
        }
        if (StrUtil.isEmpty(appId)) {
            return ipConfigVOS;
        } else {
            return ipConfigVOS.stream().filter(t -> t.getAppId().equals(appId)).collect(Collectors.toList());
        }
    }

    @Override
    public List<String> getIps(List<? extends IpSettingVO> ipconfigs) {
        List<String> ips = new ArrayList<>();
        for (IpSettingVO ipconfig : ipconfigs) {
            ips.add(ipconfig.getFrist() + "." + ipconfig.getSecond() + "." + ipconfig.getThird() + "." + ipconfig.getFourth());
        }
        return ips;
    }

    @Override
    public void setHDConfig(HDConfigVO configRepVO) {
        //校验系统配置是否正确
        BizAssert.isTrue(ObjectUtil.isNotEmpty(configRepVO.getPastDueTime())
                && configRepVO.getPastDueTime() >= 0, ConfigMngErrorEnum.PAST_DUE_TIME_CANNOT_LESS_THAN_ZERO);
        BizAssert.isNotEmpty(configRepVO.getIsOneWayLogin(), ConfigMngErrorEnum.CHOOSE_IF_ONE_WAY_LOGIN);
        BizAssert.isNotEmpty(configRepVO.getIsSendSyslog(), ConfigMngErrorEnum.CHOOSE_IF_SEND_SYSLOG);

        //根据前端传来的配置生成实体类
        Config config = getHDConfigByRepVO(configRepVO);

        //更新配置
        this.setConfig(config);
    }

    @Override
    public HDConfigVO getHDConfig() {
        Config config = getConfig(getHDConfigByRepVO(new HDConfigVO()
                .setIsOneWayLogin(Constants.DEFAULT_ONE_WAY_LOGIN)
                .setPastDueTime(Constants.DEFAULT_PWD_PAST_DUE_TIME)
                .setIsSendSyslog(Constants.DEFAULT_NO_SEND_SYSLOG)));
        HDConfigVO hdConfigVO = JsonUtils.parse(config.getCvalue(), HDConfigVO.class);
        return hdConfigVO;
    }

    @Override
    public BizSysConfigVO getSysCfgItemByCode(String appCode) {
        Config config = findConfigByCtypeAndCcode(Constants.SYS_CONFIG, Constants.SYS_SCAN_CONFIG);
        if (ObjectUtil.isNull(config)) {
            return null;
        }

        List<BizSysConfigVO> configVOS = JsonUtils.parse(config.getCvalue(), new TypeReference<List<BizSysConfigVO>>() {
        });

        return configVOS.stream().filter(c -> appCode.equals(c.getAppCode())).findFirst().orElse(null);
    }

    @Override
    public boolean updateAppAndScanConfig(BizSysConfigVO reqVO) {
        cacheService.del(Config.SYS_SCAN_CONFIG_CACHE_KEY);

        Config config = this.findConfigByCtypeAndCcode(Constants.SYS_CONFIG, Constants.SYS_SCAN_CONFIG);
        if (ObjectUtil.isNull(config)) {
            config = new Config();
            config.setCtype(Constants.SYS_CONFIG)
                    .setCcode(Constants.SYS_SCAN_CONFIG)
                    .setCitem("系统配置及安扫配置")
                    .setOrderIndex(0);
        }
        List<BizSysConfigVO> sysConfigs = new ArrayList<>();
        if (StrUtil.isNotBlank(config.getCvalue())) {
            sysConfigs = JsonUtils.parse(config.getCvalue(), new TypeReference<List<BizSysConfigVO>>() {
            });
        }

        boolean flag = false;
        for (BizSysConfigVO tmp : sysConfigs) {
            if (tmp.getAppCode().equals(reqVO.getAppCode())) {
                BeanUtil.copyProperties(reqVO, tmp);
                flag = true;
                break;
            }
        }

        if (!flag) {
            sysConfigs.add(reqVO);
        }
        config.setCvalue(JsonUtils.toJson(sysConfigs));

        if (StrUtil.isBlank(config.getId())) {
            return this.save(config);
        }

        return this.updateById(config);
    }

    public Config getHDConfigByRepVO(HDConfigVO repVO) {
        Config config = new Config()
                .setCtype(Constants.SYS_CONFIG)
                .setCcode(Constants.IAM_HD_CONFIG)
                .setCitem("后端配置")
                .setOrderIndex(1)
                .setCvalue(JsonUtils.toJson(repVO));

        return config;
    }


    /**
     * 根据传来的config条件进行查询配置，
     * 并且如果查询不到相关配置，则建立默认配置
     *
     * @param config
     * @return
     */
    public Config getConfig(Config config) {

        List<Config> configList = this.baseMapper.listConfigByCTypeAndCCode(config.getCtype(), config.getCcode());

        //如果没有获取到配置则建立默认配置并返回默认过期时间
        if (ObjectUtil.isEmpty(configList) || StrUtil.isBlank(configList.get(0).getId())) {
            this.save(config);
            return config;
        }

        //有获取到配置则取第一个返回
        return configList.get(0);
    }

    /**
     * 设置配置信息，
     * 先根据配置信息的type和code获取配置信息，
     * 如果没有获取到配置信息则就新增配置信息，
     * 如果有获取到配置信息就编辑配置信息
     *
     * @param config
     */
    public void setConfig(Config config) {
        if (ObjectUtil.isEmpty(config)) {
            return;
        }
        List<Config> configList = this.baseMapper.listConfigByCTypeAndCCode(config.getCtype(), config.getCcode());

        //如果没有获取到配置则进行新增操作，如果获取到就编辑操作
        if (ObjectUtil.isEmpty(configList) || StrUtil.isEmpty(configList.get(0).getId())) {
            this.save(config);
        } else {
            config.setId(configList.get(0).getId());
            this.updateById(config);
        }
    }

    @Override
    public BizSysConfigVO getSysConfigByAppCode(String appCode) {
        BizSysConfigVO vo = cacheService.get(Constants.IAM_LOGIN, Config.SYS_SCAN_CONFIG_CACHE_KEY);
        if (ObjectUtil.isNotNull(vo)) {
            return vo;
        }

        Config config = this.baseMapper.findConfigByCtypeAndCcode(Constants.SYS_CONFIG, Constants.SYS_SCAN_CONFIG);
        if (ObjectUtil.isNull(config) || StrUtil.isBlank(config.getCvalue())) {
            return null;
        }

        List<BizSysConfigVO> configVOS = JsonUtils.parse(config.getCvalue(), new TypeReference<List<BizSysConfigVO>>() {
        });

        return configVOS.stream().filter(c -> appCode.equals(c.getAppCode())).findFirst().orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateLoginPageConfig(LoginPageConfigReqVO reqVO) {
        Config config = this.findConfigByCtypeAndCcode(Constants.SYS_CONFIG, Constants.LOGIN_PAGE_CONFIG);
        if (ObjectUtil.isNull(config)) {
            config = new Config();
            config.setCtype(Constants.SYS_CONFIG)
                    .setCcode(Constants.LOGIN_PAGE_CONFIG)
                    .setCitem("登录页面配置")
                    .setOrderIndex(0);
        }

        LoginPageConfigReqVO cfg = null;
        if (StrUtil.isNotBlank(config.getCvalue())) {
            cfg = JsonUtils.parse(config.getCvalue(), LoginPageConfigReqVO.class);
        }

        config.setCvalue(JsonUtils.toJson(reqVO));
        boolean res = StrUtil.isBlank(config.getId()) ? this.save(config) : this.updateById(config);
        BizAssert.isTrue(res, "保存新登录页面配置失败!");

        if (ObjectUtil.isNull(cfg)) { // 原来未配置，则直接添加
            if (ObjectUtil.isNotEmpty(reqVO.getIconFile())
                    && StrUtil.isNotBlank(reqVO.getIconFile().getFileId())
                    && StrUtil.isNotBlank(reqVO.getIconFile().getGroupName())) {
                res = fileInfoService.batchAddFile(new ArrayList<FileStoreInfoVO>() {{
                                                       add(reqVO.getIconFile());
                                                   }}, config.getId(),
                        FileBizTypeEnum.USER_ICON);
                BizAssert.isTrue(res, "新增登录页面图标失败!");
            }
            if (ObjectUtil.isNotEmpty(reqVO.getLoginPageFiles())) {
                res = fileInfoService.batchAddFile(reqVO.getLoginPageFiles(), config.getId(), FileBizTypeEnum.HOME_BANNER);
                BizAssert.isTrue(res, "新增登录页面轮播图失败!");
            }
        } else {
            // 替换图标
            if (ObjectUtil.isNotEmpty(reqVO.getIconFile()) &&
            StrUtil.isNotBlank(reqVO.getIconFile().getFileId()) &&
                    !reqVO.getIconFile().equals(cfg.getIconFile())) {
                if (ObjectUtil.isNotEmpty(cfg.getIconFile()) && StrUtil.isNotBlank(cfg.getIconFile().getFileId())) {
                    fileInfoService.removeById(cfg.getIconFile().getFileId());
                }
                res = fileInfoService.batchAddFile(new ArrayList<FileStoreInfoVO>() {{
                                                       add(reqVO.getIconFile());
                                                   }}, config.getId(),
                        FileBizTypeEnum.USER_ICON);
                BizAssert.isTrue(res, "新增登录页面图标失败!");
            }

            // 替换banner图
            if (ObjectUtil.isNotNull(cfg.getLoginPageFiles())) {
                // 求交集并且不改变原列表
                List<FileStoreInfoVO> storeInfoVOS = new ArrayList<>();
                for (FileStoreInfoVO vo1 : cfg.getLoginPageFiles()) {
                    for (FileStoreInfoVO vo2 : reqVO.getLoginPageFiles()) {
                        if (vo1.equals(vo2)) {
                            storeInfoVOS.add(vo1);
                            break;
                        }
                    }
                }

                // 找出要删除的(原来有，但新的没有)
                cfg.getLoginPageFiles().removeAll(storeInfoVOS);
                if (ObjectUtil.isNotEmpty(cfg.getLoginPageFiles())) {
                    fileInfoService.deleteByBizIds(
                            cfg.getLoginPageFiles().stream().map(FileStoreInfoVO::getFileId).collect(Collectors.toList())
                    );
                }

                // 找出改变的(包括新增的)
                reqVO.getLoginPageFiles().removeAll(storeInfoVOS);
                if (ObjectUtil.isNotEmpty(reqVO.getLoginPageFiles())) {
                    res = fileInfoService.batchAddFile(reqVO.getLoginPageFiles(), config.getId(),
                            FileBizTypeEnum.HOME_BANNER);
                    BizAssert.isTrue(res, "更新登录页面轮播图失败!");
                }
            }
        }

        return true;
    }

    @Override
    public LoginPageConfigRspVO getLoginPageConfig() {
        LoginPageConfigRspVO vo = new LoginPageConfigRspVO();
        Config config = this.findConfigByCtypeAndCcode(Constants.SYS_CONFIG, Constants.LOGIN_PAGE_CONFIG);
        if (ObjectUtil.isNull(config) || StrUtil.isBlank(config.getCvalue())) {
            return vo;
        }

        vo = JsonUtils.parse(config.getCvalue(), LoginPageConfigRspVO.class);
        if (ObjectUtil.isEmpty(vo)) {
            return vo;
        }

        FileStoreInfoVO iconFile = vo.getIconFile();
        if (ObjectUtil.isNotEmpty(iconFile) &&
                StrUtil.isNotBlank(iconFile.getGroupName()) &&
                StrUtil.isNotBlank(iconFile.getFileId())) {
            vo.setIconFileUrl(Constants.getImageUrl(iconFile.getGroupName(), iconFile.getFileId()));
        }

        List<String> loginPageImgUrls = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(vo.getLoginPageFiles())) {
            for (FileStoreInfoVO fs : vo.getLoginPageFiles()) {
                if (ObjectUtil.isNotEmpty(fs) &&
                        StrUtil.isNotBlank(fs.getGroupName()) &&
                        StrUtil.isNotBlank(fs.getFileId())) {
                    loginPageImgUrls.add(Constants.getImageUrl(fs.getGroupName(), fs.getFileId()));
                }
            }
        }
        vo.setLoginPageFileUrls(loginPageImgUrls);

        return vo;
    }

    @Override
    public CheckFileExistRspVO exist(Serializable md5) {
        CheckFileExistRspVO vo = fileInfoService.exist(md5);
        if (StrUtil.isNotBlank(vo.getFileType()) && Constants.IMAGES.contains(vo.getFileType().toLowerCase())) {
            vo.setImgUrl(Constants.getImageUrl(vo.getGroupName(), vo.getFileId()));
        }
        return vo;
    }

    @Override
    public FileUploadRspVO uploadImage(String name, String md5, Long size, Integer total, Integer chunk,
                                       MultipartFile file) throws IOException {
        FileUploadRspVO vo = fileInfoService.upload(name, md5, size, total, chunk, file);
        vo.setImgUrl(Constants.getImageUrl(vo.getGroupName(), vo.getFileId()));
        return vo;
    }
}
