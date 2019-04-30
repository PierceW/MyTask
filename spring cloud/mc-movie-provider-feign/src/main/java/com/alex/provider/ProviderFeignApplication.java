package com.alex.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName ProviderFeignApplication
 * @Description todo
 * @Author AlexWang
 * @Date 2019/4/28 0028 上午 10:15
 * @Version 1.0
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
public class ProviderFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderFeignApplication.class, args);
    }
}
