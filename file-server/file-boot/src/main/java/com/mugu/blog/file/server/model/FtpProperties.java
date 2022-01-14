package com.mugu.blog.file.server.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ftp")
public class FtpProperties {
    private String host;

    private String username;

    private String password;

    private Integer port;

    /**
     * 基础文件路径
     */
    private String basePath;

    /**
     * 文件路径
     */
    private String imagePath;

    /**
     * url前缀
     */
    private String urlPrefix;

    /**
     * 图片的前缀地址
     */
    private String imageUrlPrefix;
}
