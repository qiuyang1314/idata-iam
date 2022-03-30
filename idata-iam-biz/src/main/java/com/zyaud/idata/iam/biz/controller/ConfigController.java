package com.zyaud.idata.iam.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyaud.fzhx.binder.annotation.BindResult;
import com.zyaud.fzhx.cache.service.CacheService;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.common.utils.JsonUtils;
import com.zyaud.fzhx.core.model.IdEntity;
import com.zyaud.fzhx.core.mybatis.QueryBuilder;
import com.zyaud.fzhx.core.web.SuperController;
import com.zyaud.fzhx.iam.annotation.PreAuthorize;
import com.zyaud.fzhx.log.annotation.SysLog;
import com.zyaud.fzhx.log.enums.OptypeEnum;
import com.zyaud.idata.foundation.util.utils.Methods;
import com.zyaud.idata.iam.biz.model.dto.ConfigCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.ConfigPageDTO;
import com.zyaud.idata.iam.biz.model.dto.ConfigUpdateDTO;
import com.zyaud.idata.iam.biz.model.entity.Config;
import com.zyaud.idata.iam.biz.model.vo.*;
import com.zyaud.idata.iam.biz.service.IConfigService;
import com.zyaud.idata.iam.common.errorcode.ConfigMngErrorEnum;
import com.zyaud.idata.iam.common.utils.Constants;
import com.zyaud.idata.iam.common.vo.PublicIdPageReqVO;
import com.zyaud.idata.iam.common.vo.PublicIdReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fzhx-aicode
 * @since 2021-02-08
 */
@Slf4j
@RestController
@Api(tags = {"系统配置模块"})
@RequestMapping("/system/config")
public class ConfigController extends SuperController {

    @Resource
    private IConfigService crudService;

    @Resource
    private CacheService cacheService;

    @ApiOperation(value = "新增一条记录")
    @PostMapping(value = "/create")
    @SysLog(value = "新增", request = false, optype = OptypeEnum.INSERT)
    public Result<Boolean> create(@Validated @RequestBody ConfigCreateDTO createDTO) {
        Config entity = BeanUtil.toBean(createDTO, Config.class);
        return ok(crudService.save(entity));
    }

    @ApiOperation(value = "删除记录")
    @PostMapping(value = "/delete")
    @SysLog(value = "'删除:' + #ids", optype = OptypeEnum.DELETE)
    public Result<Boolean> delete(@RequestBody List<? extends Serializable> ids) {
        return ok(crudService.removeByIds(ids));
    }

    @ApiOperation(value = "修改记录")
    @PostMapping(value = "/update")
    @SysLog(value = "'修改:' + #updateDTO?.id", request = false, optype = OptypeEnum.UPDATE)
    public Result<Boolean> update(@Validated(IdEntity.Update.class) @RequestBody ConfigUpdateDTO updateDTO) {
        Config entity = BeanUtil.toBean(updateDTO, Config.class);
        return ok(crudService.updateById(entity));
    }

    @ApiOperation(value = "单条记录查询")
    @PostMapping(value = "/get")
    @SysLog(value = "'查询:' + #id", optype = OptypeEnum.SELECT)
    public Result<Config> get(@RequestParam Serializable id) {
        return ok(crudService.getById(id));
    }

    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    public Result<IPage<Config>> page(@Validated @RequestBody ConfigPageDTO pageDTO) {
        IPage<Config> page = pageDTO.getPage();
        Wrapper<Config> query = QueryBuilder.queryWrapper(pageDTO);
        return ok(crudService.page(page, query));
    }


    @ApiOperation(value = "获取后端配置")
    @PostMapping(value = "/getConfig")
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<HDConfigVO> getConfig() {
        HDConfigVO hdConfigVO = crudService.getHDConfig();
        return ok(hdConfigVO);
    }


    @ApiOperation(value = "设置后端配置")
    @PostMapping(value = "/setConfig")
    @SysLog(value = "'设置密码过期时间:' + #setConfigRepVO?.pastDueTime + ';'" +
            "设置是否单终端登录:'+'#setConfigRepVO?.isOneWayLogin + ';'" +
            "设置是否推送登录日志:'+'#setConfigRepVO?.isSendSyslog + ';'",
            optype = OptypeEnum.UPDATE)
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result setConfig(@RequestBody HDConfigVO HDConfigVO) {
        crudService.setHDConfig(HDConfigVO);
        return ok();
    }

