package com.service;

import com.pojo.Dynamic;

import java.util.List;

/**
 * @Author: feng
 * @Date: 2019/9/24 14:33
 */
public interface DynamicService {
    /**
     * 根据查询条件获取公司动态列表信息
     * @return
     */
    List<Dynamic> getDynamicAllList(Dynamic dynamic);

    /**
     * 新增公司动态
     * @param dynamic
     * @return
     */
    int addDynamic(Dynamic dynamic);

    /**
     * 根据SID查询公司动态详细信息
     * @param dId
     * @return
     */
    Dynamic getDynamicInfo(int dId);
    /**
     * 通过sid修改公司动态信息
     * @param dynamic
     * @return
     */
    int updateDynamic(Dynamic dynamic);

    /**
     * 根据Sid删除公司动态信息
     * @param dId
     */
    void delDynamic(int dId);
}
