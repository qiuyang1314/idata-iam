package com.zyaud.idata.iam.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyaud.fzhx.binder.annotation.BindResult;
import com.zyaud.fzhx.core.model.IdEntity;
import com.zyaud.fzhx.core.mybatis.QueryBuilder;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.iam.annotation.PreAuthorize;
import com.zyaud.idata.foundation.common.model.dto.PublicIdsReqDTO;
import com.zyaud.idata.iam.biz.model.dto.UserCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.UserPageDTO;
import com.zyaud.idata.iam.biz.model.dto.UserUpdateDTO;
import com.zyaud.idata.iam.client.dto.UserCodeInDTO;
import com.zyaud.idata.iam.biz.model.entity.User;
import com.zyaud.idata.iam.biz.model.entity.UserCode;
import com.zyaud.idata.iam.biz.model.vo.*;
import com.zyaud.idata.iam.biz.service.IInUserCodeService;
import com.zyaud.idata.iam.biz.service.IInUserService;
import com.zyaud.idata.iam.client.dto.UserInDTO;
import com.zyaud.idata.iam.client.vo.PublicNameInVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息 前端控制器;
 * 切记:限于纪委产品内部系统间调用   ****
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
@Slf4j
@RestController
@RequestMapping("/system/inUser")
public class InUserController extends SuperController {
    @Resource
    private IInUserService crudService;

    @Resource
    private IInUserCodeService userCodeService;


    @ApiOperation(value = "新增一条记录")
    @PostMapping(value = "/create")
    //@SysLog(value = "新增", request = false)
    @PreAuthorize(value = "hasPermission('system:user:create')", msg = "您没有‘新增用户’的权限")
    public boolean create(@Validated UserCreateDTO createDTO) {
        return crudService.create(createDTO);
    }

    @ApiOperation(value = "删除记录")
    @PostMapping(value = "/delete")
    //@SysLog("'删除:' + #ids")
    @PreAuthorize(value = "hasPermission('system:user:delete')", msg = "您没有‘删除用户’的权限")
    public boolean delete(@RequestBody List<? extends Serializable> ids) {
        return crudService.removeByIds(ids);
    }

    @ApiOperation(value = "修改某条记录")
    @PostMapping(value = "/update")
    //@SysLog(value = "'修改:' + #updateDTO?.id", request = false)
    @PreAuthorize(value = "hasPermission('system:user:get')", msg = "您没有‘编辑用户’的权限")
    public boolean update(@Validated(IdEntity.Update.class) UserUpdateDTO updateDTO) {
        User entity = BeanUtil.toBean(updateDTO, User.class);
        return crudService.updateById(entity);
    }

