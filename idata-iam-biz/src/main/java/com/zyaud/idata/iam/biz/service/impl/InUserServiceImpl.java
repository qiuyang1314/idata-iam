package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.idata.iam.biz.mapper.UserCodeMapper;
import com.zyaud.idata.iam.biz.mapper.UserMapper;
import com.zyaud.idata.iam.biz.model.dto.UserCreateDTO;
import com.zyaud.idata.iam.biz.model.entity.User;
import com.zyaud.idata.iam.biz.model.entity.UserCode;
import com.zyaud.idata.iam.biz.model.vo.UserCodeAASVO;
import com.zyaud.idata.iam.biz.model.vo.UserVO;
import com.zyaud.idata.iam.biz.service.IInUserService;
import com.zyaud.idata.iam.biz.service.IUserCodeService;
import com.zyaud.idata.iam.client.dto.UserInDTO;
import com.zyaud.idata.iam.common.errorcode.UserMngErrorEnum;
import com.zyaud.idata.iam.common.utils.LambdaStream;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class InUserServiceImpl extends ServiceImpl<UserMapper, User> implements IInUserService {
    @Resource
    private IUserCodeService userCodeService;

    @Resource
    private UserCodeMapper userCodeMapper;


    @Override
    public boolean create(UserCreateDTO createDTO) {
        if (ObjectUtil.isNotEmpty(createDTO.getIdCard())) {
            List<User> userList = baseMapper.findUserListByIdCard(createDTO.getIdCard());
            if (userList.size() > 0) {
                log.error("新增失败:身份证号已存在");
                return false;
            }
        }

        User entity = BeanUtil.toBean(createDTO, User.class);
        return this.save(entity);
    }

    @Override
    public List<UserInDTO> turnUserList2UserVOList(List<User> userList) {
        List<UserInDTO> voList = LambdaStream.toList(userList, u -> BeanUtil.toBean(u, UserInDTO.class));
        //获取账号信息写入
        Set<String> userIds = LambdaStream.toSet(voList, UserInDTO::getId);
        List<UserCode> userCodes = userCodeService.findUserCodeListByUserIds(userIds);
		// 修改实现，原实现方式会导致一部分userCode被排除在外
        for (UserCode uc: userCodes) {
            for (UserInDTO userVO : voList) {
                if (uc.getUserId().equals(userVO.getId())) {
                    userVO.setUserCodeId(uc.getId());
                    userVO.setLoginName(uc.getLoginName());
                    break;
                }
            }
        }

        return voList;
    }

    @Override
    public List<UserInDTO> list(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(userName), User.NAME, userName);
        List<User> users = this.list(queryWrapper);
        return this.turnUserList2UserVOList(users);
    }

    @Override
    public Map<String, String> getUserNamesByIds(List<String> userCodeIds) {
        // 新建返回值
        Map<String, String> codeIdNameMap = new HashMap<>();

        // 参数检测
        if (ObjectUtil.isEmpty(userCodeIds)) {
            return codeIdNameMap;
        }

        // 获取账号信息
        List<UserCode> userCodes = userCodeService.listByIds(userCodeIds);

        // 获取关联用户信息
        Set<String> userIds = LambdaStream.toSet(userCodes, UserCode::getUserId);
        if (userIds.size() < 1) {
            return codeIdNameMap;
        }
        List<User> userList = baseMapper.selectBatchIds(userIds);

        // 组装返回数据
        Map<String, String> userNameMap = LambdaStream.toMap(userList, User::getId, User::getName);
        userCodes.forEach(t -> codeIdNameMap.put(t.getId(), userNameMap.get(t.getUserId())));
        return codeIdNameMap;
    }

    @Override
    public List<UserCode> findUserCodeListByName(String name) {
        List<User> userList = baseMapper.findUserListByName(name);
        Set<String> userIds = LambdaStream.toSet(userList, User::getId);
        return userCodeMapper.listUserCodeByUserIds(userIds);
    }

    @Override
    public List<UserCodeAASVO> findUserListByOfficeId(String officeId) {
        List<User> userList = baseMapper.findUserListByOfficeId(officeId);
        BizAssert.isFalse(userList.size() < 1, UserMngErrorEnum.USER_IS_NOT_EXIST_IN_THIS_OFFICE);

        List<UserCodeAASVO> userCodeVOS = new ArrayList<>();
        Set<String> userIds = LambdaStream.toSet(userList, User::getId);
        List<UserCode> userCodeList = userCodeService.findUserCodeListByUserIds(userIds);
        userCodeList.forEach(t -> {
            UserCodeAASVO vo = BeanUtil.toBean(t, UserCodeAASVO.class);
            vo.setName(t.getLoginName());
            userCodeVOS.add(vo);
        });

        if (StrUtil.isNotBlank(officeId)) {
            userCodeVOS.forEach(u -> u.setOfficeId(officeId));
        } else {
            userCodeVOS.forEach(u -> {
                List<User> filterUserList = LambdaStream.toFilterList(userList, t -> t.getId().equalsIgnoreCase(u.getUserId()));
                if (ObjectUtil.isNotEmpty(filterUserList)) {
                    User user = filterUserList.get(0);
                    u.setOfficeId(user.getOfficeId());
                    u.setName(u.getName() + "(" + user.getName() + ")"); // 后续建议由前端组装
                }
            });
        }

        return userCodeVOS;
    }
}
