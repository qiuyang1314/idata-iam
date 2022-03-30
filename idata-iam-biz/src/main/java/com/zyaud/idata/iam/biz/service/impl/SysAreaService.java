package com.zyaud.idata.iam.biz.service.impl;

import com.zyaud.fzhx.common.model.Result;
import com.zyaud.idata.foundation.infra.client.client.SysAreaClient;
import com.zyaud.idata.foundation.infra.client.vo.PublicIdReqVO;
import com.zyaud.idata.foundation.infra.client.vo.SysAreaInfoRspVO;
import com.zyaud.idata.foundation.infra.client.vo.SysAreaListRspVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class SysAreaService {

    @Resource
    private SysAreaClient sysAreaClient;


    public Map<String, String> getAreaNameMap() {
        Map<String, String> areaNameMap = new HashMap<>();
        try {
            Result<List<SysAreaListRspVO>> result = sysAreaClient.listArea();
            List<SysAreaListRspVO> areaList = result.getData();
            areaNameMap = areaList.stream().collect(Collectors.toMap(SysAreaListRspVO::getAreaCode, SysAreaListRspVO::getAreaName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areaNameMap;
    }

    public SysAreaInfoRspVO getById(String id){
        try {
            PublicIdReqVO idReqVO = new PublicIdReqVO();
            idReqVO.setId(id);
            Result<SysAreaInfoRspVO> result = sysAreaClient.getAreaById(idReqVO);
            return result.getData();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
