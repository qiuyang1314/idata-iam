package com.zyaud.idata.iam.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.idata.iam.biz.model.entity.UserCode;
import com.zyaud.idata.iam.biz.model.vo.*;
import com.zyaud.idata.iam.biz.service.IInUserCodeService;
import com.zyaud.idata.iam.client.dto.UserCodeInDTO;
import com.zyaud.idata.iam.client.vo.PublicIdsReqInVO;
import com.zyaud.idata.iam.client.vo.PublicNameInVO;
import com.zyaud.idata.iam.common.vo.PublicIdReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户账号 前端控制器
 * 切记:限于纪委产品内部系统间调用   ****
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
@Slf4j
@RestController
@RequestMapping("/system/inUserCode")
@Api(value = "InStdCodeController", tags = "内部系统调用账号模块")
public class InUserCodeController extends SuperController {

    @Resource
    private IInUserCodeService crudService;


    @ApiOperation("根据appCode获取用户账号列表")
    @PostMapping("/listUserCodeByAppCode")
    public List<UserCodeInDTO> listUserCodeByAppCode(@Validated @RequestBody PublicStringVO publicStringVO) {
        List<UserCode> userCodes = crudService.listUserCodeByAppCode(publicStringVO.getString());
        List<UserCodeInDTO> userCodeInDTOS = userCodes.stream()
                .map(t -> BeanUtil.toBean(t, UserCodeInDTO.class)).collect(Collectors.toList());
        return userCodeInDTOS;
    }

    @ApiOperation("获取所有的用户账号列表")
    @PostMapping("/listUserCode")
    public List<UserCodeInDTO> listUserCode() {
        List<UserCode> userCodes = crudService.list();
        List<UserCodeInDTO> userCodeInDTOS = userCodes.stream()
                .map(t -> BeanUtil.toBean(t, UserCodeInDTO.class)).collect(Collectors.toList());
        return userCodeInDTOS;
    }


    /**
     * 获取用户在指定应用下的角色列表
     *
     * @param userRoleReqVO
     * @return
     */
    @PostMapping(value = "/findUserRoleList")
    public List<UserRoleRspVO> findUserRoleList(@Validated @RequestBody UserRoleReqVO userRoleReqVO) {
        return crudService.findUserRoleList(userRoleReqVO.getAppCode(), userRoleReqVO.getUserCodeId());
    }

    /**
     * 获取在指定机构下的账号列表
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/findUserCodeListByOfficeId")
    public List<UserCodeInDTO> findUserCodeListByOfficeId(@Validated @RequestBody PublicIdReqVO reqVO) {
        return crudService.findUserCodeListByOfficeId(reqVO.getId());
    }

    @ApiOperation(value = "获取机构用户列表")
    @PostMapping(value = "/getOfficeUserTree")
    public List<UserTreeVO> getOfficeUserTree(@Validated @RequestBody UserTreeReqVO userTreeReqDTO) {
        log.info("机构用户下拉树形列表");
        return crudService.getOfficeUserTree(userTreeReqDTO);
    }

    @ApiOperation("获取角色用户列表")
    @PostMapping("/getRoleUserTree")
    public List<UserTreeVO> getRoleUserTree(@Validated @RequestBody UserTreeReqVO reqVO) {
        log.info("角色用户下拉树形列表");
        return crudService.getRoleUserTree(reqVO);
    }

    @ApiOperation("根据用户登录名模糊查询")
    @PostMapping(value = "/getUserCodeByName")
    public List<UserCodeInDTO> getUserCodeByName(@RequestBody PublicNameInVO publicNameInVO) {
        QueryWrapper<UserCode> wrapper = new QueryWrapper<>();
        wrapper.like(UserCode.LOGINNAME, publicNameInVO.getName());
        List<UserCodeInDTO> userCodes = crudService.list(wrapper).stream()
                .map(t -> BeanUtil.toBean(t, UserCodeInDTO.class)).collect(Collectors.toList());
        return userCodes;
    }


    @ApiOperation("根据用户登录名模糊查询")
    @PostMapping(value = "/getUserCodeByIds")
    public List<UserCodeInDTO> getUserCodeByIds(@RequestBody PublicIdsReqInVO idsReqInVO) {
        if (ObjectUtil.isEmpty(idsReqInVO.getIds())) {
            return new ArrayList<>();
        }
        List<UserCodeInDTO> userCodes = crudService.listByIds(idsReqInVO.getIds()).stream()
                .map(t -> BeanUtil.toBean(t, UserCodeInDTO.class)).collect(Collectors.toList());
        return userCodes;
    }


}
