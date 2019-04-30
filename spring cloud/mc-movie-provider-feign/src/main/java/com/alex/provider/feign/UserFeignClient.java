package com.alex.provider.feign;

import com.alex.provider.entity.Result;
import com.alex.provider.fallback.UserFallbackFactory;
import feign.Logger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @ClassName UserFeignClient
 * @Description todo
 * @Author AlexWang
 * @Date 2019/4/28 0028 上午 10:18
 * @Version 1.0
 **/
@FeignClient(name = "consumer-service", fallbackFactory = UserFallbackFactory.class)
public interface UserFeignClient {

    @PostMapping("/user/{id}")
    Result findById(@PathVariable long id);

    /**
     * 该Feign Client的配置类，注意：
     * 1. 该类可以独立出去；
     * 2. 该类上也可添加@Configuration声明是一个配置类；
     * 配置类上也可添加@Configuration注解，声明这是一个配置类；
     * 但此时千万别将该放置在主应用程序上下文@ComponentScan所扫描的包中，
     * 否则，该配置将会被所有Feign Client共享，无法实现细粒度配置！
     * 个人建议：像我一样，不加@Configuration注解
     */
    class UserFeignConfig {
        @Bean
        public Logger.Level logger() {
            return Logger.Level.FULL;
        }
    }


}
