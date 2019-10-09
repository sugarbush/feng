package com.service.impl;

import com.dao.HomeDao;
import com.pojo.Home;
import com.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: feng
 * @Date: 2019/9/18 14:35
 */
@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private HomeDao homeDao;

    @Override
    public List<Home> getHomeAllList(Home home) {
        return homeDao.getHomeAllList(home);
    }

    @Override
    public int addHome(Home home) {
        return homeDao.addHome(home);
    }

    @Override
    public Home getHomeInfo(int hId) {
        return homeDao.getHomeInfo(hId);
    }

    @Override
    public int updateHome(Home home) {
        return homeDao.updateHome(home);
    }

    @Override
    public void delHome(int hId) {
        homeDao.delHome(hId);
    }
}
