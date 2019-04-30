package com.alex.provider.fallback;

import com.alex.provider.entity.Result;
import com.alex.provider.feign.UserFeignClient;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName UserFallbackFactory
 * @Description todo
 * @Author AlexWang
 * @Date 2019/4/29 0029 上午 10:04
 * @Version 1.0
 **/
@Component
public class UserFallbackFactory implements FallbackFactory<UserFeignClient> {

    private static final Logger logger = LoggerFactory.getLogger(UserFallbackFactory.class);

    @Override
    public UserFeignClient create(Throwable throwable) {
        return new UserFeignClient() {
            @Override
            public Result findById(long id) {
                logger.error("exec findById back ", throwable);
                return Result.error(throwable);
            }
        };
    }
}
