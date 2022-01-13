package com.mugu.blog.user.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(value = "com.mugu.blog.user.boot.dao")
public class UserBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserBootApplication.class);
    }
}
