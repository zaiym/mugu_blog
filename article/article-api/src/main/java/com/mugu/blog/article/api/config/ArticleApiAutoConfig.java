package com.mugu.blog.article.api.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(value = {"com.mugu.blog.article.api.feign"})
@Configuration
public class ArticleApiAutoConfig {
}
