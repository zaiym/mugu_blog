package com.mugu.blog.picture;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.mugu.blog.picture.dao")
public class PictureApplication {
    public static void main(String[] args) {
        SpringApplication.run(PictureApplication.class,args);
    }
}
