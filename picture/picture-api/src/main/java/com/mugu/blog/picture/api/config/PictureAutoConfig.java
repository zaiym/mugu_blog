package com.mugu.blog.picture.api.config;

import com.mugu.blog.picture.api.feign.admin.PictureAdminFeign;
import com.mugu.blog.picture.api.feign.fallback.PictureAdminFallback;
import com.mugu.blog.picture.api.feign.fallback.PictureFrontFallback;
import com.mugu.blog.picture.api.feign.front.PictureFrontFeign;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableFeignClients(basePackageClasses = {PictureFrontFeign.class, PictureAdminFeign.class})
@ComponentScan(basePackageClasses = {PictureFrontFallback.class, PictureAdminFallback.class})
public class PictureAutoConfig {

}
