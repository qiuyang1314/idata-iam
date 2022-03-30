package com.zyaud.idata.iam.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.core.mybatis.QueryBuilder;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.log.annotation.SysLog;
import com.zyaud.fzhx.log.enums.OptypeEnum;
import com.zyaud.idata.iam.api.resp.DownloadGetRspVO;
import com.zyaud.idata.iam.biz.model.dto.DownloadCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.DownloadPageDTO;
import com.zyaud.idata.iam.biz.model.dto.DownloadUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.Download;
import com.zyaud.idata.iam.biz.service.IDownloadService;
import com.zyaud.idata.iam.common.vo.PublicIdListReqVO;
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
import java.io.File;

@Slf4j
@RestController
@RequestMapping("/system/inDownload")
@Api(value = "InDownload", tags = "下载中心")
public class InDownloadController extends SuperController {
    @Resource
    private IDownloadService crudService;

    @ApiOperation(value = "新增一条下载中心记录")
    @PostMapping(value="/create")
    @SysLog(value = "新增", request = false, optype = OptypeEnum.INSERT)
    public Result<Boolean> createDownloadSupply(@Validated @RequestBody DownloadCreateDTO createDTO) {
        return Result.ok(crudService.create(createDTO));
    }

    @ApiOperation(value = "获取一条下载中心记录")
    @PostMapping(value="/get")
    @SysLog(value = "获取", request = false, optype = OptypeEnum.SELECT)
    public DownloadGetRspVO getDownload(@Validated @RequestBody PublicIdReqVO idReqVO) {
        return BeanUtil.toBean(crudService.getById(idReqVO.getId()), DownloadGetRspVO.class);
    }

    @ApiOperation(value = "更新下载中心记录状态")
    @PostMapping(value="/update")
    @SysLog(value = "更新下载中心记录状态", request = false, optype = OptypeEnum.UPDATE)
    public Result<Boolean> updateDownload(@Validated @RequestBody DownloadUpdateDTO updateDTO) {
        return Result.ok(crudService.update(updateDTO));
    }


    @ApiOperation(value = "下载中心分页列表查询")
    @PostMapping(value = "/page")
    public Result<IPage<Download>> pageDownload(@Validated @RequestBody DownloadPageDTO pageDTO) {
        IPage<Download> page = pageDTO.getPage();
        QueryWrapper<Download> query = QueryBuilder.queryWrapper(pageDTO);
        query.orderByDesc(Download.CREATE_TIME);
        return Result.ok(crudService.page(page, query));
    }

    @ApiOperation(value = "删除下载中心记录")
    @PostMapping(value="/delete")
    @SysLog(value = "'删除:' + #ids", optype = OptypeEnum.DELETE)
    public Result<Boolean> deleteDownload(@Validated  @RequestBody PublicIdListReqVO idListReqVO) {
        //删除临时文件
        for (String id : idListReqVO.getIds()) {
            String fpath = crudService.getById(id).getFpath();
            try{
                File deleteFile = new File(fpath);
                deleteFile.delete();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return Result.ok(crudService.removeByIds(idListReqVO.getIds()));
    }

}
