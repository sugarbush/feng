package com.service.impl;

import com.dao.PaperDao;
import com.dao.UserDao;
import com.pojo.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: feng
 * @Date: 2019/9/18 9:57
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User login(String loginName, String upassword) {
        User user = userDao.login(loginName,upassword);
        return user;
    }

    @Override
    public List<User> getUserAllList(User user) {
        return userDao.getUserAllList(user);
    }

    @Override
    public boolean addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public boolean delUser(User user) {
        return userDao.delUser(user);
    }

    @Override
    public User getUserId(int uid) {
        return userDao.getUserId(uid);
    }
}
