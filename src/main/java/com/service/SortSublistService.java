package com.service;

import com.pojo.SortSublist;

import java.util.List;

/**
 * @Author: feng
 * @Date: 2019/9/24 14:33
 */
public interface SortSublistService {
    /**
     * 根据查询条件获取关于我们子列表列表信息
     * @return
     */
    List<SortSublist> getSortSublistAllList(SortSublist sortSublist);

    /**
     * 新增关于我们子列表
     * @param sortSublist
     * @return
     */
    int addSortSublist(SortSublist sortSublist);

    /**
     * 根据SID查询关于我们子列表详细信息
     * @param sId
     * @return
     */
    SortSublist getSortSublistInfo(int sId);
    /**
     * 通过sid修改关于我们子列表信息
     * @param sortSublist
     * @return
     */
    int updateSortSublist(SortSublist sortSublist);

    /**
     * 根据Sid删除关于我们子列表信息
     * @param sId
     */
    void delSortSublist(int sId);
}
