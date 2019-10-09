package com.service;

import com.pojo.Statistics;

import java.util.List;

/**
 * @Author: feng
 * @Date: 2019/9/18 14:33
 */
public interface StatisticsService {
    /**
     * 根据查询条件获取各表总数信息s
     * @return
     */
    List<Statistics> getStatisticsAllList(Statistics tatistics);
 
}
