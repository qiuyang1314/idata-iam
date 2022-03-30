package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyaud.fzhx.binder.annotation.BindResult;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.idata.iam.biz.mapper.UserMapper;
import com.zyaud.idata.iam.biz.model.entity.User;
import com.zyaud.idata.iam.biz.model.entity.UserCode;
import com.zyaud.idata.iam.biz.model.vo.UserVO;
import com.zyaud.idata.iam.biz.service.IAppService;
import com.zyaud.idata.iam.biz.service.IRoleUserCodeService;
import com.zyaud.idata.iam.biz.service.IUserCodeService;
import com.zyaud.idata.iam.biz.service.IUserService;
import com.zyaud.idata.iam.common.errorcode.UserMngErrorEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private IUserCodeService userCodeService;

    @Resource
    private IAppService appService;

    @Resource
    private IRoleUserCodeService roleUserCodeService;

    @Override
    public User login() {
        return null;
    }

    @Override
    public Map<String, String> getUserNameById(Set<Serializable> ids) {
        if (ObjectUtil.isNotEmpty(ids)) {
            List<User> users = this.listByIds(ids);
            Map<String, String> collect = users.stream().collect(Collectors.toMap(t -> t.getId(), t -> t.getName()));
            return collect;
        } else {
            return new HashMap<>();
        }
    }

    @Override
    @BindResult
    public List<UserVO> getUserVOs(List<User> users) {
        if (ObjectUtil.isNotEmpty(users)) {
            List<UserVO> userVOS = users.stream().map(t -> BeanUtil.toBean(t, UserVO.class)).collect(Collectors.toList());
            //获取账号信息写入
            Set<String> userIds = userVOS.stream().map(t -> t.getId()).collect(Collectors.toSet());
            List<UserCode> userCodes = userCodeService.findUserCodeListByUserIds(userIds);
            for (UserVO userVO : userVOS) {
                Optional<UserCode> userCode = userCodes.stream().filter(t -> t.getUserId().equals(userVO.getId())).findFirst();
                userCode.ifPresent(t -> userVO.setUserCodeId(t.getId()));
            }
            return userVOS;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean verifyIdCard(String idCard, String id) {
        if (StrUtil.isNotBlank(idCard)) {
            Long count = baseMapper.countByIdCard(idCard, id);
            BizAssert.isTrue(count == 0, UserMngErrorEnum.IDCARD_IS_EXIST);
        }

        return true;
    }

}
