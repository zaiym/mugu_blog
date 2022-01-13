package com.mugu.blog.feign;

import com.mugu.blog.feign.intercept.FeignRequestInterceptor;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableDiscoveryClient
public class FeignAutoConfig {
}