    @ApiOperation(value = "根据应用获取配置信息")
    @PostMapping(value = "/findIpConfigByAppId")
    @BindResult
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<IPage<FindIpConfigByAppIdVO>> findIpConfigByAppId(@RequestBody PublicIdPageReqVO publicIdPageReqVO) {
        //获取配置
        Config config = crudService.findConfigByCtypeAndCcode(Constants.SYS_CONFIG, Constants.SYS_IPCONFIG);
        //获取应用下的ip配置
        List<IpConfigVO> ipConfigVOS = crudService.findIpConfigVOByConfigAndAppId(config, publicIdPageReqVO.getId());
        //转换
        List<FindIpConfigByAppIdVO> configs = ipConfigVOS.stream()
                .map(t -> BeanUtil.toBean(t, FindIpConfigByAppIdVO.class).setIpconfigs(getIpSection(t.getIpconfig(), t.getIps())))
                .collect(Collectors.toList());
        //ip数
        configs.forEach(t -> t.setIpSum(t.getIps().size()).setIps(t.getIpconfigs()));
        //分页
        List<FindIpConfigByAppIdVO> page = Methods.getPage((int) publicIdPageReqVO.getCurrent(), (int) publicIdPageReqVO.getSize(), configs);
        //组装
        IPage<FindIpConfigByAppIdVO> iPage = new Page(publicIdPageReqVO.getCurrent(), publicIdPageReqVO.getSize(), configs.size());
        iPage.setRecords(page);
        return ok(iPage);
    }

    @ApiOperation(value = "设置ip")
    @PostMapping(value = "/setIpConfig")
    @SysLog(value = "'设置访问ip限制,应用id:' + setIpConfigVO?.appId+',设置的ip:' + setIpConfigVO?.ipconfig",
            optype = OptypeEnum.UPDATE)
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result setIpConfig(@Validated @RequestBody SetIpConfigVO setIpConfigVO) {
        //检验
        verify(setIpConfigVO);
        //获取配置
        Config config = crudService.findConfigByCtypeAndCcode(Constants.SYS_CONFIG, Constants.SYS_IPCONFIG);
        IpConfigVO ipConfigVO = BeanUtil.toBean(setIpConfigVO, IpConfigVO.class);
        ipConfigVO.setIps(getIps(setIpConfigVO.getIpconfig()))
                .setIpconfig(setIpConfigVO.getIpconfig())
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        if (ObjectUtil.isNull(config)) {
            config = new Config();
            config.setCtype(Constants.SYS_CONFIG)
                    .setCcode(Constants.SYS_IPCONFIG)
                    .setCitem("ip配置")
                    .setCvalue(JsonUtils.toJson(Arrays.asList(ipConfigVO)))
                    .setOrderIndex(0);
            crudService.save(config);
        } else {
            List<IpConfigVO> ipConfigVOS = crudService.findIpConfigVOByConfigAndAppId(config, null);
            List<IpConfigVO> notIps = ipConfigVOS.stream().filter(t -> !t.getAppId().equals(setIpConfigVO.getAppId())).collect(Collectors.toList());
            List<IpConfigVO> isIps = ipConfigVOS.stream().filter(t -> t.getAppId().equals(setIpConfigVO.getAppId())).collect(Collectors.toList());
            if (isIps.size() > 0) {
                ipConfigVO.setCreateTime(isIps.get(0).getCreateTime());
            }
            if (ObjectUtil.isEmpty(ipConfigVO.getCreateTime())) {
                ipConfigVO.setCreateTime(ipConfigVO.getUpdateTime());
            }
            notIps.add(ipConfigVO);
            config.setCvalue(JsonUtils.toJson(notIps));
            crudService.updateById(config);
        }

        //清除缓存
        cacheService.del(Constants.IP_CONFIG);
        return ok();
    }

