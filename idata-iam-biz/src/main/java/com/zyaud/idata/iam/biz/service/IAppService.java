package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.biz.model.dto.AppCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.AppPageDTO;
import com.zyaud.idata.iam.biz.model.dto.AppUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.App;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 系统应用 服务类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface IAppService extends IService<App> {
    Map<String, String> getAppNameById(Set<Serializable> ids);

    Map<String, String> getAppNameByAppCodes(Set<Serializable> appCodes);

    /**
     * 新增应用
     *
     * @param createDTO
     * @return
     */
    boolean createApp(AppCreateDTO createDTO);

    /**
     * 编辑应用
     *
     * @param updateDTO
     * @return
     */
    boolean updateApp(AppUpdateDTO updateDTO);

    /**
     * 分页列表
     *
     * @param pageDTO
     * @return
     */
    IPage appPage(AppPageDTO pageDTO);

    /**
     * 重置公钥私钥
     *
     * @param id
     * @return
     */
    boolean reset(String id);

    /**
     * 启用/禁用
     *
     * @param id
     * @return
     */
    boolean isUseable(String id);

    App getAppByAppCode(String appCode);

    App getAppByAppName(String appName);

    List<App> findAppByIsuseable();

    List<String> findAppCodeByIsuseable();
}
