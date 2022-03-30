package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.core.mybatis.QueryBuilder;
import com.zyaud.idata.iam.biz.mapper.AppMapper;
import com.zyaud.idata.iam.biz.model.dto.AppCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.AppPageDTO;
import com.zyaud.idata.iam.biz.model.dto.AppUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.App;
import com.zyaud.idata.iam.biz.model.vo.AppVO;
import com.zyaud.idata.iam.biz.service.IAppService;
import com.zyaud.idata.iam.common.errorcode.AppMngErrorEnum;
import com.zyaud.idata.iam.common.utils.Constants;
import com.zyaud.idata.iam.common.utils.RSAUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.KeyPair;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统应用 服务实现类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements IAppService {

    @Override
    public Map<String, String> getAppNameById(Set<Serializable> ids) {
        if (ObjectUtil.isNotEmpty(ids)) {
            List<App> apps = this.listByIds(ids);
            Map<String, String> collect = apps.stream().collect(Collectors.toMap(t -> t.getId(), t -> t.getAppName()));
            return collect;
        } else {
            return new HashMap<>();
        }
    }

    @Override
    public Map<String, String> getAppNameByAppCodes(Set<Serializable> appCodes) {
        if (ObjectUtil.isNotEmpty(appCodes)) {
            List<App> apps = this.baseMapper.listAppByCodes(appCodes);
            Map<String, String> collect = apps.stream().collect(Collectors.toMap(t -> t.getAppCode(), t -> t.getAppName()));
            return collect;
        } else {
            return new HashMap<>();
        }
    }

    @Override
    public boolean createApp(AppCreateDTO createDTO) {
        //校验
        verify(createDTO.getAppName(), createDTO.getAppCode(), null);
        //入库
        App entity = BeanUtil.toBean(createDTO, App.class);
        // 生成公钥私钥
        KeyPair keyPair = RSAUtils.generateKeyPair();
        entity.setAppKey(RSAUtils.getPublicKeyString(keyPair))
                .setAppSecret(RSAUtils.getPrivateKeyString(keyPair));
        return this.save(entity);
    }

    @Override
    public boolean updateApp(AppUpdateDTO updateDTO) {
        //校验
        verify(updateDTO.getAppName(), updateDTO.getAppCode(), updateDTO.getId());
        //修改
        App entity = BeanUtil.toBean(updateDTO, App.class);
        return this.updateById(entity);
    }

    @Override
    public IPage appPage(AppPageDTO pageDTO) {
        QueryWrapper<App> query = QueryBuilder.queryWrapper(pageDTO);
        query.orderByAsc(App.ORDERINDEX);
        IPage<App> page = this.page(pageDTO.getPage(), query);
        IPage iPage = new Page(page.getCurrent(), page.getSize(), page.getTotal());
        List<AppVO> appVOS = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(page.getRecords())) {
            page.getRecords().forEach(t -> appVOS.add(BeanUtil.toBean(t, AppVO.class)
                    .setIsuseable(Constants.APP_IS_USE.equals(t.getUseable()) ? true : false)));
        }
        iPage.setRecords(appVOS);
        return iPage;
    }

    @Override
    public boolean reset(String id) {
        App app = this.getById(id);
        BizAssert.isTrue(ObjectUtil.isNotNull(app), AppMngErrorEnum.APP_IS_NULL);
        // 生成公钥私钥
        KeyPair keyPair = RSAUtils.generateKeyPair();
        app.setAppKey(RSAUtils.getPublicKeyString(keyPair))
                .setAppSecret(RSAUtils.getPrivateKeyString(keyPair));
        return this.updateById(app);
    }

    @Override
    public boolean isUseable(String id) {
        App app = this.getById(id);
        BizAssert.isNotEmpty(app, AppMngErrorEnum.APP_IS_NULL);
        if (Constants.APP_IS_USE.equals(app.getUseable())) {
            app.setUseable(Constants.APP_NO_USE);
        } else {
            app.setUseable(Constants.APP_IS_USE);
        }
        return this.updateById(app);
    }

    @Override
    public App getAppByAppCode(String appCode) {
        return baseMapper.getAppByAppNameAndAppCode(null, appCode);
    }

    @Override
    public App getAppByAppName(String appName) {
        return baseMapper.getAppByAppNameAndAppCode(appName, null);
    }

    @Override
    public List<App> findAppByIsuseable() {
        return baseMapper.findAppByAppNameAndAppCode(null, null, Constants.APP_IS_USE);
    }

    @Override
    public List<String> findAppCodeByIsuseable() {
        return findAppByIsuseable().stream().map(t -> t.getAppCode()).collect(Collectors.toList());
    }

    public void verify(String appName, String appCode, String id) {
        //验证应用名唯一
        List<App> appByAppName = baseMapper.findAppByAppNameAndAppCodeAndNid(appName, null, id);
        BizAssert.isTrue(!(appByAppName.size() > 0), AppMngErrorEnum.APP_NAME_IS_EXIST);
        //验证编码唯一
        List<App> appByAppCode = baseMapper.findAppByAppNameAndAppCodeAndNid(null, appCode, id);
        BizAssert.isTrue(!(appByAppCode.size() > 0), AppMngErrorEnum.APP_CODE_IS_EXIST);
    }
}
