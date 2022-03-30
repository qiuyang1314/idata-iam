package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.log.annotation.SysLog;
import com.zyaud.fzhx.log.enums.OptypeEnum;
import com.zyaud.idata.iam.biz.mapper.AppMapper;
import com.zyaud.idata.iam.biz.mapper.RoleMapper;
import com.zyaud.idata.iam.biz.model.dto.in.RoleInDTO;
import com.zyaud.idata.iam.biz.model.entity.App;
import com.zyaud.idata.iam.biz.model.entity.Role;
import com.zyaud.idata.iam.biz.model.vo.PublicStringVO;
import com.zyaud.idata.iam.common.utils.Constants;
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
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 系统应用角色 前端控制器
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
@Slf4j
@RestController
@Api(tags = "内部业务系统调用角色模块")
@RequestMapping("/system/inRole")
public class InRoleController extends SuperController {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private AppMapper appMapper;



    @ApiOperation("根据AppCode获取对应系统是启用状态的角色列表")
    @SysLog(value = "'根据AppCode获取对应系统是启用状态的角色列表,应用编码:' + #stringVO.string", optype = OptypeEnum.SELECT)
    @PostMapping("/getRoleList")
    public List<RoleInDTO> getRoleList(@Validated @RequestBody PublicStringVO stringVO) {
        App app = appMapper.getAppByCode(stringVO.getString());
        List<Role> roles = roleMapper.listRoleByAppIdAndUseable(app.getId(), Constants.ART_USING);
        List<RoleInDTO> roleInDTOS = roles.stream()
                .map(t -> BeanUtil.toBean(t, RoleInDTO.class)).collect(Collectors.toList());
        return roleInDTOS;
    }

    @ApiOperation("获取启用状态的角色列表")
    @SysLog(value = "'获取启用状态的角色列表,应用编码'", optype = OptypeEnum.SELECT)
    @PostMapping("/getRoleListByUseAbel")
    public List<RoleInDTO> getRoleListByUseAbel() {
        List<Role> roles = roleMapper.listRoleByUseable(Constants.ART_USING);
        List<RoleInDTO> roleInDTOS = roles.stream()
                .map(t -> BeanUtil.toBean(t, RoleInDTO.class)).collect(Collectors.toList());
        return roleInDTOS;
    }

    @ApiOperation("根据roleId获取角色")
    @SysLog(value = "'根据roleId获取角色,角色id:' + #publicIdVO?.id", optype = OptypeEnum.SELECT)
    @PostMapping("/getRoleById")
    public RoleInDTO getRoleById(@Validated @RequestBody PublicIdReqVO publicIdReqVO) {
        Role role = roleMapper.selectById(publicIdReqVO.getId());
        RoleInDTO roleInDTOS = BeanUtil.toBean(role, RoleInDTO.class);
        return roleInDTOS;
    }
}
