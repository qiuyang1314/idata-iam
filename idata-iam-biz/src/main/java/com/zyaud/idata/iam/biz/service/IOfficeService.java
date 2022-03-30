package com.zyaud.idata.iam.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyaud.idata.iam.biz.model.dto.OfficeCreateDTO;
import com.zyaud.idata.iam.biz.model.dto.OfficePageDTO;
import com.zyaud.idata.iam.biz.model.dto.OfficeUpdateDTO;
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
public interface IOfficeService extends IService<Office> {
    Map<String, String> getOfficeNameById(Set<Serializable> ids);

    void repetitionName(String name, String id);

    void repetitionCode(String code, String id);

    List<Office> findByCodes(List<String> codes);

    List<OfficeTreeVO> getOfficeTree(List<Office> list);

    boolean createOffice(OfficeCreateDTO createDTO);

    void updateOffice(OfficeUpdateDTO updateDTO);

    boolean deleteOfficeByIds(List<String> ids);

    List<OfficeTreeVO> getOfficeList(OfficePageDTO pageDTO);
}
