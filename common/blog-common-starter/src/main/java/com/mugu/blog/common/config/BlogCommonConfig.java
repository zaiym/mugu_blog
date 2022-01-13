package com.mugu.blog.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.mugu.blog.common.filter"})
public class BlogCommonConfig {
}
