package com.service.impl;

import com.dao.SortSublistDao;
import com.pojo.SortSublist;
import com.service.SortSublistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: feng
 * @Date: 2019/9/24 14:35
 */
@Service
public class SortSublistServiceImpl implements SortSublistService {
    @Autowired
    private SortSublistDao sortSublistDao;

    @Override
    public List<SortSublist> getSortSublistAllList(SortSublist sortSublist) {
        return sortSublistDao.getSortSublistAllList(sortSublist);
    }

    @Override
    public int addSortSublist(SortSublist sortSublist) {
        return sortSublistDao.addSortSublist(sortSublist);
    }

    @Override
    public SortSublist getSortSublistInfo(int sId) {
        return sortSublistDao.getSortSublistInfo(sId);
    }

    @Override
    public int updateSortSublist(SortSublist sortSublist) {
        return sortSublistDao.updateSortSublist(sortSublist);
    }

    @Override
    public void delSortSublist(int sId) {
        sortSublistDao.delSortSublist(sId);
    }
}
