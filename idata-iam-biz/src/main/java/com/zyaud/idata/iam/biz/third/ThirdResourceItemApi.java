package com.zyaud.idata.iam.biz.third;

import com.zyaud.idata.iam.biz.third.appResultDto.AppResultDto;
import com.zyaud.idata.iam.biz.third.resourceItem.RemoteResourceItemCreateReqDto;
import com.zyaud.idata.iam.biz.third.resourceItem.RemoteResourceItemDeleteReqDto;
import com.zyaud.idata.iam.biz.third.resourceItem.RemoteResourceItemUpdateReqDto;

/**
 * @program: idata-iam
 * @description: 第三方资源项同步接口
 * @author: qiuyang
 * @create: 2022-01-04 15:06
 **/
public interface ThirdResourceItemApi {

    /**
    * @Description 新增资源项
    * @Author qiuyang
    * @param remoteResourceItemCreateReqDto
    * @return AppResultDto
    * @Date 2022/1/4 15:07
    **/
    AppResultDto remoteAddResourceItem(RemoteResourceItemCreateReqDto remoteResourceItemCreateReqDto);

    /**
    * @Description 更新资源项
    * @Author qiuyang
    * @param remoteResourceItemUpdateReqDto
    * @return AppResultDto
    * @Date 2022/1/4 15:08
    **/
    AppResultDto remoteUpdateResourceItem(RemoteResourceItemUpdateReqDto remoteResourceItemUpdateReqDto);

    /**
    * @Description 删除资源项
    * @Author qiuyang
    * @param remoteResourceItemDeleteReqDto
    * @return AppResultDto
    * @Date 2022/1/4 15:09
    **/
    AppResultDto remoteDeleteResourceItem(RemoteResourceItemDeleteReqDto remoteResourceItemDeleteReqDto);
}
