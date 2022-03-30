package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.client.dto.OfficeInDTO;
import com.zyaud.idata.iam.biz.model.entity.Office;
import com.zyaud.idata.iam.biz.model.vo.OfficeTreeVO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 机构部门 服务类
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface IInOfficeService extends IService<Office> {
    Map<String, String> getOfficeNameById(Set<Serializable> ids);

    void verifyName(String name, String id);

    void verifyCode(String code, String id);

    void createOffice(Office office);

    void updateOffice(Office office);


    List<Office> findByCodes(List<String> codes);

    List<OfficeTreeVO> getOfficeTree(List<Office> list);

    /**
     * 通过机构类型获取机构列表
     * @param orgType
     * @return
     */
    List<OfficeInDTO> getOfficeListByType(String orgType);
}
