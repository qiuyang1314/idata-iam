package com.zyaud.idata.iam.biz.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.zyaud.cdi.comet.client.client.ICometClient;
import com.zyaud.cdi.comet.client.dto.BusinessReqParams;
import com.zyaud.fzhx.common.model.Result;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.fzhx.iam.token.Token;
import com.zyaud.fzhx.iam.token.TokenStore;
import com.zyaud.fzhx.iam.token.TokenUtils;
import com.zyaud.idata.iam.api.req.CometReqVO;
import com.zyaud.idata.iam.biz.mapper.UserCodeMapper;
import com.zyaud.idata.iam.biz.model.entity.UserCode;
import com.zyaud.idata.iam.biz.service.IAppService;
import com.zyaud.idata.iam.biz.service.IAsyncService;
import com.zyaud.idata.iam.common.errorcode.AccountMngErrorEnum;
import com.zyaud.idata.iam.common.utils.Constants;
import io.jsonwebtoken.Claims;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.SyslogIF;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class AsyncServiceImpl implements IAsyncService {
    @Value("${spring.syslog_ip:172.18.119.110}")
    private String syslog_ip;
    @Value("${spring.syslog_port:514}")
    private int syslog_port;
    @Value("${fzhx.iam.token.secret}")
    private String secret;
    @Resource
    private UserCodeMapper userCodeMapper;
    @Resource
    private TokenStore tokenStore;
    @Resource
    private IAppService appService;
    @Resource
    private ICometClient cometClient;

    @Override
    @Async
    public void verifyOneTerminal(String loginName, String ip, Token accessToken, Token refreshToken) {

        UserCode userCode = userCodeMapper.getByLoginName(loginName);
        BizAssert.isNotEmpty(userCode, AccountMngErrorEnum.USER_CODE_OR_PASSWORD_IS_FALSE);

        List<Token> tokens = tokenStore.findTokensByUserId(loginName);
        if (ObjectUtil.isEmpty(tokens)) {
            return;
        }

        tokens = tokens.stream().filter(t -> !t.getValue().equals(accessToken.getValue()))
                .filter(t -> !t.getValue().equals(refreshToken.getValue())).collect(Collectors.toList());
        //未登录
        Set<String> tnos = new HashSet<>();
        for (Token token : tokens) {
            if (TokenUtils.isTokenExpired(token.getValue(), this.secret)) {
                continue;
            }
            Claims claims = TokenUtils.parserJwt(token.getValue(), this.secret);
            if (ObjectUtil.isNotNull(claims.get("ext"))) {
                Map<String, Object> ext = (Map<String, Object>) claims.get("ext");
                tnos.add(String.valueOf(ext.get("tno")));
            }
        }
        //删除token
        tokens.forEach(t -> tokenStore.removeToken(loginName, t.getValue()));

        List<String> appIdByIsuseable = appService.findAppCodeByIsuseable();

        //登录时间
        LocalDateTime loginTime = LocalDateTime.now();
        for (String appCode : appIdByIsuseable) {
            for (String tno : tnos) {
                BusinessReqParams cometReqVO = new BusinessReqParams(userCode.getId(), Constants.COMET_METHOD_LOGIN,
                        Constants.IAM_CODE, Result.ok());
                cometReqVO.setTno(tno);
                Result result = cometReqVO.getResult();
                Map<String, Object> map = new HashMap<>();
                map.put("loginName", loginName);
                map.put("ip", ip);
                map.put("loginTime", loginTime);
                result.setExtra(map);
                try {
                    cometClient.processMsg(appCode, cometReqVO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Async
    @Override
    public void singleTerminal(String loginName, String userCodeId, String ip, String accessToken, String refreshToken) {
        //验证单终端登录并处理
        List<Token> tokens = tokenStore.findTokensByUserId(loginName);
        if (tokens.size() == 0) {
            return;
        }
        tokens = tokens.stream().filter(t -> !t.getValue().equals(accessToken))
                .filter(t -> !t.getValue().equals(refreshToken))
                .collect(Collectors.toList());
        //未登录
        Set<String> tnos = new HashSet<>();
        for (Token token : tokens) {
            if (TokenUtils.isTokenExpired(token.getValue(), this.secret)) {
                continue;
            }
            Claims claims = TokenUtils.parserJwt(token.getValue(), this.secret);
            if (ObjectUtil.isNotNull(claims.get("ext"))) {
                Map<String, Object> ext = (Map<String, Object>) claims.get("ext");
                tnos.add(String.valueOf(ext.get("tno")));
            }
        }
        //删除token
        tokens.forEach(t -> tokenStore.removeToken(loginName, t.getValue()));

        List<String> appIdByIsuseable = appService.findAppCodeByIsuseable();

        //登录时间
        LocalDateTime loginTime = LocalDateTime.now();
        for (String appCode : appIdByIsuseable) {
            for (String tno : tnos) {
                BusinessReqParams cometReqVO = new BusinessReqParams(userCodeId, Constants.COMET_METHOD_LOGIN,
                        Constants.IAM_CODE, Result.ok());
                cometReqVO.setTno(tno);
                Result result = cometReqVO.getResult();
                Map<String, Object> map = new HashMap<>();
                map.put("loginName", loginName);
                map.put("ip", ip);
                map.put("loginTime", loginTime);
                result.setExtra(map);
                try {
                    cometClient.processMsg(appCode, cometReqVO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    @Async
    public void sysLog(String msg) {
        String host = syslog_ip;
        int port = syslog_port;
        SyslogIF syslog = Syslog.getInstance(SyslogConstants.UDP);// 协议
        syslog.getConfig().setHost(host);// 接收服务器
        syslog.getConfig().setPort(port);// 端口
        syslog.getConfig().setMaxMessageLength(1024000);
        try {
            syslog.log(0, URLDecoder.decode(msg, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
