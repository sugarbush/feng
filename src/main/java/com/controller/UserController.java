package com.controller;

import com.pojo.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: feng
 * @Date: 2019/9/18 10:28
 */
@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userServivce;

    //登录
    @RequestMapping("/checkLogin")
    @ResponseBody
    public Map<String, Object>  checkLogin(User user){
        Map<String, Object> model = new HashMap<>();
        if(user.getLoginName() == null || user.getuPassword() == null){
            model.put("errorCode","100001");
            model.put("errorMsg","用户名或密码不能为空！");
            return model;
        }
        //调用service方法
        user = userServivce.login(user.getLoginName(), user.getuPassword());
        //若有user则添加到model里并且跳转到成功页面
        if(user != null){
            model.put("user",user);
            model.put("errorCode","000000");
            model.put("errorMsg","登录成功!");
            return model;
        }else{
            model.put("errorCode","100002");
            model.put("errorMsg","用户名或密码错误，请确认!");
            return model;
        }
    }
}