    @ApiOperation(value = "返回设置ip")
    @PostMapping(value = "/getIpConfig")
    public Result<GetIpConfigVO> getIpConfig(@RequestBody PublicIdReqVO publicIdReqVO) {
        //获取配置
        Config config = crudService.findConfigByCtypeAndCcode(Constants.SYS_CONFIG, Constants.SYS_IPCONFIG);
        //获取应用下的ip
        List<IpConfigVO> ipConfigVOS = crudService.findIpConfigVOByConfigAndAppId(config, publicIdReqVO.getId());
        if (ipConfigVOS.size() == 0) {
            return ok(new GetIpConfigVO());
        } else {
            IpConfigVO ipConfigVO = ipConfigVOS.get(0);
            GetIpConfigVO getIpConfigVO = BeanUtil.toBean(ipConfigVO, GetIpConfigVO.class);
            List<IpSettingIdxVO> ipSettingIdxVOS = new ArrayList<>();
            if (ObjectUtil.isNotEmpty(ipConfigVO.getIpconfig())) {
                ipSettingIdxVOS = ipConfigVO.getIpconfig().stream().map(t -> BeanUtil.toBean(t, IpSettingIdxVO.class)).collect(Collectors.toList());
            } else {
                ipSettingIdxVOS.addAll(getIpSettingIdxVO(ipConfigVO.getIps()));
            }
            getIpConfigVO.setIpconfig(ipSettingIdxVOS);
            for (int i = 0; i < getIpConfigVO.getIpconfig().size(); i++) {
                getIpConfigVO.getIpconfig().get(i).setIdx(i);
            }
            return ok(getIpConfigVO);
        }
    }

    public List<IpSettingIdxVO> getIpSettingIdxVO(List<String> ips) {
        List<IpSettingIdxVO> ipSettingIdxVOS = new ArrayList<>();
        for (String ip : ips) {
            String[] split = ip.split("\\.");
            if (split.length > 0) {
                IpSettingIdxVO ipSettingIdxVO = new IpSettingIdxVO();
                ipSettingIdxVO.setFrist(split[0]);
                if (split.length > 3) {

                    ipSettingIdxVO.setSecond(split[1])
                            .setThird(split[2])
                            .setFourth(split[3]);
                }
                ipSettingIdxVOS.add(ipSettingIdxVO);
            }
        }
        return ipSettingIdxVOS;
    }

    public List<String> getIps(Set<IpSettingVO> ipconfig) {
        List<String> ips = new ArrayList<>();
        //单个ip
        List<IpSettingVO> settingVOS = ipconfig.stream().filter(t -> StrUtil.isBlank(t.getFifth())).collect(Collectors.toList());
        if (settingVOS.size() > 0) {
            for (IpSettingVO ipSettingVO : settingVOS) {
                ips.add(ipSettingVO.getFrist() + "." + ipSettingVO.getSecond() + "." + ipSettingVO.getThird() + "." + ipSettingVO.getFourth());
            }
        }
        //ip段
        List<IpSettingVO> ipSettingVOS = ipconfig.stream().filter(t -> StrUtil.isNotBlank(t.getFifth())).collect(Collectors.toList());
        if (ipSettingVOS.size() > 0) {
            for (IpSettingVO ipSettingVO : ipSettingVOS) {
                int fifth = NumberUtil.parseInt(ipSettingVO.getFifth());
                int fourth = NumberUtil.parseInt(ipSettingVO.getFourth());
                for (int i = fourth; i <= fifth; i++) {
                    ips.add(ipSettingVO.getFrist() + "." + ipSettingVO.getSecond() + "." + ipSettingVO.getThird() + "." + i);
                }
            }
        }
        return ips;
    }

    public Set<String> getIpSection(Set<IpSettingVO> ipconfig, List<String> ipList) {
        Set<String> ips = new HashSet<>();
        //单个ip
        if (ObjectUtil.isNotEmpty(ipconfig)) {
            for (IpSettingVO ipSettingVO : ipconfig) {
                String ip = ipSettingVO.getFrist() + "." + ipSettingVO.getSecond() + "." + ipSettingVO.getThird() + "." + ipSettingVO.getFourth();
                if (StrUtil.isNotBlank(ipSettingVO.getFifth())) {
                    ip += "-" + ipSettingVO.getFifth();
                }
                ips.add(ip);
            }
        } else {
            ips.addAll(ipList);
        }
        return ips;
    }

    public Integer isIpSection(String num) {
        Integer ipNum = NumberUtil.parseInt(num);
        if (ipNum < 0 || ipNum > 255) {
            BizAssert.fail(ConfigMngErrorEnum.IP_SECTION);
        }
        return ipNum;
    }

