package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyaud.fzhx.common.utils.TreeUtils;
import com.zyaud.idata.iam.biz.mapper.*;
import com.zyaud.idata.iam.client.dto.UserCodeInDTO;
import com.zyaud.idata.iam.biz.model.entity.*;
import com.zyaud.idata.iam.biz.model.vo.UserRoleRspVO;
import com.zyaud.idata.iam.biz.model.vo.UserTreeReqVO;
import com.zyaud.idata.iam.biz.model.vo.UserTreeVO;
import com.zyaud.idata.iam.biz.service.IInUserCodeService;
import com.zyaud.idata.iam.common.utils.Constants;
import com.zyaud.idata.iam.common.utils.PasswordEncryptor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InUserCodeServiceImpl extends ServiceImpl<UserCodeMapper, UserCode> implements IInUserCodeService {

    @Resource
    private AppMapper appMapper;

    @Resource
    private RoleUserCodeMapper roleUserCodeMapper;

    @Resource
    private UserCodeMapper userCodeMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private OfficeMapper officeMapper;

    private String calcCipher(String plain, String saltValue, String encryptAlgo) {
        PasswordEncryptor encoderMd5 = new PasswordEncryptor(saltValue, encryptAlgo);
        return encoderMd5.encode(plain);
    }

    private void encryptPassword(UserCode userCode, String plain, int saltLen, String encryptAlgo) {
        String saltValue = RandomUtil.randomNumbers(saltLen);
        userCode.setPasswd(calcCipher(plain, saltValue, encryptAlgo)).setSalt(saltValue);
    }

    @Override
    public List<UserCode> listUserCodeByAppCode(String appCode) {
        List<UserCode> userCodes = new ArrayList<>();
        if (StrUtil.isNotBlank(appCode)) {
            App app = appMapper.getAppByCode(appCode);
            List<RoleUserCode> roleUserCodeList = roleUserCodeMapper
                    .listRoleUserCodeByAppId(app.getId());
            List<String> userCodeId = roleUserCodeList.stream()
                    .map(RoleUserCode::getUserCodeId).collect(Collectors.toList());
            if (userCodeId.size() > 0) {
                userCodes = this.listByIds(userCodeId);
            }
        }

        return userCodes;
    }

    @Override
    public List<UserRoleRspVO> findUserRoleList(String appCode, String userCodeId) {
        List<UserRoleRspVO> userRoleRspVOS = new ArrayList<>();
        try {
            App app = appMapper.getAppByCode(appCode);
            List<RoleUserCode> roleUserCodes = roleUserCodeMapper.listRoleUserCodeByUserCodeIds(
                    new ArrayList<String>() {{
                        add(userCodeId);
                    }});
            roleUserCodes.removeIf(r -> !r.getAppId().equals(app.getId()));
            List<Role> roleList = roleMapper.selectBatchIds(
                    roleUserCodes.stream().map(RoleUserCode::getRoleId).collect(Collectors.toSet()));
            roleList.forEach(r -> {
                UserRoleRspVO userRoleRspVO = BeanUtil.toBean(r, UserRoleRspVO.class);
                userRoleRspVOS.add(userRoleRspVO);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userRoleRspVOS;
    }


    @Override
    public List<UserCodeInDTO> findUserCodeListByOfficeId(String officeId) {
        List<User> userList = userMapper.findUserListByOfficeId(officeId);
        if (ObjectUtil.isEmpty(userList)){
            return new ArrayList<>();
        }
        Set<String> userIds = userList.stream().map(User::getId).collect(Collectors.toSet());
        Map<String, String> userNameMap = userList.stream().collect(Collectors.toMap(User::getId, User::getName));
        List<UserCode> userCodeList = this.userCodeMapper.listUserCodeByUserIds(userIds);

        if (ObjectUtil.isEmpty(userCodeList)){
            return new ArrayList<>();
        }
        List<UserCodeInDTO> userCodeInDTOS = userCodeList.stream()
                .map(t -> BeanUtil.toBean(t, UserCodeInDTO.class)
                        .setUserName(userNameMap.get(t.getUserId()))).collect(Collectors.toList());
        return userCodeInDTOS;
    }

    /**
     * 机构用户下拉树形列表
     *
     * @param reqVO
     * @return
     */
    @Override
    public List<UserTreeVO> getOfficeUserTree(UserTreeReqVO reqVO) {
        // 处理机构
        List<UserTreeVO> userTreeVOS = getUserTreeVOS(reqVO);
        List<Office> offices = officeMapper.findAllOffice();
        if (ObjectUtil.isEmpty(offices)) {
            return new ArrayList<>();
        }
        offices.forEach(t -> {
            UserTreeVO treeVO = BeanUtil.toBean(t, UserTreeVO.class);
            treeVO.setType(Constants.TREE_TYPE_OFFICE);
            userTreeVOS.add(treeVO);
        });
        return userTreeVOS;
    }

    /**
     * 角色用户下拉树形列表
     *
     * @param reqVO
     * @return
     */
    @Override
    public List<UserTreeVO> getRoleUserTree(UserTreeReqVO reqVO) {
        List<UserTreeVO> userTreeVOS = getUserTreeVOS(reqVO);
        List<App> apps = appMapper.listAppByCodes(reqVO.getAppCodes());
        if (ObjectUtil.isEmpty(apps)) {
            return new ArrayList<>();
        }
        List<String> appIds = apps.stream().map(App::getId).collect(Collectors.toList());
        List<Role> roles = roleMapper.getByAppIds(appIds);
        if (ObjectUtil.isEmpty(roles)) {
            return new ArrayList<>();
        }

        roles.forEach(t -> {
            UserTreeVO treeVO = BeanUtil.toBean(t, UserTreeVO.class)
                    .setType(Constants.TREE_TYPE_ROLE).setName(t.getRoleName());
            treeVO.setParentId(Constants.ROOTID);
            userTreeVOS.add(treeVO);
        });

        return TreeUtils.buildByRecursive(userTreeVOS, Constants.ROOTID);
    }

    @Override
    public List<UserTreeVO> getUserTreeVOS(UserTreeReqVO reqVO) {
        List<UserTreeVO> userTreeVOS = new ArrayList<>();
        List<UserCode> userCodeList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(reqVO.getAppCodes())) {
            List<App> apps = appMapper.listAppByCodes(reqVO.getAppCodes());
            if (ObjectUtil.isEmpty(apps)) {
                return new ArrayList<>();
            }
            List<String> appIds = apps.stream().map(App::getId).collect(Collectors.toList());
            List<RoleUserCode> roleUserCodes = roleUserCodeMapper.listRoleUserCodeByAppIdsAndRoleIdsAndUserCodeIds(appIds, null, null);

            List<String> userCodeIds = roleUserCodes.stream().map(RoleUserCode::getUserCodeId).distinct().collect(Collectors.toList());
            if (ObjectUtil.isEmpty(userCodeIds)) {
                return new ArrayList<>();
            }

            userCodeList = userCodeMapper.selectBatchIds(userCodeIds);
        } else {
            userCodeList = userCodeMapper.findAllUserCode();
        }

        List<String> userIds = userCodeList.stream().map(UserCode::getUserId).distinct().collect(Collectors.toList());

        if (ObjectUtil.isEmpty(userIds)) {
            return new ArrayList<>();
        }

        Map<String, User> userMap = userMapper.selectBatchIds(userIds).stream().collect(Collectors.toMap(User::getId, t -> t));

        for (UserCode userCode : userCodeList) {
            User user = userMap.get(userCode.getUserId());
            if (ObjectUtil.isEmpty(user)) {
                continue;
            }
            //是否排除指定用户id
            if (ObjectUtil.isNotEmpty(reqVO.getUserIds()) && reqVO.getUserIds().contains(userCode.getId())) {
                continue;
            }

            UserTreeVO treeVO = new UserTreeVO();
            treeVO.setId(userCode.getId());
            treeVO.setName(userCode.getLoginName());
            treeVO.setUserId(user.getId());
            treeVO.setUserName(user.getName());
            treeVO.setParentId(user.getOfficeId());
            treeVO.setUserCodeType(userCode.getType());
            treeVO.setType(Constants.TREE_TYPE_USER);
            userTreeVOS.add(treeVO);

        }

        return userTreeVOS;
    }

}
