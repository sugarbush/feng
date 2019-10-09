package com.service;

import com.pojo.User;

import java.util.List;

/**
 *  @Author: feng
 *  @Date: 2019/9/18 9:50
 *
 */
public interface UserService {
    /**
     * 登录接口
     * @param uname
     * @param upassword
     * @return
     */
    User login(String loginName, String upassword);

    /**
     * 根据查询条件获取用户列表信息
     * @return
     */
    List<User> getUserAllList(User user);

    /**
     * 增加用户信息
     * @param user
     * @return
     */
    boolean addUser(User user);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    boolean updateUser(User user);

    /**
     * 删除用户信息
     * @param user
     * @return
     */
    boolean delUser(User user);

    /**
     * 根据用户ID获取用户信息
     * @param uid
     * @return
     */
    User getUserId(int uid);
}
