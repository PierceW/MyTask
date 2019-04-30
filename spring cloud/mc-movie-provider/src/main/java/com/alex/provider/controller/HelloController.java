package com.alex.provider.controller;

import com.alex.provider.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HelloController
 * @Description todo
 * @Author AlexWang
 * @Date 2019/4/26 0026 上午 9:10
 * @Version 1.0
 **/
@RestController
public class HelloController {
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/user/{id}")
    public ResponseEntity<User> hello(@PathVariable int id) {
        Map<String, Integer> map = new HashMap<>();
        map.put("id", id);
        User user = new User();
        user.setId(Long.parseLong(id+""));
        ResponseEntity<User> userResponseEntity = restTemplate.postForEntity("http://consumer-service/user/{id}", map, User.class, id);
        return userResponseEntity;
    }
}
