package com.zyaud.idata.iam.client.client;


import com.zyaud.fzhx.common.model.Result;
import com.zyaud.idata.foundation.common.model.dto.PublicIdsReqDTO;
import com.zyaud.idata.foundation.common.model.vo.PublicAppCodeVO;
import com.zyaud.idata.foundation.common.model.vo.PublicIdReqVO;
import com.zyaud.idata.iam.client.dto.*;
import com.zyaud.idata.iam.client.entity.User;
import com.zyaud.idata.iam.client.vo.DataSourceRspVo;
import com.zyaud.idata.iam.client.vo.PublicIdsReqInVO;
import com.zyaud.idata.iam.client.vo.PublicNameInVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * iam接口
 *
 * @author shine
 * @date 2022/1/26 16:56
 */
@FeignClient(name = "idata-iam", url = "${spring.feign.url.iam}")
public interface IamClient {
    /**
     * 字典类型下拉列表
     *
     * @return
     */
    @PostMapping(value = "/system/inStdCode/getCodeListByStdType")
    List<StdCodeInDTO> getByStdType(@RequestBody StdCodeListReqDTO stdCodeListReqDTO);

    /**
     * 根据用户账号id获取对应的用户
     * 如果传过来的是空的id则获取到的数据为空
     *
     * @param publicIdsReqDTO
     * @return
     */
    @PostMapping(value = "/system/inUser/getUserNameMapByUserCodeId")
    Map<String, String> getUserNameMapByUserCodeId(@Validated @RequestBody PublicIdsReqDTO publicIdsReqDTO);

    /**
     * 获取字典目录树
     *
     * @param stdCodeListReqDTO
     * @return
     */
    @PostMapping("/system/inStdCode/getByStdTypeTree")
    List<StdCodeTreeDTO> getByStdTypeTree(@Validated @RequestBody StdCodeListReqDTO stdCodeListReqDTO);


    /**
     * 根据应用编码ip配置
     *
     * @param appCodeVO
     * @return
     */
    @PostMapping("/system/external-api/getConfigByAppCode")
    IpConfigDTO getConfigByAppCode(@RequestBody PublicAppCodeVO appCodeVO);

    /**
     * @param userTreeReqDTO
     * @return List<UserTreeVO>
     * @Description 获取机构用户树
     * @Author qiuyang
     * @Date 2022/1/13 11:15
     **/
    @PostMapping("/system/inUserCode/getOfficeUserTree")
    List<UserTreeRepDTO> getOfficeUserTree(@Validated @RequestBody UserTreeReqDTO userTreeReqDTO);

    /**
     * @param
     * @return
     * @Description 获取启用状态角色
     * @Author qiuyang
     * @Date 2022/1/13 11:25
     **/
    @PostMapping("/system/inRole/getRoleListByUseAbel")
    List<RoleInDTO> getRoleListByUseAbel();


    /**
     * 根据名称查询用户信息
     *
     * @param publicNameVO
     * @return
     */
    @PostMapping("/system/inUser/list")
    List<UserInDTO> list(@RequestBody PublicNameInVO publicNameVO);

    /**
     * 获取所有机构列表
     *
     * @return
     */
    @PostMapping("/system/inOffice/getList")
    List<OfficeInDTO> getList();

    /**
     * 根据当前机构id获取机构信息
     */
    @PostMapping(value = "/system/inOffice/get")
    OfficeInDTO get(@RequestBody PublicIdReqVO reqVO);

    /**
     * 获取机构树
     */
    @PostMapping(value = "/system/inOffice/getAllOfficeTree")
    List<OfficeTreeInDTO> getAllOfficeTree();

    /**
     * 根据用户名称模糊查询获取用户账号的登录名
     *
     * @return
     */
    @PostMapping(value = "/system/inUserCode/getUserCodeByName")
    List<UserCodeInDTO> getUserCodeByName(@RequestBody PublicNameInVO publicNameInVO);

    /**
     * 根据用户名称模糊查询获取用户账号的登录名
     *
     * @return
     */
    @PostMapping(value = "/system/inUserCode/getUserCodeByIds")
    List<UserCodeInDTO> getUserCodeByIds(@RequestBody PublicIdsReqInVO idsReqInVO);

    /**
     * 获取在指定机构下的账号列表
     *
     * @return
     */
    @PostMapping(value = "/system/inUserCode/findUserCodeListByOfficeId")
    List<UserCodeInDTO> findUserCodeListByOfficeId(@RequestBody PublicIdReqVO reqVO);

    /**
     * 获取在指定机构下的账号列表
     *
     * @return
     */
    @PostMapping(value = "/system/user/getUserInfoByAccountId")
    User getUserInfoByAccountId(@RequestBody PublicIdReqVO reqVO);


    /**
     * @param
     * @Description:获取配置数据源
     * @Author: dong
     * @return:
     * @Date: 2022/2/23 15:15
     */
    @PostMapping(value = "/cfg/opendataSource/getConfigdataSource")
    Result<List<GlobalDataSourceRspDTO>> getConfigdataSource(@RequestBody DataSourceQueryReqDTO dataSourceQueryReqDTO);


    /**
     * @param globalConfigQueryReqDTO
     * @Description: 获取配置数据
     * @Author: dong
     * @return: com.zyaud.fzhx.common.model.Result<com.zyaud.idata.iam.client.dto.GlobalConfigRspDTO>
     * @Date: 2022/2/25 17:31
     */
    @PostMapping(value = "/cfg/in/dataSource/getConfigListByType")
    Result<GlobalConfigRspDTO> getConfigListByType(@RequestBody GlobalConfigQueryReqDTO globalConfigQueryReqDTO);

    /** 
    * @Description: 根据id获取数据源
    * @Author: dong 
    * @param publicIdReqVO
    * @return: com.zyaud.fzhx.common.model.Result<DataSourceRspVo>
    * @Date: 2022/3/11 18:38
    */
    @PostMapping(value = "/cfg/opendataSource/getConfigdataSourceById")
    Result<DataSourceRspVo> getConfigdataSourceById(@RequestBody PublicIdReqVO publicIdReqVO);
}
