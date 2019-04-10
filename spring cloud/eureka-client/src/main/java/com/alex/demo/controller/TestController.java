package com.alex.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author Alex
 * @Date 2019/4/9 9:43
 * @Version 1.0
 **/
@RestController
public class TestController {

    @Value("${server.port}")
    public String port;

    @RequestMapping("/")
    public String home() {
        return "Hello world from port : " + port;
    }
}
