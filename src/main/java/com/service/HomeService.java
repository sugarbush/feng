package com.service;

import com.pojo.Home;

import java.util.List;

/**
 * @Author: feng
 * @Date: 2019/9/18 14:33
 */
public interface HomeService {
    /**
     * 根据查询条件获取首页信息列表信息
     * @return
     */
    List<Home> getHomeAllList(Home home);

    /**
     * 新增首页信息
     * @param home
     * @return
     */
    int addHome(Home home);

    /**
     * 根据SID查询首页信息详细信息
     * @param hId
     * @return
     */
    Home getHomeInfo(int hId);
    /**
     * 通过sid修改首页信息信息
     * @param home
     * @return
     */
    int updateHome(Home home);

    /**
     * 根据hId删除首页信息信息
     * @param hId
     */
    void delHome(int hId);
}
