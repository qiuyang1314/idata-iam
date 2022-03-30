package com.zyaud.idata.iam.biz.mapper;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyaud.fzhx.common.utils.BizAssert;
import com.zyaud.idata.iam.biz.model.entity.Config;

import java.util.List;

/**
 * <p>
 * ConfigMapper 接口
 *
 * </p>
 *
 * @author fzhx-aicode
 * @since 2021-02-08
 */
public interface ConfigMapper extends BaseMapper<Config> {
    /**
     * 根据配置项编码获取配置
     *
     * @param ctype
     * @param ccode
     * @return
     */
    default Config findConfigByCtypeAndCcode(String ctype, String ccode) {
        if (StrUtil.isEmpty(ctype)) {
            BizAssert.fail("配置类型不能为空");
        }
        if (StrUtil.isEmpty(ccode)) {
            BizAssert.fail("配置项编码不能为空");
        }
        QueryWrapper<Config> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Config.CTYPE, ctype)
                .eq(Config.CCODE, ccode);
        return this.selectOne(queryWrapper);
    }

    default List<Config> listConfigByCTypeAndCCode(String ctype, String ccode) {
        QueryWrapper<Config> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(ctype), Config.CTYPE, ctype)
                .eq(StrUtil.isNotBlank(ccode), Config.CCODE, ccode);
        return this.selectList(queryWrapper);
    }
}
