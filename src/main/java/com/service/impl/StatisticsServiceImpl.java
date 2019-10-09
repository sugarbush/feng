package com.service.impl;

import com.dao.StatisticsDao;
import com.pojo.Statistics;
import com.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: feng
 * @Date: 2019/9/18 14:35
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsDao statisticsDao;

    @Override
    public List<Statistics> getStatisticsAllList(Statistics statistics) {
        return statisticsDao.getStatisticsAllList(statistics);
    }
 
}
