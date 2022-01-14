package com.mugu.blog.comments;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.mugu.blog.comments.dao")
public class BlogCommentsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogCommentsApplication.class,args);
    }
}
