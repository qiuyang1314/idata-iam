package com.zyaud.idata.iam.api.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(name = "lcclient", url = "${spring.feign.url.lc:localhost}")
public interface IExternalLangCaoClient {

    /**
     * 获取账号、用户、机构信息
     *
     * @param appCode
     * @param Authorization
     * @return
     */
    @GetMapping("/common-bsp/api/organ/app")
    Map<String, Object> app(@RequestParam("appCode") String appCode, @RequestParam("Authorization") String Authorization);


    
    /** 
    * @Description: 通过票据浪潮cas单点登录返回浪潮登录用户
    * @Author: dong 
    * @param ticket
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    * @Date: 2022/1/13 15:45
    */ 
    @GetMapping("/common-auth/login/cas")
    Map<String, Object> casLogin(@RequestParam("ticket") String ticket);


    /** 
    * @Description:
    * @Author: dong 
    * @param userId
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    * @Date: 2022/1/18 11:45
    */ 
    @GetMapping("/common-bsp/users/{userId}/roles")
    Map<String, Object> getRoleSbyUserId(@PathVariable("userId") String userId);

}
