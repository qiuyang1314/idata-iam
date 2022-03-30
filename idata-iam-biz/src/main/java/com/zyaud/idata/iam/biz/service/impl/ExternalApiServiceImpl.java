package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.idata.iam.biz.model.entity.Office;
import com.zyaud.idata.iam.biz.model.entity.User;
import com.zyaud.idata.iam.biz.model.entity.UserCode;
import com.zyaud.idata.iam.biz.model.vo.UserCodeAndUserAndAppAndOfficeInfoVO;
import com.zyaud.idata.iam.biz.service.*;
import com.zyaud.idata.iam.common.errorcode.AccountMngErrorEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ExternalApiServiceImpl implements IExternalApiService {
    @Resource
    private IUserCodeService userCodeService;
    @Resource
    private IUserService userService;
    @Resource
    private IOfficeService officeService;
    @Resource
    private IStdCodeService stdCodeService;

    @Override
    public List<UserCodeAndUserAndAppAndOfficeInfoVO> getUserCodeAndUserAndAppAndOfficeInfoByUserCodeIds(List<String> userCodeIds) {
        if (userCodeIds.size() == 0) {
            BizAssert.fail(AccountMngErrorEnum.USER_CODE_IDS_CANNOT_BE_EMPTY);
        }
        //统一封装对象
        List<UserCodeAndUserAndAppAndOfficeInfoVO> userCodeAndAppAndOfficeInfoVOS = new ArrayList<>();
        //账号信息
        List<UserCode> userCodes = userCodeService.listByIds(userCodeIds);
        List<UserCodeAndUserAndAppAndOfficeInfoVO.UserCodeInfo> userCodeInfos = userCodes.stream().map(t -> BeanUtil.toBean(t, UserCodeAndUserAndAppAndOfficeInfoVO.UserCodeInfo.class)).collect(Collectors.toList());
        for (UserCodeAndUserAndAppAndOfficeInfoVO.UserCodeInfo userCodeInfo : userCodeInfos) {
            //账号类型字典值转换
            Set<String> type = userCodes.stream().map(t -> t.getType()).collect(Collectors.toSet());
            Map<String, String> accountType = stdCodeService.getDictData("accountType", type);
            UserCodeAndUserAndAppAndOfficeInfoVO userCodeAndAppAndOfficeInfoVO = new UserCodeAndUserAndAppAndOfficeInfoVO();
            String accountTypeName = accountType.get(userCodeInfo.getType());
            if (StrUtil.isNotEmpty(accountTypeName)) {
                userCodeInfo.setTypeName(accountTypeName);
            }

            userCodeAndAppAndOfficeInfoVO.setUserCodeInfo(userCodeInfo);
            userCodeAndAppAndOfficeInfoVOS.add(userCodeAndAppAndOfficeInfoVO);
        }

        if (userCodeInfos.size() > 0) {
            //用户信息
            List<String> userIds = userCodeInfos.stream().map(t -> t.getUserId()).collect(Collectors.toList());
            List<User> users = userService.listByIds(userIds);
            List<UserCodeAndUserAndAppAndOfficeInfoVO.UserInfo> userInfos = users.stream().map(t -> BeanUtil.toBean(t, UserCodeAndUserAndAppAndOfficeInfoVO.UserInfo.class)).collect(Collectors.toList());

            //机构信息
            List<String> officeIds = users.stream().map(t -> t.getOfficeId()).collect(Collectors.toList());
            List<Office> offices = officeService.listByIds(officeIds);
            List<UserCodeAndUserAndAppAndOfficeInfoVO.OfficeInfo> officeInfos = offices.stream()
                    .map(t -> BeanUtil.toBean(t, UserCodeAndUserAndAppAndOfficeInfoVO.OfficeInfo.class)).collect(Collectors.toList());
            for (UserCodeAndUserAndAppAndOfficeInfoVO userCodeAndAppAndOfficeInfoVO : userCodeAndAppAndOfficeInfoVOS) {
                //用户信息
                Optional<UserCodeAndUserAndAppAndOfficeInfoVO.UserInfo> userInfo = userInfos.stream()
                        .filter(t -> t.getId().equals(userCodeAndAppAndOfficeInfoVO.getUserCodeInfo().getUserId())).findFirst();
                userInfo.ifPresent(t -> userCodeAndAppAndOfficeInfoVO.setUserInfo(t));
                //机构信息
                Optional<UserCodeAndUserAndAppAndOfficeInfoVO.OfficeInfo> officeInfo = officeInfos.stream()
                        .filter(t -> t.getId().equals(userCodeAndAppAndOfficeInfoVO.getUserInfo().getOfficeId())).findFirst();
                officeInfo.ifPresent(t -> userCodeAndAppAndOfficeInfoVO.setOfficeInfo(t));
            }
        }
        return userCodeAndAppAndOfficeInfoVOS;
    }
}
