package com.zyaud.idata.iam.biz.controller;


import com.zyaud.fzhx.common.model.Result;
import com.zyaud.idata.iam.biz.model.vo.CheckFileExistRspVO;
import com.zyaud.idata.iam.biz.model.vo.FileUploadRspVO;
import com.zyaud.idata.iam.biz.model.vo.PublicIdListVO;
import com.zyaud.idata.iam.biz.service.IFileInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * <p>
 * 内审文件信息 前端控制器
 * </p>
 *
 * @author luohuixiang
 * @since 2021-04-22
 */
@RestController
@RequestMapping("/system/file-info")
@Api(value = "FileInfo", tags = "内审文件信息")
public class FileInfoController {

    @Resource
    private IFileInfoService fileInfoService;


    @ApiOperation(value = "验证文件是否上传")
    @PostMapping(value = "/exist")
    public Result<CheckFileExistRspVO> exist(@RequestParam("md5") Serializable md5) {
        return Result.ok(fileInfoService.exist(md5));
    }

    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/upload", name = "上传文件")
    public Result<FileUploadRspVO> upload(MultipartFile file) {
        return Result.ok(fileInfoService.upload(file));
    }


    @ApiOperation(value = "删除文件")
    @PostMapping(value = "/delete", name = "删除文件")
    public Result delete(@RequestBody @Validated PublicIdListVO publicIdsReqVO) {
        return Result.ok(fileInfoService.deleteByBizIds(publicIdsReqVO.getIds()));
    }

    @ApiOperation(value = "预览文件")
    @PostMapping(value = "/preview", name = "预览文件")
    public void preview(HttpServletResponse response, @RequestParam(value = "id")@ApiParam("文件id") String id) {
        fileInfoService.preview(id,response);
    }

}

