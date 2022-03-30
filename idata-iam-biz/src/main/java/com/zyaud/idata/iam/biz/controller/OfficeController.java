package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyaud.fzhx.binder.annotation.BindResult;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.iam.annotation.PreAuthorize;
import com.zyaud.fzhx.log.annotation.SysLog;
import com.zyaud.fzhx.log.enums.OptypeEnum;
import com.zyaud.idata.iam.api.client.IAppFrameClient;
import com.zyaud.idata.iam.api.req.DTO.RemoteOrgCreateReqDTO;
import com.zyaud.idata.iam.api.req.DTO.RemoteOrgDeleteReqDTO;
import com.zyaud.idata.iam.api.req.DTO.RemoteOrgUpdateReqDTO;
import com.zyaud.idata.iam.api.resp.DTO.AppResultRspDto;
import com.zyaud.idata.iam.biz.mapper.OfficeMapper;
import com.zyaud.idata.iam.biz.model.dto.OfficeCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.OfficePageDTO;
import com.zyaud.idata.iam.biz.model.dto.OfficeUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.Office;
import com.zyaud.idata.iam.biz.model.vo.OfficeTreeVO;
import com.zyaud.idata.iam.biz.model.vo.OfficeVO;
import com.zyaud.idata.iam.biz.service.IOfficeService;
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
 * 机构部门 前端控制器
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-06-28
 */
@Slf4j
@RestController
@Api(tags = "机构模块")
@RequestMapping("/system/office")
public class OfficeController extends SuperController {

    @Resource
    private IOfficeService crudService;

    @Resource
    private SyncAppframe syncAppframe;


    @ApiOperation("新增机构")
    @PostMapping(value = {"/create"})
    @SysLog(value = "'新增机构,机构名:' + #createDTO?.name", optype = OptypeEnum.INSERT)
    @PreAuthorize(value = "hasPermission('system:office:create')", msg = "您没有‘新增机构’的权限")
    public Result<Boolean> create(@Validated @RequestBody OfficeCreateDTO createDTO) {
        //同步机构新增到appframe
        syncAppframe.remoteAddOrg(createDTO);
        return this.ok(this.crudService.createOffice(createDTO));
    }

    @ApiOperation("批量同步机构到appframe")
    @PostMapping(value = {"/syncOfficeAppframe"})
    @SysLog(value = "'同步机构到appframe'")
    //@PreAuthorize(value = "hasPermission('system:office:syncOfficeAppframe')", msg = "您没有‘批量同步机构到appframe’的权限")
    public Result<Boolean> syncOfficeAppframe() {
        //同步机构新增到appframe
        syncAppframe.remoteBatchOrg();
        return ok();
    }

    @ApiOperation(value = "删除机构")
    @PostMapping(value = "/delete")
    @SysLog(value = "'删除机构,机构id:' + #reqVO?.ids", optype = OptypeEnum.DELETE)
    @PreAuthorize(value = "hasPermission('system:office:delete')", msg = "您没有‘删除机构’的权限")
    public Result<Boolean> delete(@Validated @RequestBody PublicIdListReqVO reqVO) {
        //同步机构删除到appframe
        syncAppframe.remoteDeleteOrg(reqVO);
        return ok(crudService.deleteOfficeByIds(reqVO.getIds()));
    }

    @ApiOperation(value = "修改机构")
    @PostMapping(value = "/update")
    @SysLog(value = "'修改机构,机构id:' + #updateDTO?.id + ',机构名:' + #updateDTO?.name", optype = OptypeEnum.UPDATE)
    @PreAuthorize(value = "hasPermission('system:office:get')", msg = "您没有‘编辑机构’的权限")
    public Result update(@Validated @RequestBody OfficeUpdateDTO updateDTO) {
        //同步机构新增到appframe
        syncAppframe.remoteUpdateOrg(updateDTO);
        crudService.updateOffice(updateDTO);
        return ok();
    }

    @ApiOperation(value = "查询机构")
    @PostMapping(value = "/get")
    @SysLog(value = "'查询机构,机构id:' + #reqVO?.id", optype = OptypeEnum.SELECT)
    @BindResult
    @PreAuthorize(value = "permitAll()")
    public Result<OfficeVO> get(@Validated @RequestBody PublicIdReqVO reqVO) {
        Office office = this.crudService.getById(reqVO.getId());
        OfficeVO officeVO = BeanUtil.toBean(office, OfficeVO.class);
        if (Constants.ROOTID.equals(officeVO.getParentId())) {
            officeVO.setParentId(null);
        }
        return this.ok(officeVO);
    }

    @ApiOperation(value = "机构下拉树形列表")
    @PostMapping(value = "/getOfficeList")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<List<OfficeTreeVO>> getOfficeList(@RequestBody OfficePageDTO pageDTO) {
        return ok(crudService.getOfficeList(pageDTO));
    }

    @ApiOperation(value = "机构下拉列表查询")
    @PostMapping(value = "/getList")
    public Result<List<Office>> getList() {
        List<Office> offices = crudService.list();
        return ok(offices);
    }

    @ApiOperation(value = "机构下拉树形列表")
    @PostMapping(value = "/getAllOfficeTree")
    public Result<List<OfficeTreeVO>> getAllOfficeTree() {
        List<Office> list = crudService.list();
        return ok(crudService.getOfficeTree(list));
    }

}