    @ApiOperation(value = "单条记录查询")
    @PostMapping(value = "/get")
    //@SysLog("'查询:' + #id")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public User get(@RequestParam Serializable id) {
        return crudService.getById(id);
    }

    @ApiOperation("分页列表查询")
    @PostMapping({"/page"})
    //@SysLog(value = "'分页列表查询:第' + #pageDTO?.current + '页, 显示' + #pageDTO?.size + '行'", response = false)
    @BindResult
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public IPage<UserVO> page(@Validated UserPageDTO pageDTO) {
        Wrapper<User> query = QueryBuilder.queryWrapper(pageDTO);
        IPage<User> page = this.crudService.page(pageDTO.getPage(), query);
        IPage iPage = new Page(page.getCurrent(), page.getSize(), page.getTotal());
        iPage.setRecords(crudService.turnUserList2UserVOList(page.getRecords()));
        return iPage;
    }

    @ApiOperation("列表查询")
    @PostMapping({"/list"})
    public List<UserInDTO> list(@RequestBody PublicNameInVO publicNameVO) {
        return crudService.list(publicNameVO.getName());
    }

    @ApiOperation("根据id获取用户名称")
    @PostMapping(value = "/getUserById")
    public Map<String /* userCodeId */, String /* userName */> getUserNamesByIds(@RequestBody PublicIdListVO idListVO) {
        return crudService.getUserNamesByIds(idListVO.getIds());
    }


    @ApiOperation("根据用户名模糊查询用户账号")
    @PostMapping(value = "/getUserByName")
    public List<UserCodeInDTO> findUserCodeListByName(@Validated @RequestBody PublicIdListVO nameVO) {
        List<UserCode> userCodes = crudService.findUserCodeListByName(nameVO.getOperationName());
        List<UserCodeInDTO> userCodeInDTOS = userCodes.stream()
                .map(t -> BeanUtil.toBean(t, UserCodeInDTO.class)).collect(Collectors.toList());
        return userCodeInDTOS;
    }

    @ApiOperation("获取指定机构下的所有用户")
    @PostMapping(value = "/getUsersByOffice")
    public List<UserCodeAASVO> findUserListByOfficeId(@RequestBody @RequestParam(required = false) IdVO idVO) {
        return crudService.findUserListByOfficeId(ObjectUtil.isNotEmpty(idVO) ? idVO.getId() : null);
    }

    /**
     * 根据用户账号id获取对应的用户
     * 如果传过来的是空的则获取到的数据为空
     *
     * @param publicIdListVO
     * @return
     */
    @ApiOperation("根据用户账号id获取对应的用户")
    @PostMapping(value = "/getUserMapByUserCodeId")
    public Map<String/*用户账号id*/, UserInDTO/*用户账号对应用户*/> getUserMapByUserCodeId(@Validated @RequestBody PublicIdListVO publicIdListVO) {
        Map<String, UserInDTO> userInDTOMap = new HashMap<>();
        if (ObjectUtil.isEmpty(publicIdListVO.getIds())) {
            return userInDTOMap;
        }
        List<UserCode> userCodes = userCodeService.listByIds(publicIdListVO.getIds());
        Set<String> userIds = userCodes.stream().map(t -> t.getUserId()).collect(Collectors.toSet());
        if (userIds.size() == 0) {
            return userInDTOMap;
        }
        List<User> userList = crudService.listByIds(userIds);
        Map<String, UserInDTO> userNameMap = userList.stream()
                .collect(Collectors.toMap(User::getId, t -> BeanUtil.toBean(t, UserInDTO.class)));

        userCodes.forEach(t -> userInDTOMap.put(t.getId(), userNameMap.get(t.getUserId())));

        return userInDTOMap;
    }

    @ApiOperation("根据id获取用户名称")
    @PostMapping(value = "/getUserNameMapByUserCodeId")
    public Map<String/*用户账号id*/, String/*用户账号对应用户名称*/> getUserNameMapByUserCodeId(@RequestBody PublicIdsReqDTO publicIdsReqDTO) {
        Map<String, String> userNameCodeIdMap = new HashMap<>();
        if (ObjectUtil.isEmpty(publicIdsReqDTO.getIds())) {
            return userNameCodeIdMap;
        }
        List<UserCode> userCodes = userCodeService.listByIds(publicIdsReqDTO.getIds());
        Set<String> userIds = userCodes.stream().map(t -> t.getUserId()).collect(Collectors.toSet());
        if (userIds.size() == 0) {
            return userNameCodeIdMap;
        }
        List<User> userList = crudService.listByIds(userIds);
        Map<String, String> userNameMap = userList.stream().collect(Collectors.toMap(t -> t.getId(), t -> t.getName()));
        userCodes.forEach(t -> userNameCodeIdMap.put(t.getId(), userNameMap.get(t.getUserId())));
        return userNameCodeIdMap;
    }


    @ApiOperation("获取所有用户")
    @PostMapping(value = "/userList")
    public List<UserInDTO> userList() {
        List<User> users = crudService.list();
        List<UserInDTO> userInDTOList = users.stream()
                .map(t -> BeanUtil.toBean(t, UserInDTO.class)).collect(Collectors.toList());
        return userInDTOList;
    }

}
