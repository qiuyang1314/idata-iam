package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
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
import com.zyaud.idata.iam.biz.model.dto.ProblemFeedbackCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.ProblemFeedbackPageDTO;
import com.zyaud.idata.iam.biz.model.entity.ProblemFeedback;
import com.zyaud.idata.iam.biz.model.entity.ProblemPhoto;
import com.zyaud.idata.iam.biz.model.vo.ProblemFeedbakeVO;
import com.zyaud.idata.iam.biz.service.IProblemFeedbackService;
import com.zyaud.idata.iam.biz.service.IProblemPhotoService;
import com.zyaud.idata.iam.common.errorcode.SuggestMngErrorEnum;
import com.zyaud.idata.iam.common.utils.Constants;
import com.zyaud.idata.iam.common.vo.PublicIdListReqVO;
import com.zyaud.idata.iam.common.vo.PublicIdReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 问题反馈表 前端控制器
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
@Slf4j
@RestController
@Api(tags = "问题反馈模块")
@RequestMapping(value = "/system/XsProblemFeedback", name = "问题反馈")
public class ProblemFeedbackController extends SuperController {

    @Resource
    private IProblemFeedbackService crudService;

    @Resource
    private IProblemPhotoService iProblemPhotoService;

    @Value("${wttp.ip:172.17.2.230:80}")
    private String ip;


    @ApiOperation(value = "新增问题反馈")
    @PostMapping(value = "/create", name = "新增问题反馈")
    @SysLog(value = "'新增问题反馈,问题反馈标题:' + #createDTO?.opinionTitle", optype = OptypeEnum.INSERT)
    @Transactional
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<String> create(@Validated ProblemFeedbackCreateDTO createDTO,
                                 @RequestParam(required = false) MultipartFile[] multipartFiles) throws IOException {
        ProblemFeedback xsProblemFeedback = BeanUtil.toBean(createDTO, ProblemFeedback.class);
        crudService.save(xsProblemFeedback);
        iProblemPhotoService.createProblemPhoto(multipartFiles, xsProblemFeedback.getId());
        return ok(xsProblemFeedback.getId());
    }


    @ApiOperation(value = "删除问题反馈")
    @PostMapping(value = "/delete")
    @SysLog(value = "'删除问题反馈,问题反馈id:' + #ids", optype = OptypeEnum.DELETE)
    @Transactional
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<Boolean> delete(@RequestBody @Validated PublicIdListReqVO reqVO) {
        iProblemPhotoService.deleteByIssueIds(reqVO.getIds());
        return ok(crudService.removeByIds(reqVO.getIds()));
    }

    @ApiOperation(value = "查询问题反馈")
    @RequestMapping(value = "/get")
    @SysLog(value = "'查询问题反馈,问题反馈id:' + #id", optype = OptypeEnum.SELECT)
    @BindResult
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<ProblemFeedbakeVO> get(@Validated @RequestBody PublicIdReqVO reqVO) {
        //
        ProblemFeedback problemFeedback = crudService.getById(reqVO.getId());
        if (ObjectUtil.isEmpty(problemFeedback)) {
            BizAssert.fail(SuggestMngErrorEnum.PROBLEM_FEEDBACK_IS_NULL_BY_ERROR_ID);
        }
        ProblemFeedbakeVO problemFeedbakeVO = BeanUtil.toBean(problemFeedback, ProblemFeedbakeVO.class);
        List<ProblemPhoto> list = iProblemPhotoService.getProblemPhotosByIssueId(reqVO.getId());
        List<String> urlList = new ArrayList<>();
        if (list.size() > 0) {
            for (ProblemPhoto problemPhoto : list) {
                String url = Constants.getImageUrl(problemPhoto.getGroupName(), problemPhoto.getFileId());
                urlList.add(url);
            }
        }
        problemFeedbakeVO.setUrls(urlList);
        return ok(problemFeedbakeVO);
    }

    @ApiOperation(value = "分页列表查询")
    @RequestMapping(value = "/page")
    @BindResult
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<IPage<ProblemFeedback>> page(@RequestBody ProblemFeedbackPageDTO pageDTO) {
        QueryWrapper<ProblemFeedback> query = QueryBuilder.queryWrapper(pageDTO);
        query.orderByDesc(ProblemFeedback.CREATE_TIME);
        IPage<ProblemFeedback> page = this.crudService.page(pageDTO.getPage(), query);
        IPage iPage = new Page(page.getCurrent(), page.getSize(), page.getTotal());
        List<ProblemFeedbakeVO> problemFeedPageVOArrayList = new ArrayList<>();
        page.getRecords().forEach(t -> problemFeedPageVOArrayList.add(BeanUtil.toBean(t, ProblemFeedbakeVO.class)));
        iPage.setRecords(problemFeedPageVOArrayList);
        return this.ok(iPage);
    }


}
