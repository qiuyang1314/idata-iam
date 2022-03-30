package com.zyaud.idata.iam.biz.strategy;

import com.zyaud.fzhx.iam.core.FzhxIamUser;
import com.zyaud.fzhx.iam.token.Token;
import com.zyaud.idata.iam.biz.model.entity.App;
import com.zyaud.idata.iam.biz.model.entity.Menu;
import com.zyaud.idata.iam.biz.model.vo.LoginRespVO;

import java.util.List;

public interface UserLoginStrategy {

    LoginRespVO login(String loginName, String passwd, String returnURL);

    //TODO 改名字 singleTerminal
    LoginRespVO oneTerminalLogin(String loginName, String passwd, String returnURL);

    List<Menu> getMenuByUserCodeId(String userCodeId, String appId);

    List<Menu> findMenusByUserCodeAndRoleForSpecialApp(String userCodeId, String roleId, App app);

    Token getToken(FzhxIamUser fzhxIamUser, Long expire);

    void verifyCaptcha(String key, String captcha);
}
