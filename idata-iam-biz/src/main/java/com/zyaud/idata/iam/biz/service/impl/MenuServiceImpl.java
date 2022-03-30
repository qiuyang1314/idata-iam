package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.common.utils.TreeUtils;
import com.zyaud.fzhx.core.mybatis.QueryBuilder;
import com.zyaud.idata.iam.biz.mapper.MenuMapper;
import com.zyaud.idata.iam.biz.model.dto.MenuCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.MenuPageDTO;
import com.zyaud.idata.iam.biz.model.dto.MenuUpdateDTO;
import com.zyaud.idata.iam.biz.model.dto.in.MenuCreateInDTO;
import com.zyaud.idata.iam.biz.model.dto.in.MenuCreateParamInDTO;
import com.zyaud.idata.iam.biz.model.dto.in.MenuDelInDTO;
import com.zyaud.idata.iam.biz.model.entity.App;
import com.zyaud.idata.iam.biz.model.entity.Menu;
import com.zyaud.idata.iam.biz.model.entity.RoleMenu;
import com.zyaud.idata.iam.biz.model.entity.StdCode;
import com.zyaud.idata.iam.biz.model.vo.MenuTreeVO;
import com.zyaud.idata.iam.biz.service.IAppService;
import com.zyaud.idata.iam.biz.service.IMenuService;
import com.zyaud.idata.iam.biz.service.IRoleMenuService;
import com.zyaud.idata.iam.biz.service.IStdCodeService;
import com.zyaud.idata.iam.common.errorcode.MenuMngErrorEnum;
import com.zyaud.idata.iam.common.utils.Constants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Resource
    private IAppService appService;

    @Resource
    private IRoleMenuService roleMenuService;

    @Resource
    private IStdCodeService stdCodeService;


    @Override
    public Map<String /*menuCode*/, String /*menuId*/> createMenuByBizApp(String appCode, MenuCreateInDTO createDTO) {
        Map<String, String> errMap = new HashMap<>();
        // 检测系统应用编号
        App app = appService.getAppByAppCode(appCode);
        if (ObjectUtils.isEmpty(app)) {
            errMap.put("error", "新增菜单失败:AppCode非法，未找到应用");
            log.error(errMap.get("error"));
            return errMap;
        }

        List<MenuCreateParamInDTO> createParams = createDTO.getCreateParams();
        List<Menu> menuList = new ArrayList<>();
        List<Menu> retMenuList = new ArrayList<>();
        for (MenuCreateParamInDTO cp : createParams) {
            // 检测父节点菜单是目录而非菜单
            if (!Constants.MENU_TOPPARENT.equals(cp.getParentId())) {
                Menu parent = this.getById(cp.getParentId());
                if (ObjectUtil.isEmpty(parent)) {
                    errMap.put("error", "新增菜单失败:父节点菜单不存在");
                    log.error(errMap.get("error"));
                    return errMap;
                }

                if (!parent.getType().equals(Constants.MENUTYPE_DIRECTORY)) {
                    errMap.put("error", "新增菜单失败:父节点菜单不是目录类型");
                    log.error(errMap.get("error"));
                    return errMap;
                }
            }

            // 拆出目录
            String[] menuNames = cp.getMenuName().split("/");
            List<String> menuNameList = new ArrayList<>(menuNames.length);
            Collections.addAll(menuNameList, menuNames);
            menuNameList.removeIf(StrUtil::isBlankIfStr);
            // 只支持2级菜单
            if (menuNameList.size() > 2) {
                BizAssert.fail("菜单路径中只允许包含1个目录");
            }

            List<Menu> tempList;
            boolean createFlag;
            String parentId = Constants.MENU_TOPPARENT;
            for (int i = 0; i < menuNameList.size(); i++) {
                // 检测菜单名称
                if (i == 0) {
                    parentId = cp.getParentId();
                }
                // 检测是否已经创建
                // 先在本次添加的菜单中找
                Iterator<Menu> it = menuList.iterator();
                createFlag = true;
                while (it.hasNext()) {
                    Menu temp = it.next();
                    if (temp.getParentId().equals(parentId) && temp.getName().equals(menuNameList.get(i))) {
                        createFlag = false;
                        parentId = temp.getId();
                        break;
                    }
                }
                // 找到则继续下一个
                if (!createFlag) {
                    continue;
                }

                // 未找到，则在数据库中找
                tempList = this.baseMapper.findMenuListByParentIdAndName(parentId, menuNameList.get(i));
                if (tempList.size() > 0) {
                    parentId = tempList.get(0).getId();
                    retMenuList.add(tempList.get(0));
                    continue;
                }

                // 最终的菜单才需要检测code
                if (i == (menuNameList.size() - 1)) {
                    tempList = this.baseMapper.findMenuListByMenuCode(cp.getMenuCode());
                    if (tempList.size() > 0) {
                        errMap.put("error", "新增菜单失败:[" + menuNameList.get(i) + "]菜单已经存在，不能重复发布");
                        log.error(errMap.get("error"));
                        return errMap;
                    }
                }

                // 添加菜单
                Menu menu = createMenu(app, cp, menuNameList.get(i), parentId, i != (menuNameList.size() - 1));
                menuList.add(menu);
                parentId = menu.getId();

                // 添加到返回
                if (Constants.MENUTYPE_MENU.equals(menu.getType())) {
                    retMenuList.add(menu);
                }
            }
        }

        // 保存
        this.saveBatch(menuList);

        try {
            System.err.println("menuList:" + retMenuList.toString());
            Map<String, String> codeIdMap = retMenuList.stream().collect(Collectors.toMap(Menu::getCode, Menu::getId));
            return codeIdMap;
        } catch (Exception e) {
            errMap.put("error", "新增菜单失败:" + e.getMessage());
            log.error(errMap.get("error"));
            return errMap;
        }
    }

    private Menu createMenu(App app, MenuCreateParamInDTO cp, String menuName, String parentId, boolean isDir) {
        Menu menu = new Menu();
        menu.setId(IdUtil.fastSimpleUUID());
        menu.setParentId(parentId);
        menu.setParentIds(null);
        menu.setAppId(app.getId());
        menu.setName(menuName);
        menu.setCode(isDir ? null : cp.getMenuCode());
        menu.setPath(cp.getPath());
        menu.setSite(cp.getSite());
        menu.setOpenType(cp.getOpenType());
        menu.setMenuParam(cp.getMenuParam());
        menu.setType(isDir ? Constants.MENUTYPE_DIRECTORY : Constants.MENUTYPE_MENU);
        menu.setOrderIndex(0);
        if (Constants.MENU_TOPPARENT.equals(parentId)) {
            menu.setIcon("default_1");
        } else {
            menu.setIcon("default_2");
        }
        menu.setImg(null);
        menu.setHidden(Constants.MENUHIDDEN_NO);
        menu.setPerms(null);
        return menu;
    }

    @Override
    public List<MenuTreeVO> getMenuTree(MenuPageDTO menuPageDTO) {
        Wrapper<Menu> query = QueryBuilder.queryWrapper(menuPageDTO);
        List<Menu> list = ObjectUtil.isEmpty(query) ? this.list() : this.list(query);
        return getMenuTreeByWrapper(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> deleteMenu(String appCode, MenuDelInDTO delInDTO) {

        // 检测系统应用编号
        App app = appService.getAppByAppCode(appCode);
        if (ObjectUtils.isEmpty(app)) {
            return Result.fail("删除菜单失败:AppCode非法，未找到应用");
        }

        // 删除菜单
        if (!this.removeByIds(delInDTO.getMenuIds())) {
            return Result.fail("删除菜单失败:未找到菜单项!");
        }

        // 删除角色菜单
        List<RoleMenu> roleMenus = roleMenuService.findRoleMenuListByMenuIds(new HashSet<>(delInDTO.getMenuIds()));
        if (ObjectUtil.isNotEmpty(roleMenus)) {
            roleMenuService.removeByIds(delInDTO.getMenuIds());
        }

        return Result.ok("删除菜单成功");
    }

    @Override
    public List<MenuTreeVO> getMenuTreeNeButton(MenuPageDTO menuPageDTO) {
        if (StrUtil.isBlank(menuPageDTO.getAppId())) {
            return new ArrayList<>();
        }
        List<Menu> menus = this.baseMapper.findMenusByAppIdAndNameNeType(menuPageDTO.getAppId(),
                menuPageDTO.getName(), Constants.MENUTYPE_BUTTON);
        return getMenuTreeByWrapper(menus);
    }

    @Override
    public List<Menu> getButtonList(MenuPageDTO menuPageDTO) {
        if (StrUtil.isBlank(menuPageDTO.getParentId())) {
            return new ArrayList<>();
        }
        List<Menu> menus = this.baseMapper.findMenusByParentIdAndNameAndType(menuPageDTO.getParentId(),
                menuPageDTO.getName(), Constants.MENUTYPE_BUTTON);
        return menus;
    }

    private List<MenuTreeVO> getMenuTreeByWrapper(List<Menu> menus) {
        List<MenuTreeVO> menuTreeVOS = new ArrayList<>();
        menus.forEach(t -> menuTreeVOS.add(BeanUtil.toBean(t, MenuTreeVO.class)));
        if (menuTreeVOS.size() > 0) {
            Set<String> openType = menuTreeVOS.stream().map(MenuTreeVO::getOpenType).collect(Collectors.toSet());
            List<StdCode> stdCodes = stdCodeService.findStdCodeByStdTypesAndCodeNameAndCodeValues(
                    Arrays.asList("menuOpenWay"), null, openType);
            for (MenuTreeVO menuTreeVO : menuTreeVOS) {
                Optional<StdCode> first = stdCodes.stream().filter(t -> t.getCodeValue().equals(menuTreeVO.getOpenType())).findFirst();
                first.ifPresent(t -> menuTreeVO.setOpenTypeName(t.getCodeName()));
            }
        }
        return TreeUtils.buildByRecursive(menuTreeVOS, Constants.MENU_TOPPARENT);
    }

    @Override
    public List<MenuTreeVO> findMenuTreeNodeList(String appCode) {
        // 检测系统应用编号
        App app = appService.getAppByAppCode(appCode);
        if (ObjectUtils.isEmpty(app)) {
            log.error("获取菜单失败:AppCode非法，未找到应用");
            return new ArrayList<>();
        }

        List<MenuTreeVO> voList = new ArrayList<>();
        List<Menu> menuList = this.baseMapper.findMenuListByAppId(app.getId());
        menuList.forEach(m -> voList.add(BeanUtil.toBean(m, MenuTreeVO.class)));
        return voList;
    }

    @Override
    public boolean createMenu(MenuCreateDTO createDTO) {
        Menu entity = BeanUtil.toBean(createDTO, Menu.class);
        this.verifyParent(entity);
        return this.save(entity);
    }

    @Override
    public boolean updateMenu(MenuUpdateDTO updateDTO) {
        Menu entity = BeanUtil.toBean(updateDTO, Menu.class);
        this.verifyParent(entity);
        return this.updateById(entity);
    }

    public void verifyParent(Menu menu){
        if (ObjectUtil.isEmpty(menu)){
            return;
        }
        if (ObjectUtil.isEmpty(menu.getParentId())) {
            menu.setParentId(Constants.ROOTID);
        }
        if (!Constants.ROOTID.equals(menu.getParentId())) {
            Menu parentMenu = this.getById(menu.getParentId());
            BizAssert.isNotEmpty(parentMenu, MenuMngErrorEnum.PARENT_MENU_IS_NULL);
            BizAssert.isTrue(!Constants.MENUTYPE_BUTTON.equals(parentMenu.getType()), MenuMngErrorEnum.PARENT_MENU_IS_BUTTON);
        }

        BizAssert.isTrue(!(!Constants.MENUTYPE_BUTTON.equals(menu.getType()) &&
                ObjectUtil.isEmpty(menu.getOpenType())), MenuMngErrorEnum.OPENTYPE_IS_NULL);
    }
}
