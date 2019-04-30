package com.alex.provider.controller;

import com.alex.provider.entity.Result;
import com.alex.provider.entity.User;
import com.alex.provider.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Description todo
 * @Author AlexWang
 * @Date 2019/4/28 0028 上午 10:18
 * @Version 1.0
 **/
@RestController
public class UserController {

    @Autowired
    private UserFeignClient userFeignClient;

    @PostMapping("/user/{id}")
    public Result findById(@PathVariable long id) {
        Result result = userFeignClient.findById(id);
        return result;
    }

}
