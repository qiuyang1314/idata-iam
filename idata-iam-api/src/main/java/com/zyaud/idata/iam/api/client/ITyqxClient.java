package com.zyaud.idata.iam.api.client;

import com.bjsasc.drap.pt.context.ContextUser;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.log.annotation.SysLog;
import com.zyaud.fzhx.log.enums.OptypeEnum;
import com.zyaud.idata.iam.api.req.DTO.OnlyTokenReqDto;
import com.zyaud.idata.iam.api.req.TokenAnalysisReqVO;
import com.zyaud.idata.iam.api.resp.TokenAnalysisRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: idata-iam
 * @description: 神软统一权限服务模块
 * @author: qiuyang
 * @create: 2022-02-18 10:43
 **/
@RequestMapping({"/tyqx"})
@Api(tags = "神软统一权限服务模块")
public interface ITyqxClient {

    @ApiOperation(value = "获取用户")
    @PostMapping(value = "/getUserInfo")
    @SysLog(value = "'获取用户sso_code:' + #sso_code", optype = OptypeEnum.SELECT)
    public Result<ContextUser> getUserInfo(@RequestParam String sso_code);

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/registerUser")
    @SysLog(value = "'用户注册userName:' + #contextUser.userName + ", optype = OptypeEnum.INSERT)
    public Result<ContextUser> registerUser(@RequestBody ContextUser contextUser, @RequestParam String passWord);

    @ApiOperation(value = "全量用户同步")
    @PostMapping(value = "/registerUserTotal")
    @SysLog(value = "'全量用户同步:' + #tokenDto.token + ", optype = OptypeEnum.INSERT)
    public Result<String> registerUserTotal(@RequestBody OnlyTokenReqDto tokenDto);

}
