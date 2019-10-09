package com.service.impl;

import com.dao.DynamicDao;
import com.pojo.Dynamic;
import com.service.DynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: feng
 * @Date: 2019/9/24 14:35
 */
@Service
public class DynamicServiceImpl implements DynamicService {
    @Autowired
    private DynamicDao dynamicDao;

    @Override
    public List<Dynamic> getDynamicAllList(Dynamic dynamic) {
        return dynamicDao.getDynamicAllList(dynamic);
    }

    @Override
    public int addDynamic(Dynamic dynamic) {
        return dynamicDao.addDynamic(dynamic);
    }

    @Override
    public Dynamic getDynamicInfo(int dId) {
        return dynamicDao.getDynamicInfo(dId);
    }

    @Override
    public int updateDynamic(Dynamic dynamic) {
        return dynamicDao.updateDynamic(dynamic);
    }

    @Override
    public void delDynamic(int dId) {
        dynamicDao.delDynamic(dId);
    }
}
