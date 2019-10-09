package com.dao;

import com.pojo.Sort;
import com.pojo.Statistics;

import java.util.List;

/**
 * @Author: feng
 * @Date: 2019/9/18 14:36
 */
public interface StatisticsDao {
    /**
     * 根据查询条件获取各表总数信息
     * @return
     */
    List<Statistics> getStatisticsAllList(Statistics statistics);

}
