package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyaud.fzhx.binder.annotation.BindResult;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.core.mybatis.QueryBuilder;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.iam.annotation.PreAuthorize;
import com.zyaud.fzhx.log.annotation.SysLog;
import com.zyaud.fzhx.log.enums.OptypeEnum;
import com.zyaud.idata.iam.api.client.IAppFrameClient;
import com.zyaud.idata.iam.api.req.DTO.RemoteUserInfoDeleteReqDTO;
import com.zyaud.idata.iam.api.req.DTO.RemoteUserInfoUpdateReqDTO;
import com.zyaud.idata.iam.api.resp.DTO.AppResultRspDto;
import com.zyaud.idata.iam.biz.model.dto.UserCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.UserPageDTO;
import com.zyaud.idata.iam.biz.model.dto.UserUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.Office;
import com.zyaud.idata.iam.biz.model.entity.User;
import com.zyaud.idata.iam.biz.model.entity.UserCode;
import com.zyaud.idata.iam.biz.model.vo.PublicNameVO;
import com.zyaud.idata.iam.biz.model.vo.UserVO;
import com.zyaud.idata.iam.biz.service.IOfficeService;
import com.zyaud.idata.iam.biz.service.IUserCodeService;
import com.zyaud.idata.iam.biz.service.IUserService;
import com.zyaud.idata.iam.biz.utils.SyncAppframe;
import com.zyaud.idata.iam.common.utils.Constants;
import com.zyaud.idata.iam.common.vo.PublicIdListReqVO;
import com.zyaud.idata.iam.common.vo.PublicIdReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * ???????????? ???????????????
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
@Slf4j
@RestController
@Api(tags = "????????????")
@RequestMapping("/system/user")
public class UserController extends SuperController {

    @Resource
    private IUserService crudService;

    @Resource
    private SyncAppframe syncAppframe;

    @Resource
    private IUserCodeService userCodeService;

    @ApiOperation(value = "????????????")
    @PostMapping(value = "/create")
    @SysLog(value = "'????????????,????????????:' + #createDTO.name", optype = OptypeEnum.INSERT)
    @PreAuthorize(value = "hasPermission('system:user:create')", msg = "????????????????????????????????????")
    public Result<Boolean> create(@Validated @RequestBody UserCreateDTO createDTO) {
        crudService.verifyIdCard(createDTO.getIdCard(), null);

        User entity = BeanUtil.toBean(createDTO, User.class);
        return ok(crudService.save(entity));
    }

    @ApiOperation(value = "????????????")
    @PostMapping(value = "/delete")
    @SysLog(value = "'????????????,??????id:' + #ids", optype = OptypeEnum.DELETE)
    //@PreAuthorize(value = "hasPermission('system:user:delete')", msg = "????????????????????????????????????")
    public Result<Boolean> delete(@Validated @RequestBody PublicIdListReqVO reqVO) {
        //????????????appframe??????
        syncAppframe.remoteDeleteUser(reqVO);
        return ok(crudService.removeByIds(reqVO.getIds()));
    }

    @ApiOperation(value = "????????????")
    @PostMapping(value = "/update")
    @SysLog(value = "'????????????,??????id:' + #updateDTO?.id + ',????????????:' + #updateDTO?.name", optype = OptypeEnum.UPDATE)
    //@PreAuthorize(value = "hasPermission('system:user:get')", msg = "????????????????????????????????????")
    public Result<Boolean> update(@Validated @RequestBody UserUpdateDTO updateDTO) {
        crudService.verifyIdCard(updateDTO.getIdCard(), updateDTO.getId());

        User entity = BeanUtil.toBean(updateDTO, User.class);
        //????????????appframe??????
        syncAppframe.remoteUpdateUser(updateDTO);
        return ok(crudService.updateById(entity));
    }

    @ApiOperation(value = "????????????")
    @PostMapping(value = "/get")
    @SysLog(value = "'????????????,??????id:' + #id", optype = OptypeEnum.SELECT)
    public Result<User> get(@Validated @RequestBody PublicIdReqVO reqVO) {
        return ok(crudService.getById(reqVO.getId()));
    }

    @ApiOperation(value = "????????????id??????????????????")
    @PostMapping(value = "/getUserInfoByAccountId")
    @SysLog(value = "'????????????id??????????????????,??????id:' + #id", optype = OptypeEnum.SELECT)
//    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public User getUserInfoByAccountId(@Validated @RequestBody PublicIdReqVO reqVO) {
        UserCode userCode = userCodeService.getById(reqVO.getId());
        BizAssert.isNotEmpty(userCode.getUserId(),"??????????????????????????????");
        User user = crudService.getById(userCode.getUserId());
        return user;
    }

    @ApiOperation("??????????????????")
    @PostMapping({"/page"})
    @BindResult
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<IPage<User>> page(@RequestBody UserPageDTO pageDTO) {
        Wrapper<User> query = QueryBuilder.queryWrapper(pageDTO);
        IPage<User> page = this.crudService.page(pageDTO.getPage(), query);
        IPage iPage = new Page(page.getCurrent(), page.getSize(), page.getTotal());
        iPage.setRecords(crudService.getUserVOs(page.getRecords()));
        return this.ok(iPage);
    }

    @ApiOperation("????????????")
    @PostMapping({"/list"})
    @PreAuthorize(value = "hasLogin()", msg = "????????????????????????????????????..")
    public Result<List<UserVO>> list(@RequestBody PublicNameVO publicNameVO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(publicNameVO.getName()), User.NAME, publicNameVO.getName());
        List<User> users = this.crudService.list(queryWrapper);
        return this.ok(crudService.getUserVOs(users));
    }

}
