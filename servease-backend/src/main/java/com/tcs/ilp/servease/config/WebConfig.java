package com.tcs.ilp.servease.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    	registry.addInterceptor(sessionInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns(
            "/auth/login",
            "/auth/logout",

            // ✅ ALLOW SWAGGER
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",

            // ✅ OPTIONAL (sometimes needed)
            "/webjars/**"
        );
    }
}
