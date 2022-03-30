package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.core.model.IdEntity;
import com.zyaud.fzhx.core.mybatis.QueryBuilder;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.iam.annotation.PreAuthorize;
import com.zyaud.fzhx.log.annotation.SysLog;
import com.zyaud.fzhx.log.enums.OptypeEnum;
import com.zyaud.idata.iam.biz.model.dto.DownloadPageDTO;
import com.zyaud.idata.iam.biz.model.dto.DownloadUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.Download;
import com.zyaud.idata.iam.biz.service.IDownloadService;
import com.zyaud.idata.iam.common.vo.PublicIdListReqVO;
import io.swagger.annotations.Api;
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
 * 下载中心 前端控制器
 * </p>
 *
 * @author fzhx-aicode
 * @since 2021-05-20
 */
@Slf4j
@RestController
@RequestMapping("/system/download")
@Api(value = "Download", tags = "下载中心")
public class DownloadController extends SuperController {

    @Resource
    private IDownloadService crudService;

    @ApiOperation(value = "删除下载中心记录")
    @PostMapping(value="/delete")
    @SysLog(value = "'删除:' + #idListReqVO?.ids", optype = OptypeEnum.DELETE)
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<Boolean> delete(@Validated  @RequestBody PublicIdListReqVO idListReqVO) {
        return ok(crudService.removeByIds(idListReqVO.getIds()));
    }

    @ApiOperation(value = "修改下载中心记录")
    @PostMapping(value="/update")
    @SysLog(value = "'修改:' + #updateDTO?.id", request = false, optype = OptypeEnum.UPDATE)
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<Boolean> update(@Validated(IdEntity.Update.class) @RequestBody DownloadUpdateDTO updateDTO) {
        Download entity = BeanUtil.toBean(updateDTO, Download.class);
        return ok(crudService.updateById(entity));
    }


    @ApiOperation(value = "下载中心分页列表查询")
    @PostMapping(value = "/page")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<IPage<Download>> page(@Validated @RequestBody DownloadPageDTO pageDTO) {
        IPage<Download> page = pageDTO.getPage();
        Wrapper<Download> query = QueryBuilder.queryWrapper(pageDTO);
        return ok(crudService.page(page, query));
    }





}
