package com.fastyw.bee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置相关的访问映射
 */
@Configuration
public class InterseptorMvcConfig implements WebMvcConfigurer {

    /**
     *  将自定义的拦截器放入spring context 容器中
     *  因为自定义拦截器中注入了stringRedisTemplate
     *  这样可以保证注册拦截器时，stringRedisTemplate也已经存在spring容器中，
     *  避免在拦截器用到stringRedisTemplate时stringRedisTemplate为null
     * @return
     */
    @Bean
    public RequestInterceptor getRequestInterceptor(){
        return new RequestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getRequestInterceptor()).addPathPatterns("/**")
        .excludePathPatterns("/common/**", "/shop/**","/config/**", "/user/wxapp/register/complex",
                "/user/wxapp/login","/banner/**", "/error");
    }
}

