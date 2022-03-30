package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.idata.iam.biz.model.dto.MenuCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.MenuPageDTO;
import com.zyaud.idata.iam.biz.model.dto.MenuUpdateDTO;
import com.zyaud.idata.iam.biz.model.dto.in.MenuCreateInDTO;
import com.zyaud.idata.iam.biz.model.dto.in.MenuDelInDTO;
import com.zyaud.idata.iam.biz.model.entity.Menu;
import com.zyaud.idata.iam.biz.model.vo.MenuTreeVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface IMenuService extends IService<Menu> {
    Map<String /*menuCode*/, String /*menuId*/> createMenuByBizApp(String appCode, MenuCreateInDTO createDTO);

    Result<String> deleteMenu(String appCode, MenuDelInDTO delInDTO);

    List<MenuTreeVO> getMenuTree(MenuPageDTO menuPageDTO);

    List<MenuTreeVO> getMenuTreeNeButton(MenuPageDTO menuPageDTO);

    List<Menu> getButtonList(MenuPageDTO menuPageDTO);

    List<MenuTreeVO> findMenuTreeNodeList(String appCode);

    boolean createMenu(MenuCreateDTO createDTO);

    boolean updateMenu(MenuUpdateDTO updateDTO);
}
