package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zyaud.fzhx.cache.service.CacheService;
import com.zyaud.fzhx.common.utils.JsonUtils;
import com.zyaud.idata.iam.biz.mapper.ConfigMapper;
import com.zyaud.idata.iam.biz.model.entity.Config;
import com.zyaud.idata.iam.biz.model.vo.BizSysConfigVO;
import com.zyaud.idata.iam.biz.service.IInConfigService;
import com.zyaud.idata.iam.common.utils.Constants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2021-02-08
 */
@Service
public class InConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IInConfigService {
    @Resource
    private CacheService cacheService;

    @Override
    public List<Config> findConfigByCTypeAndCCode(String cType, String cCode) {
        return this.baseMapper.listConfigByCTypeAndCCode(cType, cCode);
    }

    @Override
    public void createConfig(Config config) {
        if (verifyConfig(config)) {
            this.save(config);
        }
    }

    @Override
    public void updateConfig(Config entity) {
        Config config = this.getById(entity.getId());
        if (ObjectUtil.isEmpty(config)) {
            log.error("该配置项已被删除请重试");
            return;
        }

        if (verifyConfig(entity)) {
            this.updateById(entity);
        }

    }

    @Override
    public BizSysConfigVO getSysConfigByAppCode(String appCode) {
        BizSysConfigVO vo = cacheService.get(Constants.IAM_LOGIN,Config.SYS_SCAN_CONFIG_CACHE_KEY);
        if (ObjectUtil.isNotNull(vo)) {
            return vo;
        }

        Config config = this.baseMapper.findConfigByCtypeAndCcode(Constants.SYS_CONFIG, Constants.SYS_SCAN_CONFIG);
        if (ObjectUtil.isEmpty(config) || StrUtil.isBlank(config.getCvalue())) {
            return null;
        }

        List<BizSysConfigVO> configVOS = JsonUtils.parse(config.getCvalue(), new TypeReference<List<BizSysConfigVO>>() {
        });

        vo = configVOS.stream().filter(c -> c.getAppCode().equals(appCode)).findFirst().orElse(null);
        if (ObjectUtil.isNotNull(vo)) {
            cacheService.setExpire(Constants.IAM_LOGIN,Config.SYS_SCAN_CONFIG_CACHE_KEY, vo, 24 * 60 * 60);
        }

        return vo;
    }

    /**
     * 检查配置是否正确
     *
     * @param config
     * @return
     */
    public boolean verifyConfig(Config config) {
        if (ObjectUtil.isEmpty(config) ||
                StrUtil.isBlank(config.getCcode()) ||
                StrUtil.isBlank(config.getCitem()) ||
                StrUtil.isBlank(config.getCtype()) ||
                StrUtil.isBlank(config.getCvalue()) ||
                ObjectUtil.isEmpty(config.getOrderIndex())) {
            log.error("系统配置的各项内容不能为空:" + config.toString());
            return false;
        }
        return true;
    }
}
