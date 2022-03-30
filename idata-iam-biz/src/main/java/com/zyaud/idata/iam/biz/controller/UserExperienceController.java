package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.core.mybatis.QueryBuilder;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.iam.annotation.PreAuthorize;
import com.zyaud.idata.iam.biz.model.dto.UserExperienceCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.UserExperiencePageDTO;
import com.zyaud.idata.iam.biz.model.dto.UserExperienceUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.UserExperience;
import com.zyaud.idata.iam.biz.service.IUserExperienceService;
import com.zyaud.idata.iam.common.vo.PublicIdListReqVO;
import com.zyaud.idata.iam.common.vo.PublicIdReqVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * <p>
 * 用户教育工作经历 前端控制器
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
@Slf4j
@RestController
@RequestMapping("/system/user-experience")
public class UserExperienceController extends SuperController {

    @Resource
    private IUserExperienceService crudService;

    @ApiOperation(value = "新增一条记录")
    @PostMapping(value = "/create")
    //@SysLog(value = "新增", request = false)
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<Boolean> create(@Validated @RequestBody UserExperienceCreateDTO createDTO) {
        UserExperience entity = BeanUtil.toBean(createDTO, UserExperience.class);
        return ok(crudService.save(entity));
    }

    @ApiOperation(value = "删除记录")
    @PostMapping(value = "/delete")
    //@SysLog("'删除:' + #ids")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<Boolean> delete(@Validated @RequestBody PublicIdListReqVO reqVO) {
        return ok(crudService.removeByIds(reqVO.getIds()));
    }

    @ApiOperation(value = "修改某条记录")
    @PostMapping(value = "/update")
    //@SysLog(value = "'修改:' + #updateDTO?.id", request = false)
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<Boolean> update(@Validated @RequestBody UserExperienceUpdateDTO updateDTO) {
        UserExperience entity = BeanUtil.toBean(updateDTO, UserExperience.class);
        return ok(crudService.updateById(entity));
    }

    @ApiOperation(value = "单条记录查询")
    @PostMapping(value = "/get")
    //@SysLog("'查询:' + #id")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<UserExperience> get(@Validated @RequestBody PublicIdReqVO reqVO) {
        return ok(crudService.getById(reqVO.getId()));
    }

    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    //@SysLog(value = "'分页列表查询:第' + #pageDTO?.current + '页, 显示' + #pageDTO?.size + '行'", response = false)
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<IPage<UserExperience>> page(@RequestBody UserExperiencePageDTO pageDTO) {
        IPage<UserExperience> page = pageDTO.getPage();
        Wrapper<UserExperience> query = QueryBuilder.queryWrapper(pageDTO);
        return ok(crudService.page(page, query));
    }


}
