package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyaud.fzhx.binder.annotation.BindResult;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.common.utils.JsonUtils;
import com.zyaud.fzhx.core.mybatis.QueryBuilder;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.iam.annotation.PreAuthorize;
import com.zyaud.fzhx.log.enums.OptypeEnum;
import com.zyaud.idata.iam.biz.mapper.SysLogMapper;
import com.zyaud.idata.iam.biz.model.dto.SysLogCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.SysLogPageDTO;
import com.zyaud.idata.iam.biz.model.entity.SysLog;
import com.zyaud.idata.iam.biz.model.vo.GetAppCodesVO;
import com.zyaud.idata.iam.biz.model.vo.GetmodulesByAppCodeVO;
import com.zyaud.idata.iam.biz.model.vo.SysLogPageVO;
import com.zyaud.idata.iam.biz.model.vo.SysLogVO;
import com.zyaud.idata.iam.biz.service.ISysLogService;
import com.zyaud.idata.iam.common.errorcode.LogMngErrorEnum;
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
 * 系统日志 前端控制器
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
@Slf4j
@RestController
@RequestMapping("/system/sys-log")
@Api(tags = "系统日志模块")
public class SysLogController extends SuperController {

    @Resource
    private ISysLogService crudService;
    @Resource
    private SysLogMapper sysLogMapper;

    @ApiOperation(value = "非统一权限系统添加日志记录到统一权限系统")
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody SysLogCreateDTO createDTO) {
        crudService.saveLog(createDTO.getAppCode(),
                createDTO.getUa(),
                createDTO.getParams(),
                createDTO.getResult(),
                createDTO.getOptype(),
                createDTO.getModule(),
                createDTO.getUserName(),
                createDTO.getDescription(),
                createDTO.getRequestIp(),
                createDTO.getType(),
                createDTO.getExDesc());
        return ok();
    }

    @ApiOperation(value = "通过应用编码获取模块")
    @PostMapping(value = "/getmodulesByAppCode")
    public Result<List<String>> getmodulesByAppCode(@Validated @RequestBody GetmodulesByAppCodeVO getmodulesByAppCodeVO) {
        return ok(sysLogMapper.getmodulesByAppCode(getmodulesByAppCodeVO.getAppCode()));
    }

    @ApiOperation(value = "获取应用列表")
    @PostMapping(value = "/getAppCodes")
    @BindResult
    public Result<List<GetAppCodesVO>> getAppCodes() {
        return ok(sysLogMapper.getAppCodes());
    }

    @ApiOperation(value = "单条记录查询")
    @PostMapping(value = "/get")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    @BindResult
    public Result<SysLogVO> get(@Validated @RequestBody PublicIdReqVO publicIdReqVO) {
        if (StrUtil.isEmpty(publicIdReqVO.getId())) {
            return fail(LogMngErrorEnum.ID_CANNOT_BE_NULL);
        }
        SysLog sysLog = crudService.getById(publicIdReqVO.getId());
        SysLogVO sysLogVO = BeanUtil.toBean(sysLog, SysLogVO.class);
        sysLogVO.setOperateType(OptypeEnum.getName(sysLogVO.getOperateType()));
        sysLogVO.setResult(JsonUtils.toJson(sysLogVO.getResult(), true));
        return ok(sysLogVO);
    }

    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    @BindResult
    public Result<IPage<SysLogPageVO>> page(@RequestBody SysLogPageDTO pageDTO) {
        QueryWrapper<SysLog> query = QueryBuilder.queryWrapper(pageDTO);
        query.orderByDesc(SysLog.CREATE_TIME);
        IPage<SysLog> page = crudService.page(pageDTO.getPage(), query);
        IPage iPage = new Page(page.getCurrent(), page.getSize(), page.getTotal());
        if (page.getSize() > 0) {
            List<SysLogPageVO> sysLogPageVOS = page.getRecords().stream().map(t -> BeanUtil.toBean(t, SysLogPageVO.class)).collect(Collectors.toList());
            sysLogPageVOS.forEach(t -> t.setOperateType(OptypeEnum.getName(t.getOperateType())).setErrMsg(null));
            iPage.setRecords(sysLogPageVOS);
        }
        return ok(iPage);
    }

}
