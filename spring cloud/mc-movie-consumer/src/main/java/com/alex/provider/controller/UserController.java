package com.alex.provider.controller;

import com.alex.provider.entity.Result;
import com.alex.provider.entity.User;
import com.alex.provider.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Description todo
 * @Author AlexWang
 * @Date 2019/4/23 0023 下午 3:32
 * @Version 1.0
 **/
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/")
    public String home() {
        return "This is a test result";
    }

    @PostMapping("/user/all")
    public Result findAll(@PathVariable Long id) {
        return Result.success(userMapper.getAll());
    }

    @PostMapping("/user/{id}")
    public Result findById(@PathVariable Long id) {
        User user = userMapper.getOne(Long.parseLong(id + ""));
        return Result.success(user);
    }
}
