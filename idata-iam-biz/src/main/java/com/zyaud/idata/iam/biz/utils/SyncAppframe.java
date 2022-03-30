package com.zyaud.idata.iam.biz.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.idata.iam.api.client.IAppFrameClient;
import com.zyaud.idata.iam.api.req.DTO.*;
import com.zyaud.idata.iam.api.resp.DTO.AppResultRspDto;
import com.zyaud.idata.iam.biz.mapper.OfficeMapper;
import com.zyaud.idata.iam.biz.mapper.UserCodeMapper;
import com.zyaud.idata.iam.biz.model.dto.OfficeCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.OfficeUpdateDTO;
import com.zyaud.idata.iam.biz.model.dto.UserCodeUpdateDTO;
import com.zyaud.idata.iam.biz.model.dto.UserUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.Office;
import com.zyaud.idata.iam.biz.model.entity.User;
import com.zyaud.idata.iam.biz.model.entity.UserCode;
import com.zyaud.idata.iam.biz.service.IOfficeService;
import com.zyaud.idata.iam.biz.service.IUserCodeService;
import com.zyaud.idata.iam.biz.service.IUserService;
import com.zyaud.idata.iam.common.utils.Constants;
import com.zyaud.idata.iam.common.vo.PublicIdListReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @program: idata-iam
 * @description: 同步信息到apprame工具类
 * @author: qiuyang
 * @create: 2022-01-10 11:32
 **/
@Service
@Slf4j
public class SyncAppframe {

    @Resource
    private IOfficeService officeService;

    @Resource
    private IUserCodeService userCodeService;

    @Resource
    private IAppFrameClient iAppFrameClient;

    @Resource
    private IUserService userService;

    @Resource
    private OfficeMapper officeMapper;

    @Resource
    private UserCodeMapper userCodeMapper;

    @Value("${spring.syncAppframe}")
    private Boolean syncAppframe;

    /**
    * @Description 新增机构信息
    * @Author qiuyang
    * @param createDTO
    * @return void
    * @Date 2022/1/10 11:36
    **/
    public void remoteAddOrg(OfficeCreateDTO createDTO){
        try {
            if(syncAppframe){
                Office parentOffice = officeService.getById(createDTO.getParentId());
                RemoteOrgCreateReqDTO remoteOrgCreateReqDTO = new RemoteOrgCreateReqDTO();
                remoteOrgCreateReqDTO.setEmail(createDTO.getEmail());
                remoteOrgCreateReqDTO.setOrgAddr(createDTO.getAddress());
                remoteOrgCreateReqDTO.setOrgName(createDTO.getName());
                remoteOrgCreateReqDTO.setOrgCode(createDTO.getCode());
                remoteOrgCreateReqDTO.setParentOrgCode(parentOffice.getCode());
                AppResultRspDto appRsp = iAppFrameClient.remoteAddOrg(remoteOrgCreateReqDTO);
                if(!Constants.appFrameRsp.equals(appRsp.getCode())){
                    log.error("appFrame机构新增失败："+appRsp.getErrMsg());
                }
            }
        } catch (Exception e) {
            log.error("appFrame机构新增失败：",e);
        }
    }



    /**
     * @Description 删除机构信息
     * @Author qiuyang
     * @param officeIds
     * @return void
     * @Date 2022/1/10 11:36
     **/
    public void remoteDeleteOrg(PublicIdListReqVO officeIds){
        if(syncAppframe){
            List<Office> offices = officeService.listByIds(officeIds.getIds());
            if(CollectionUtil.isNotEmpty(offices)){
                RemoteOrgDeleteReqDTO remoteOrgDeleteReqDTO = new RemoteOrgDeleteReqDTO();
                List<String> orgCodes = new ArrayList<>();
                offices.forEach(office->{
                    orgCodes.add(office.getCode());
                });
                remoteOrgDeleteReqDTO.setOrgCode(orgCodes);
                AppResultRspDto appRsp = iAppFrameClient.remoteDeleteOrg(remoteOrgDeleteReqDTO);
                if(!Constants.appFrameRsp.equals(appRsp.getCode())){
                    BizAssert.fail("appFrame机构删除失败："+appRsp.getErrMsg());
                }
            }
        }
    }


