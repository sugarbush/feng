package com.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.Statistics;
import com.service.StatisticsService;
import com.util.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: feng
 * @Date: 2019/9/18 14:32
 */
@Controller
@RequestMapping("/")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;
 
    /**
     * 查询所有各表总数信息列表
     * @param statistics
     * @return
     */
    @RequestMapping("/queryStatisticsAllList")
    @ResponseBody
    public PageInfo queryStatisticsAllList(Statistics statistics,HttpServletRequest request){
        List<Statistics> list =  statisticsService.getStatisticsAllList(statistics);
        PageInfo pageInfo = new PageInfo(list);  //将sql查询结果list传递过去
        return pageInfo;
    } 
}
