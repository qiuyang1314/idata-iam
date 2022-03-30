package com.zyaud.idata.iam.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyaud.fzhx.core.mybatis.QueryBuilder;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.idata.iam.biz.model.entity.App;
import com.zyaud.idata.iam.biz.model.entity.Config;
import com.zyaud.idata.iam.biz.model.vo.*;
import com.zyaud.idata.iam.biz.service.IAppService;
import com.zyaud.idata.iam.biz.service.IConfigService;
import com.zyaud.idata.iam.biz.service.IExternalApiService;
import com.zyaud.idata.iam.biz.service.IRoleUserCodeService;
import com.zyaud.idata.iam.common.utils.Constants;
import com.zyaud.idata.iam.common.vo.PublicAppCodeReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/system/external-api")
@Api(value = "ExternalApi", tags = "远程调用API模块")
public class ExternalApiController extends SuperController {

    @Autowired
    private IExternalApiService externalApiService;
    @Autowired
    private IAppService appService;
    @Autowired
    private IRoleUserCodeService roleUserCodeService;
    @Autowired
    private IConfigService configService;

    @ApiOperation("根据账号ids和应用编码获取账号、机构、用户、应用信息")
    @PostMapping(value = "/getUserCodeAndUserAndAppAndOfficeInfoByUserCodeIdsAndAppCode")
    public List<UserCodeAndUserAndAppAndOfficeInfoVO> getUserCodeAndUserAndAppAndOfficeInfoByUserCodeIdsAndAppCode(
            @Validated @RequestBody UserCodeIdsAndAppCodeVO userCodeIdsAndAppCodeVO) {
        App app = appService.getAppByAppCode(userCodeIdsAndAppCodeVO.getAppCode());
        if (ObjectUtil.isEmpty(app)) {
            return new ArrayList<>();
        }
        List<UserCodeAndAppAndRoleInfoVO> userCodeAndAppAndRoleInfo = roleUserCodeService.getUserCodeAndAppAndRoleInfoByUserCodeIdsAndAppIdsAndRoleIds(userCodeIdsAndAppCodeVO.getUserCodeIds(), Arrays.asList(app.getId()), null);
        List<String> userCodeIds = userCodeAndAppAndRoleInfo.stream().map(t -> t.getUserCodeInfo().getId()).collect(Collectors.toList());
        if (userCodeIds.size() > 0) {
            List<UserCodeAndUserAndAppAndOfficeInfoVO> userCodeAndUserAndAppAndOfficeInfo = externalApiService.getUserCodeAndUserAndAppAndOfficeInfoByUserCodeIds(userCodeIds);
            userCodeAndUserAndAppAndOfficeInfo.forEach(t -> t.setAppInfo(BeanUtil.toBean(app, UserCodeAndUserAndAppAndOfficeInfoVO.AppInfo.class)));
            return userCodeAndUserAndAppAndOfficeInfo;
        }

        return new ArrayList<>();
    }


    @ApiOperation("根据应用编码ip配置")
    @PostMapping(value = "/getConfigByAppCode")
    public IpConfigVO getConfigByAppCode(@Validated @RequestBody PublicAppCodeReqVO appCodeVO) {
        App app = appService.getAppByAppCode(appCodeVO.getAppCode());
        if (ObjectUtil.isEmpty(app)) {
            return null;
        }
        //获取配置
        Config config = configService.findConfigByCtypeAndCcode(Constants.SYS_CONFIG, Constants.SYS_IPCONFIG);
        //获取应用下的ip配置
        List<IpConfigVO> ipConfigVOS = configService.findIpConfigVOByConfigAndAppId(config, app.getId());
        if (ipConfigVOS.size() == 0) {
            return null;
        }
        IpConfigVO ipConfigVO = ipConfigVOS.get(0);
        return ipConfigVO;
    }

    @ApiOperation("根据条件返回所有应用")
    @PostMapping({"/findApps"})
    public List<FindAppsRspVO> findApps(@RequestBody FindAppsReqVO reqVO) {
        QueryWrapper<App> query = QueryBuilder.queryWrapper(reqVO);
        query.orderByAsc(App.ORDERINDEX);
        List<App> list = appService.list(query);
        return list.stream().map(t -> BeanUtil.toBean(t, FindAppsRspVO.class))
                .collect(Collectors.toList());
    }

}
