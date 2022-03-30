package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.biz.model.entity.User;
import com.zyaud.idata.iam.biz.model.vo.UserVO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface IUserService extends IService<User> {
    User login();

    Map<String, String> getUserNameById(Set<Serializable> ids);

    List<UserVO> getUserVOs(List<User> users);

    boolean verifyIdCard(String idCard, String id);
}