    public void verify(SetIpConfigVO setIpConfigVO) {
        setIpConfigVO.getIpconfig().forEach(t -> t.setFifth(StrUtil.isBlank(t.getFifth()) ? null : t.getFifth()));
        for (IpSettingVO ipSettingVO : setIpConfigVO.getIpconfig()) {
            BizAssert.isTrue(StrUtil.isNotBlank(ipSettingVO.getFrist()), ConfigMngErrorEnum.FIRST_IP_CANNOT_BE_NULL);
            BizAssert.isTrue(StrUtil.isNotBlank(ipSettingVO.getSecond()), ConfigMngErrorEnum.SECOND_IP_CANNOT_BE_NULL);
            BizAssert.isTrue(StrUtil.isNotBlank(ipSettingVO.getThird()), ConfigMngErrorEnum.THIRD_IP_CANNOT_BE_NULL);
            BizAssert.isTrue(StrUtil.isNotBlank(ipSettingVO.getFourth()), ConfigMngErrorEnum.FOURTH_IP_CANNOT_BE_NULL);

            isIpSection(ipSettingVO.getFrist());
            isIpSection(ipSettingVO.getSecond());
            isIpSection(ipSettingVO.getThird());
            Integer fourth = isIpSection(ipSettingVO.getFourth());
            if (StrUtil.isNotBlank(ipSettingVO.getFifth())) {
                Integer fifth = isIpSection(ipSettingVO.getFifth());
                BizAssert.isTrue(fourth < fifth,
                        ConfigMngErrorEnum.FOURTH_IP_CANNOT_GREATER_THAN_OR_EQUALS_FIFTH_IP);
            }
        }
    }

    @ApiOperation(value = "根据应用编码查系统配置项")
    @PostMapping(value = "/getSysCfgItemByCode")
    public Result<BizSysConfigVO> getSysCfgItemByCode(@Validated @RequestBody AppCodeVO appCodeVO) {
        return ok(crudService.getSysCfgItemByCode(appCodeVO.getAppCode()));
    }

    @ApiOperation(value = "根据应用编码更新应用及安扫配置")
    @PostMapping(value = "/updateAppAndScanConfig")
    public Result<Boolean> updateAppAndScanConfig(@Validated @RequestBody BizSysConfigVO reqVO) {
        return ok(crudService.updateAppAndScanConfig(reqVO));
    }

    @ApiOperation(value = "登录页配置")
    @PostMapping(value = "/updateLoginPageConfig")
    @SysLog(value = "'设置登录页配置,系统名称:' + reqVO?.loginSysName + ',是否开启验证码:' + reqVO?.enableCaptcha",
            optype = OptypeEnum.UPDATE)
    @PreAuthorize(value = "hasLogin()", msg = "亲，你要登录后才能使用哦..")
    public Result<Boolean> updateLoginPageConfig(@Validated @RequestBody LoginPageConfigReqVO reqVO) {
        return ok(crudService.updateLoginPageConfig(reqVO));
    }

    @ApiOperation(value = "获取登录页配置")
    @PostMapping(value = "/getLoginPageConfig")
    public Result<LoginPageConfigRspVO> getLoginPageConfig() {
        return ok(crudService.getLoginPageConfig());
    }


    @ApiOperation(value = "验证文件是否上传")
    @PostMapping(value = "/exist")
    public Result<CheckFileExistRspVO> exist(@RequestParam("md5") Serializable md5) {
        return Result.ok(crudService.exist(md5));
    }

    @ApiOperation(value = "上传图片文件")
    @PostMapping(value = "/uploadImage", name = "上传图片文件")
    public Result<FileUploadRspVO> uploadImage(@RequestParam("name") String name,
                                               @RequestParam("md5") String md5,
                                               @RequestParam("size") Long size,
                                               @RequestParam("chunks") Integer chunks,
                                               @RequestParam("chunk") Integer chunk,
                                               @RequestParam("file") MultipartFile file) throws IOException {
        return ok(crudService.uploadImage(name, md5, size, chunks, chunk, file));
    }


    /**
     * 创建文件
     *
     * @throws IOException
     */
    public static boolean createdAndWriteTxtFile(String path, String newStr) throws IOException {
        boolean flag = false;
        path = path + "errMsg.txt";
        File filename = new File(path);
        if (!filename.exists()) {
            filename.createNewFile();
        }
        // 先读取原有文件内容，然后进行写入操作
        String filein = newStr + "\r\n";
        String temp = "";
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            // 文件路径
            File file = new File(path);
            // 将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            // 保存该文件原有的内容
            for (int j = 1; (temp = br.readLine()) != null; j++) {
                buf = buf.append(temp);
                // System.getProperty("line.separator")
                // 行与行之间的分隔符 相当于“\n”
                buf = buf.append(System.getProperty("line.separator"));
            }
            buf.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (IOException e1) {
            // TODO 自动生成 catch 块
            throw e1;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return flag;
    }

}
