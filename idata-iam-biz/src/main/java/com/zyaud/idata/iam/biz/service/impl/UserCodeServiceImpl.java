package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.common.utils.JsonUtils;
import com.zyaud.fzhx.iam.core.FzhxIam;
import com.zyaud.fzhx.iam.core.FzhxIamUser;
import com.zyaud.idata.foundation.infra.client.vo.SysAreaInfoRspVO;
import com.zyaud.idata.iam.biz.mapper.OfficeMapper;
import com.zyaud.idata.iam.biz.mapper.RoleUserCodeMapper;
import com.zyaud.idata.iam.biz.mapper.UserCodeMapper;
import com.zyaud.idata.iam.biz.mapper.UserMapper;
import com.zyaud.idata.iam.biz.model.entity.*;
import com.zyaud.idata.iam.biz.model.vo.UserBaseInfoRspVO;
import com.zyaud.idata.iam.biz.model.vo.UserCodeRoleReqVO;
import com.zyaud.idata.iam.biz.service.IMenuService;
import com.zyaud.idata.iam.biz.service.IRoleMenuService;
import com.zyaud.idata.iam.biz.service.IRoleService;
import com.zyaud.idata.iam.biz.service.IUserCodeService;
import com.zyaud.idata.iam.common.errorcode.AccountMngErrorEnum;
import com.zyaud.idata.iam.common.errorcode.UserMngErrorEnum;
import com.zyaud.idata.iam.common.utils.Constants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户账号 服务实现类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
@Service
public class UserCodeServiceImpl extends ServiceImpl<UserCodeMapper, UserCode> implements IUserCodeService {

    @Resource
    private UserCodeMapper userCodeMapper;
    @Resource
    private RoleUserCodeMapper roleUserCodeMapper;
    @Resource
    private IRoleMenuService roleMenuService;
    @Resource
    private IRoleService roleService;
    @Resource
    private IMenuService menuService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private SysAreaService sysAreaService;
    @Resource
    private OfficeMapper officeMapper;

    @Override
    public UserCode getByLoginName(String loginName) {
        return this.baseMapper.getByLoginName(loginName);
    }

    @Override
    public FzhxIamUser getFzhxIamUserByLoginName(UserCode userCode) {
        //拥有的角色
        List<Role> roles = new ArrayList<>();
        //拥有的菜单权限
        List<Menu> menus = new ArrayList<>();
        //获取角色
        List<RoleUserCode> roleUserCodes = roleUserCodeMapper.listRoleUserCodeByUserCodeIds(Arrays.asList(userCode.getId()));
        if (roleUserCodes.size() > 0) {
            Set<String> roleIds = roleUserCodes.stream().map(RoleUserCode::getRoleId).collect(Collectors.toSet());
            roles = roleService.listByIds(roleIds);
            //获取菜单
            List<RoleMenu> roleMenus = roleMenuService.findRoleMenuListByRoleIds(roleIds);
            if (roleMenus.size() > 0) {
                Set<String> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toSet());
                menus = menuService.listByIds(menuIds);
            }
        }
        //角色名
        List<String> roleNames = roles.stream().map(Role::getRoleName).collect(Collectors.toList());
        //权限
        List<String> permsList = menus.stream().map(Menu::getPerms).collect(Collectors.toList());


        FzhxIamUser fzhxIamUser = new FzhxIamUser()
                .setUserId(userCode.getId())
                .setName(userCode.getLoginName())
                .setPermissions(new HashSet<>(permsList))
                .setRoles(new HashSet(roleNames));
        //拓展数据
        Map<String, Object> ext = new HashMap<>();
        //生成tno
        String tno = String.valueOf(DateUtil.current());
        ext.put("tno", tno);
        //用户
        if (StrUtil.isNotEmpty(userCode.getUserId())) {
            User user = userMapper.selectById(userCode.getUserId());
            if (ObjectUtil.isNotEmpty(user)) {
                //机构
                ext.put("domain", user.getOfficeId());
                //行政区划
                Office office = officeMapper.selectById(user.getOfficeId());
                if (ObjectUtil.isNotEmpty(office)) {
                    ext.put("domainName", office.getName());
                    SysAreaInfoRspVO sysArea = sysAreaService.getById(office.getDistrictId());
                    if (ObjectUtil.isNotEmpty(sysArea)) {
                        ext.put("areaId", office.getDistrictId());
                        ext.put("areaCode", sysArea.getAreaCode());
                        ext.put("areaName", sysArea.getAreaName());
                    }
                }
            }
        }
        //写入拓展数据
        fzhxIamUser.setExt(ext);
        return fzhxIamUser;
    }

    @Override
    @Cacheable(cacheNames = Constants.IAM_LOGIN, key = "'" + Constants.USER_KEY + "'" + "+#loginName", unless = "#result == null")
    public FzhxIamUser getFzhxIamUserByLoginName(String loginName) {
        UserCode userCode = getByLoginName(loginName);
        if (ObjectUtil.isEmpty(userCode)) {
            BizAssert.fail(AccountMngErrorEnum.USER_CODE_IS_NOT_EXIST);
        }
        return getFzhxIamUserByLoginName(userCode);
    }

    @Override
    public List<UserCode> findUserCodeListByUserIds(Set<String> userIds) {

        QueryWrapper<UserCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(ObjectUtil.isNotEmpty(userIds), UserCode.USERID, userIds);
        return this.list(queryWrapper);
    }

    @Override
    public boolean verify(String loginName, String id) {
        QueryWrapper<UserCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(UserCode.LOGINNAME, loginName)
                .ne(StrUtil.isNotEmpty(id), UserCode.ID, id);
        return (this.count(queryWrapper) > 0);
    }

    @Override
    public UserCode getUserCodeByUserId(String userId) {
        return userCodeMapper.getUserCodeByUserId(userId);
    }

    @Override
    public boolean setAppDefaultRole(UserCodeRoleReqVO reqVO) {
        UserCode userCode = getById(FzhxIam.getUser().getUserId());
        Map<String, String> roleMap;
        if (StrUtil.isNotBlank(userCode.getDefaultRoles())) {
            roleMap = JsonUtils.parse(userCode.getDefaultRoles(), HashMap.class);
        } else {
            roleMap = new HashMap<>();
        }
        roleMap.put(reqVO.getAppCode(), reqVO.getRoleId());

        userCode.setDefaultRoles(JsonUtils.toJson(roleMap, true));

        return this.updateById(userCode);
    }

    @Override
    public UserBaseInfoRspVO getUserBaseInfo() {
        String userCodeId = FzhxIam.getUser().getUserId();
        UserCode userCode = this.getById(userCodeId);
        BizAssert.isTrue(ObjectUtil.isNotNull(userCode), UserMngErrorEnum.USERCODE_NOTEXIST);
        BizAssert.isTrue(StrUtil.isNotBlank(userCode.getUserId()), UserMngErrorEnum.USER_NOTSET);
        User user = userMapper.selectById(userCode.getUserId());
        UserBaseInfoRspVO vo = BeanUtil.toBean(user, UserBaseInfoRspVO.class);

        vo.setUserId(user.getId());
        vo.setId(userCodeId).setLoginName(userCode.getLoginName())
                .setStatus(userCode.getStatus()).setType(userCode.getType());

        return vo;
    }


}
