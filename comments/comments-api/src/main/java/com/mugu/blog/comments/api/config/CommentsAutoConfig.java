package com.mugu.blog.comments.api.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(value = {"com.mugu.blog.comments.api.feign"})
@ComponentScan(basePackages = "com.mugu.blog.comments.api.feign.fallback")
@Configuration
public class CommentsAutoConfig {
}
