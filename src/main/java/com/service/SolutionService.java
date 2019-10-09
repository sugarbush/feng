package com.service;

import com.pojo.Solution;

import java.util.List;

/**
 * @Author: feng
 * @Date: 2019/9/18 14:33
 */
public interface SolutionService {
    /**
     * 根据查询条件获取解决方案列表信息
     * @return
     */
    List<Solution> getSolutionAllList(Solution solution);

    /**
     * 新增解决方案
     * @param solution
     * @return
     */
    int addSolution(Solution solution);

    /**
     * 根据SID查询解决方案详细信息
     * @param sId
     * @return
     */
    Solution getSolutionInfo(int sId);
    /**
     * 通过sid修改解决方案信息
     * @param solution
     * @return
     */
    int updateSolution(Solution solution);

    /**
     * 根据Sid删除解决方案信息
     * @param sId
     */
    void delSolution(int sId);
}
