package com.zyaud.idata.iam.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyaud.idata.iam.biz.model.entity.SysLog;
import com.zyaud.idata.iam.biz.model.vo.GetAppCodesVO;

import java.util.List;

/**
 * <p>
 * SysLogMapper 接口
 * 系统日志
 * </p>
 *
 * @author fzhx-aicode
 * @since 2020-05-15
 */
public interface SysLogMapper extends BaseMapper<SysLog> {
    List<GetAppCodesVO> getAppCodes();

    List<String> getmodulesByAppCode(String appCode);
}
