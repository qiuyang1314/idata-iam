// package com.zyaud.idata.iam.biz.controller;
//
// import com.zyaud.fzhx.common.model.Result;
// import com.zyaud.fzhx.core.web.SuperController;
// import com.zyaud.fzhx.log.annotation.SysLog;
// import com.zyaud.fzhx.log.enums.OptypeEnum;
// import com.zyaud.idata.iam.biz.service.IExternalLangService;
// import com.zyaud.idata.iam.common.vo.PublicIdReqVO;
// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiOperation;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.validation.annotation.Validated;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import java.io.IOException;
// import java.util.List;
//
//
// @Slf4j
// @RestController
// @RequestMapping("/external/langcao-api")
// @Api(value = "ExternalLangCaoApi", tags = "浪潮系统服务模块")
// public class ExternalLangController extends SuperController {
//
//     @Autowired
//     private IExternalLangService externalLangService;
//
//     @ApiOperation(value = "同步浪潮数据")
//     @PostMapping(value = "/synchronizationData")
//     @SysLog(value = "'同步浪潮数据,应用id:' + #publicIdReqVO.id", optype = OptypeEnum.UPDATE,request = false)
//     public Result synchronizationData(@Validated @RequestBody PublicIdReqVO publicIdReqVO) throws IOException {
//         //登录浪潮系统
//         String token = externalLangService.login();
//         log.info("浪潮登录token->:{}",token);
//         //获取浪潮系统机构、用户、账号信息
//         List<String> useIds = externalLangService.app(token);
//         //新增和绑定角色
//         // externalLangService.relevanceRole(useIds, publicIdReqVO.getId());
//         return ok("同步成功");
//     }
//
//
//     /**
//     * @Description: 浪潮cas登录返回用户id
//     * @Author: dong
//     * @param publicIdReqVO
//     * @return: com.zyaud.fzhx.common.model.Result
//     * @Date: 2022/1/13 15:32
//     */
//     @ApiOperation(value = "浪潮cas登录返回用户id")
//     @PostMapping(value = "/casLogin")
//     @SysLog(value = "'浪潮cas登录返回用户id,票据id:' + #publicIdReqVO.id",request = false)
//     public Result<String> casLogin(@Validated @RequestBody PublicIdReqVO publicIdReqVO) {
//         return ok(externalLangService.casLogin(publicIdReqVO.getId()));
//     }
//
//
//
// }
