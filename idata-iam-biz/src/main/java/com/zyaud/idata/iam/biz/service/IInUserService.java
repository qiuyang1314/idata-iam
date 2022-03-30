package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.biz.model.dto.UserCreateDTO;
import com.zyaud.idata.iam.biz.model.entity.User;
import com.zyaud.idata.iam.biz.model.entity.UserCode;
import com.zyaud.idata.iam.biz.model.vo.UserCodeAASVO;
import com.zyaud.idata.iam.biz.model.vo.UserVO;
import com.zyaud.idata.iam.client.dto.UserInDTO;

import java.util.List;
import java.util.Map;

public interface IInUserService extends IService<User> {
    boolean create(UserCreateDTO createDTO);
    List<UserInDTO> turnUserList2UserVOList(List<User> userList);
    List<UserInDTO> list(String userName);
    Map<String /* userCodeId */, String /* userName */> getUserNamesByIds(List<String> userCodeIds);
    List<UserCode> findUserCodeListByName(String name);
    List<UserCodeAASVO> findUserListByOfficeId(String officeId);
}
