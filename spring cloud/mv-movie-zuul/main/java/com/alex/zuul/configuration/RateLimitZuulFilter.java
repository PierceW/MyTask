package com.alex.zuul.configuration;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName RateLimitZuulFilter
 * @Description 限流
 * @Author AlexWang
 * @Date 2019/4/30 0030 13:44
 * @Version 1.0
 **/
@Component
public class RateLimitZuulFilter extends ZuulFilter {
    /**
     * 在高并发的应用中，限流往往是一个绕不开的话题。本文详细探讨在Spring Cloud中如何实现限流。
     *
     * 在Zuul 上实现限流是个不错的选择，
     * 只需要编写一个过滤器就可以了，
     * 关键在于如何实现限流的算法。
     * 常见的限流算法有漏桶算法以及令牌桶算法。
     * 这个可参考 https://www.cnblogs.com/LBSer/p/4083131.html ，
     * 写得通俗易懂，你值得拥有，我就不拽文了。
     *
     * Google Guava 为我们提供了限流工具类RateLimiter ，于是乎，我们可以撸代码了。
     *
     * 在过滤器中，
     * 我们使用Guava RateLimiter 实现限流，
     * 如果已经达到最大流量，就抛异常。
     */

    private final RateLimiter rateLimiter = RateLimiter.create(1000.0);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public boolean shouldFilter() {
        // 这里可以考虑弄个限流开启的开关，开启限流返回true，关闭限流返回false，你懂的。
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        try {
            RequestContext currentContext = RequestContext.getCurrentContext();
            HttpServletResponse response = currentContext.getResponse();
            if (!rateLimiter.tryAcquire()) {
                HttpStatus httpStatus = HttpStatus.TOO_MANY_REQUESTS;
                response.setContentType(MediaType.TEXT_PLAIN_VALUE);
                response.setStatus(httpStatus.value());

                currentContext.setSendZuulResponse(false);

                throw new ZuulException(httpStatus.getReasonPhrase(), httpStatus.value(), httpStatus.getReasonPhrase());
            }
        } catch (Exception e) {
            ReflectionUtils.rethrowRuntimeException(e);
        }
        return null;
    }
}
