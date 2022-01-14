package com.mugu.blog.article.config;

import com.mugu.blog.article.intercept.ViewIntercept;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ViewIntercept viewIntercept;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //Swagger需要排除的uri
        final String[] swaggerExclude = {"/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/", "/csrf"};
        registry.addInterceptor(viewIntercept).excludePathPatterns(ArrayUtils.addAll(swaggerExclude));
    }
}
