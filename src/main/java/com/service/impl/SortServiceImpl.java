package com.service.impl;

import com.dao.SortDao;
import com.pojo.Sort;
import com.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: feng
 * @Date: 2019/9/18 14:35
 */
@Service
public class SortServiceImpl implements SortService {
    @Autowired
    private SortDao SortDao;

    @Override
    public List<Sort> getSortAllList(Sort sort) {
        return SortDao.getSortAllList(sort);
    }

    @Override
    public int addSort(Sort sort) {
        return SortDao.addSort(sort);
    }

    @Override
    public Sort getSortInfo(int tId) {
        return SortDao.getSortInfo(tId);
    }

    @Override
    public int updateSort(Sort sort) {
        return SortDao.updateSort(sort);
    }

    @Override
    public void delSort(int tId) {
        SortDao.delSort(tId);
    }
}
