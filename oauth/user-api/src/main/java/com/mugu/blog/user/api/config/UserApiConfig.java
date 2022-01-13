package com.mugu.blog.user.api.config;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"com.mugu.blog.user.api.feign"})
public class UserApiConfig {
}
