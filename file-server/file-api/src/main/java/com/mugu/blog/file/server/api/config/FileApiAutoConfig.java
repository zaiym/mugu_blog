package com.mugu.blog.file.server.api.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages ={"com.mugu.blog.file.server.api.feign"} )
public class FileApiAutoConfig {
}
