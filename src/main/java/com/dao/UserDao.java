package com.dao;

import com.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: feng
 * @Date: 2019/9/18 9:59
 */
public interface UserDao {
    /**
     * 登录接口
     * @param uname
     * @param upassword
     * @return
     */
    User login(@Param("loginName")String loginName, @Param("upassword")String uPassword);

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
