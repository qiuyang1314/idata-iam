package com.zyaud.idata.iam.application.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.idata.iam.biz.model.entity.App;
import com.zyaud.idata.iam.biz.model.entity.Config;
import com.zyaud.idata.iam.biz.model.vo.BizSysConfigVO;
import com.zyaud.idata.iam.biz.model.vo.IpConfigVO;
import com.zyaud.idata.iam.biz.service.IAppService;
import com.zyaud.idata.iam.biz.service.IConfigService;
import com.zyaud.idata.iam.common.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class IpInterceptor implements HandlerInterceptor {

    @Resource
    private IAppService appService;
    @Resource
    private IConfigService configService;
    @Value("${spring.ipConfig}")
    private boolean ipConfig;
    @Value("${spring.ip-white-list}")
    private String ipWhiteList;

    private void setResponseHeader(HttpServletResponse response) {
        BizSysConfigVO vo = configService.getSysConfigByAppCode(Constants.IAM_CODE);
        if (ObjectUtil.isNull(vo)) {
            response.setHeader("X-Content-Type-Options", "nosniff");
            response.setHeader("X-XSS-Protection", "1; mode=block");
            return;
        }
        /**
         * 若要返回cookie、携带seesion等信息则将此项设置为true
         */
        if (Constants.YES.equals(vo.getOpenAccessControlAllowCredentials())) {
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }
        /**
         * 简称为HSTS。它允许一个HTTPS网站，要求浏览器总是通过HTTPS来访问它
         */
        if (Constants.YES.equals(vo.getOpenStrictTransportSecurity())) {
            response.setHeader("strict-transport-security", "max-age=16070400; includeSubDomains");
        }
        /**
         * 这个响应头主要是用来定义页面可以加载哪些资源，减少XSS的发生
         */
        if (Constants.YES.equals(vo.getOpenContentSecurityPolicy())) {
            response.setHeader("Content-Security-Policy", "default-src 'self'");
        }

        /**
         * 互联网上的资源有各种类型，通常浏览器会根据响应头的Content-Type字段来分辨它们的类型。通过这个响应头可以禁用浏览器的类型猜测行为
         */
        if (Constants.YES.equals(vo.getOpenXContentTypeOptions())) {
            response.setHeader("X-Content-Type-Options", "nosniff");
        }

        /**
         * 1; mode=block：启用XSS保护，并在检查到XSS攻击时，停止渲染页面
         */
        if (Constants.YES.equals(vo.getOpenXContentTypeOptions())) {
            response.setHeader("X-XSS-Protection", "1; mode=block");
        }

        /**
         * SAMEORIGIN：不允许被本域以外的页面嵌入；
         */
        if (Constants.YES.equals(vo.getOpenXFrameOptions())) {
            response.setHeader("X-Frame-Options", "SAMEORIGIN");
        }


//        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//        /**
//         * 允许跨域的请求方式
//         */
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
//        /**
//         * 预检请求的间隔时间
//         */
//        response.setHeader("Access-Control-Max-Age", "3600");
//        /**
//         * 允许跨域请求携带的请求头
//         */
//        response.setHeader("Access-Control-Allow-Headers",
//                "Origin, No-Cache, X-Requested-With, If-Modified-Since, " +
//                        "Pragma, Last-Modified, Cache-Control, Expires, Content-Type," +
//                        " X-E4M-With,userId,token,Access-Control-Allow-Headers");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        setResponseHeader(response);
        if (ipConfig) {
            String ip = ServletUtil.getClientIP(request);
            //是否是白名单中ip
            String[] ips = ipWhiteList.split(",");
            if (ips.length > 0) {
                List<String> ipWhiteList = Arrays.asList(ips);
                if (ipWhiteList.contains(ip)) {
                    return true;
                }
            }
            IpConfigVO ipConfigVO = getIpConfigVO(Constants.IAM_CODE);
            if (ObjectUtil.isNull(ipConfigVO)) {
                return false;
            }
            //ip地址逻辑处理
            if (!ipConfigVO.getIps().contains(ip)) {
                BizAssert.fail("很抱歉,您当前IP:" + ip + ",不能登录系统,请联系管理员");
                return false;
            }
        }
        return true;
    }

    @Cacheable(cacheNames = Constants.IP_CONFIG, key = "'" + Constants.APP_CODE_KEY + "'" + "+#appCode", unless = "#result == null")
    public IpConfigVO getIpConfigVO(String appCode) {
        IpConfigVO ipConfigVO = new IpConfigVO();

        App app = appService.getAppByAppCode(appCode);
        if (ObjectUtil.isNull(app)) {
            BizAssert.fail("很抱歉，当前访问应用不存在，请联系管理员。");
            return null;
        }
        //获取配置
        Config config = configService.findConfigByCtypeAndCcode(Constants.SYS_CONFIG, Constants.SYS_IPCONFIG);
        //获取应用下的ip配置
        List<IpConfigVO> ipConfigVOS = configService.findIpConfigVOByConfigAndAppId(config, app.getId());
        if (ipConfigVOS.size() == 0) {
            BizAssert.fail("很抱歉，应用配置未初始化，请联系管理员。");
            return null;
        }
        ipConfigVO = ipConfigVOS.get(0);
        if (ObjectUtil.isNull(ipConfigVO)) {
            BizAssert.fail("很抱歉，ip配置未找到，请联系管理员。");
            return null;
        }

        return ipConfigVO;
    }

}
