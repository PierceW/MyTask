package com.alex.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ClassName eurekaServerApplication
 * @Description todo
 * @Author AlexWang
 * @Date 2019/4/25 0025 下午 3:33
 * @Version 1.0
 **/
@SpringBootApplication
@EnableEurekaServer
public class eurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(eurekaServerApplication.class, args);
        System.out.println("Eureka server begins starting...");
    }
}
