package com.mugu.blog.article;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@MapperScan(value = "com.mugu.blog.article.dao")
@EnableElasticsearchRepositories(basePackages = {"com.mugu.blog.article.es.dao"})
public class BlogArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogArticleApplication.class,args);
    }
}
