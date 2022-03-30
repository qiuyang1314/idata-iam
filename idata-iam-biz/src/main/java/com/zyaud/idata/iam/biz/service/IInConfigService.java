package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.biz.model.entity.Config;
import com.zyaud.idata.iam.biz.model.vo.BizSysConfigVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2021-02-08
 */
public interface IInConfigService extends IService<Config> {

    List<Config> findConfigByCTypeAndCCode(String cType, String cCode);

    /**
     * 设置系统配置
     *
     * @param config
     */
    void createConfig(Config config);

    void updateConfig(Config entity);

    BizSysConfigVO getSysConfigByAppCode(String appCode);
}
