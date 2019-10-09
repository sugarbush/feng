package com.service.impl;

import com.dao.SolutionDao;
import com.dao.UserDao;
import com.pojo.Solution;
import com.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: feng
 * @Date: 2019/9/18 14:35
 */
@Service
public class SolutionServiceImpl implements SolutionService {
    @Autowired
    private SolutionDao solutionDao;

    @Override
    public List<Solution> getSolutionAllList(Solution solution) {
        return solutionDao.getSolutionAllList(solution);
    }

    @Override
    public int addSolution(Solution solution) {
        return solutionDao.addSolution(solution);
    }

    @Override
    public Solution getSolutionInfo(int sId) {
        return solutionDao.getSolutionInfo(sId);
    }

    @Override
    public int updateSolution(Solution solution) {
        return solutionDao.updateSolution(solution);
    }

    @Override
    public void delSolution(int sId) {
        solutionDao.delSolution(sId);
    }
}