    /**
     * @Description 修改机构信息
     * @Author qiuyang
     * @param updateDTO
     * @return void
     * @Date 2022/1/10 11:36
     **/
    public void remoteUpdateOrg(OfficeUpdateDTO updateDTO){
        try {
            if(syncAppframe){
                Office parentOffice = officeService.getById(updateDTO.getParentId());
                RemoteOrgUpdateReqDTO remoteOrgUpdateReqDTO = new RemoteOrgUpdateReqDTO();
                remoteOrgUpdateReqDTO.setEmail(updateDTO.getEmail());
                remoteOrgUpdateReqDTO.setOrgAddr(updateDTO.getAddress());
                remoteOrgUpdateReqDTO.setOrgName(updateDTO.getName());
                remoteOrgUpdateReqDTO.setOrgCode(updateDTO.getCode());
                remoteOrgUpdateReqDTO.setParentOrgCode(parentOffice.getCode());
                AppResultRspDto appRsp = iAppFrameClient.remoteUpdateOrg(remoteOrgUpdateReqDTO);
                if(!Constants.appFrameRsp.equals(appRsp.getCode())){
                    log.error("appFrame机构修改失败："+appRsp.getErrMsg());
                }
            }
        } catch (Exception e) {
            log.error("appFrame机构修改失败：",e);
        }
    }


    /**
    * @Description 同步新增用户
    * @Author qiuyang
    * @param userCode,user
    * @return void
    * @Date 2022/1/10 11:41
    **/
    public void remoteAddUser(UserCode userCode,User user){
        try {
            if(syncAppframe){
                Office office = officeService.getById(user.getOfficeId());
                RemoteUserInfoCreateReqDTO remoteUserInfoCreateReqDTO = new RemoteUserInfoCreateReqDTO();
                remoteUserInfoCreateReqDTO.setUserId(userCode.getUserId());
                remoteUserInfoCreateReqDTO.setLoginName(userCode.getLoginName());
                remoteUserInfoCreateReqDTO.setOrgCode(office.getCode());
                remoteUserInfoCreateReqDTO.setUserName(user.getName());
                remoteUserInfoCreateReqDTO.setStatus(userCode.getStatus());
                AppResultRspDto appRsp = iAppFrameClient.remoteAddUser(remoteUserInfoCreateReqDTO);
                if(!Constants.appFrameRsp.equals(appRsp.getCode())){
                    log.error("appFrame用户新增失败："+appRsp.getErrMsg());
                }
            }
        } catch (Exception e) {
            log.error("appFrame用户新增失败：",e);
        }
    }


    /**
    * @Description 同步更新账户
    * @Author qiuyang
    * @param updateDTO
    * @return void
    * @Date 2022/1/10 11:42
    **/
    public void remoteUpdateUserCode(UserCodeUpdateDTO updateDTO){
        //同步修改appframe用户
        try {
            if(syncAppframe){
                UserCode userCode = this.userCodeService.getById(updateDTO.getId());
                //是否已绑定
                if(ObjectUtil.isNotEmpty(userCode.getUserId())){
                    User user = userService.getById(userCode.getUserId());
                    Office office = officeService.getById(user.getOfficeId());
                    RemoteUserInfoUpdateReqDTO remoteUserInfoUpdateReqDTO = new RemoteUserInfoUpdateReqDTO();
                    remoteUserInfoUpdateReqDTO.setUserId(userCode.getUserId());
                    remoteUserInfoUpdateReqDTO.setLoginName(userCode.getLoginName());
                    remoteUserInfoUpdateReqDTO.setOrgCode(office.getCode());
                    remoteUserInfoUpdateReqDTO.setUserName(user.getName());
                    remoteUserInfoUpdateReqDTO.setStatus(updateDTO.getStatus());
                    AppResultRspDto appRsp = iAppFrameClient.remoteUpdateUser(remoteUserInfoUpdateReqDTO);
                    if(!Constants.appFrameRsp.equals(appRsp.getCode())){
                        log.error("appFrame用户修改失败："+appRsp.getErrMsg());
                    }
                }
            }
        } catch (Exception e) {
           log.error("appFrame用户修改失败：",e);
        }
    }


    /**
     * @Description 同步更新用户
     * @Author qiuyang
     * @param updateDTO
     * @return void
     * @Date 2022/1/10 11:42
     **/
    public void remoteUpdateUser(UserUpdateDTO updateDTO){
        try {
            if(syncAppframe){
                //判断是否绑定账号
                QueryWrapper<UserCode> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_id", updateDTO.getId());
                UserCode user = userCodeService.getOne(queryWrapper);
                if(ObjectUtil.isNotEmpty(user)) {
                    Office office = officeService.getById(updateDTO.getOfficeId());
                    RemoteUserInfoUpdateReqDTO remoteUserInfoUpdateReqDTO = new RemoteUserInfoUpdateReqDTO();
                    remoteUserInfoUpdateReqDTO.setUserId(updateDTO.getId());
                    remoteUserInfoUpdateReqDTO.setUserName(updateDTO.getName());
                    remoteUserInfoUpdateReqDTO.setOrgCode(office.getCode());
                    AppResultRspDto appRsp = iAppFrameClient.remoteUpdateUser(remoteUserInfoUpdateReqDTO);
                    if (!Constants.appFrameRsp.equals(appRsp.getCode())) {
                        log.error("appFrame用户修改失败：" + appRsp.getErrMsg());
                    }
                }
            }
        } catch (Exception e) {
            log.error("appFrame用户修改失败：",e);
        }
    }

