package com.mugu.blog.friendlinks;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.mugu.blog.friendlinks.dao")
public class FriendLinksApplication {
    public static void main(String[] args) {
        SpringApplication.run(FriendLinksApplication.class,args);
    }
}
