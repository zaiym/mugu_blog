package com.mugu.blog.file.server;

import com.mugu.blog.file.server.dao.FileMapper;
import com.mugu.blog.file.server.model.FtpProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = FtpProperties.class)
@MapperScan(basePackageClasses = FileMapper.class)
public class FileBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileBootApplication.class,args);
    }
}