    /**
    * @Description 同步删除,解绑账户
    * @Author qiuyang
    * @param codeIds
    * @return
    * @Date 2022/1/10 12:10
    **/
    public void remoteDeleteBindUser(PublicIdListReqVO codeIds){
        try {
            if(syncAppframe){
                List<UserCode> userCode = userCodeService.listByIds(codeIds.getIds());
                List<String> userIds = new ArrayList<>();
                userCode.forEach(code->{
                    if(ObjectUtil.isNotEmpty(code.getUserId())){
                        userIds.add(code.getUserId());
                    }
                });
                if(CollectionUtil.isNotEmpty(userIds)){
                    RemoteUserInfoDeleteReqDTO remoteUserInfoDeleteReqDTO = new RemoteUserInfoDeleteReqDTO();
                    remoteUserInfoDeleteReqDTO.setUserId(userIds);
                    AppResultRspDto appRsp = iAppFrameClient.remoteDeleteUser(remoteUserInfoDeleteReqDTO);
                    if(!Constants.appFrameRsp.equals(appRsp.getCode())){
                        log.error("appFrame用户删除失败："+appRsp.getErrMsg());
                    }
                }
            }
        } catch (Exception e) {
            log.error("appFrame用户删除失败：",e);
        }
    }

    /**
     * @Description 同步删除用户
     * @Author qiuyang
     * @param userId
     * @return
     * @Date 2022/1/10 12:10
     **/
    public void remoteDeleteUser(PublicIdListReqVO userId){
        if(syncAppframe){
            //判断是否绑定账号
            QueryWrapper<UserCode> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("user_id", userId.getIds());
            List<UserCode> users = userCodeService.list(queryWrapper);
            if(CollectionUtil.isNotEmpty(users)){
                List<String> userIds = new ArrayList<>();
                users.forEach(user->{
                    userIds.add(user.getUserId());
                });
                RemoteUserInfoDeleteReqDTO remoteUserInfoDeleteReqDTO = new RemoteUserInfoDeleteReqDTO();
                remoteUserInfoDeleteReqDTO.setUserId(userIds);
                AppResultRspDto appRsp = iAppFrameClient.remoteDeleteUser(remoteUserInfoDeleteReqDTO);
                if(!Constants.appFrameRsp.equals(appRsp.getCode())){
                    BizAssert.fail("appFrame用户删除失败："+appRsp.getErrMsg());
                }
            }
        }
    }
    /**
    * @Description 批量同步用户
    * @Author qiuyang
    * @param
    * @return
    * @Date 2022/1/10 12:09
    **/
    public void remoteBatchUser(){
        try {
            if(syncAppframe){
                List<RemoteUserInfoCreateReqDTO> remoteUserInfoCreateReqDTO = userCodeMapper.getAppframeSyncUser();
                if(CollectionUtil.isNotEmpty(remoteUserInfoCreateReqDTO)){
                    AppResultRspDto appRsp = iAppFrameClient.remoteBatchUser(remoteUserInfoCreateReqDTO);
                    if(!Constants.appFrameRsp.equals(appRsp.getCode())){
                        log.error("appFrame用户批量新增失败："+appRsp.getErrMsg());
                    }
                }
            }
        } catch (Exception e) {
            log.error("appFrame用户批量新增失败：",e);
        }
    }

    /**
    * @Description 批量同步机构
    * @Author qiuyang
    * @param
    * @return
    * @Date 2022/1/10 12:09
    **/
    public void remoteBatchOrg(){
        try {
            if(syncAppframe){
                List<RemoteOrgCreateReqDTO> remoteOrgCreateReqDTOs = officeMapper.getAppframeSyncOrg();
                if(CollectionUtil.isNotEmpty(remoteOrgCreateReqDTOs)){
                    AppResultRspDto appRsp = iAppFrameClient.remoteBatchOrg(remoteOrgCreateReqDTOs);
                    if(!Constants.appFrameRsp.equals(appRsp.getCode())){
                        log.error("appFrame机构新增失败："+appRsp.getErrMsg());
                    }
                }
            }
        } catch (Exception e) {
            log.error("appFrame机构新增失败：",e);
        }
    }

}
