package com.service;

import com.pojo.Sort;

import java.util.List;

/**
 * @Author: feng
 * @Date: 2019/9/18 14:33
 */
public interface SortService {
    /**
     * 根据查询条件获取分类列表信息
     * @return
     */
    List<Sort> getSortAllList(Sort sort);

    /**
     * 新增分类
     * @param Sort
     * @return
     */
    int addSort(Sort Sort);

    /**
     * 根据SID查询分类详细信息
     * @param tId
     * @return
     */
    Sort getSortInfo(int tId);
    /**
     * 通过sid修改分类信息
     * @param sort
     * @return
     */
    int updateSort(Sort sort);

    /**
     * 根据Sid删除分类信息
     * @param tId
     */
    void delSort(int tId);
}
