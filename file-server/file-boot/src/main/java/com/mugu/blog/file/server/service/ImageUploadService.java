package com.mugu.blog.file.server.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {

    /**
     * 上传图片
     * @param file 文件
     * @return 图片的url
     */
    String upload(MultipartFile file);

    /**
     * 上传并且压缩
     * @param file 文件
     * @param rate 压缩的比例
     * @return 图片的url
     */
    String upload(MultipartFile file,double rate);

    /**
     * 上传并且裁剪（等比例裁剪）
     * @param file 文件
     * @param height 高度
     * @param width 长度
     * @return 图片的url
     */
    String upload(MultipartFile file,Integer height,Integer width);


    /**
     * 上传并且裁剪（等比例裁剪）
     * @param file 文件
     * @param rate 压缩的比例
     * @param height 高度
     * @param width 长度
     * @return 图片的url
     */
    String upload(MultipartFile file,double rate,Integer height,Integer width);
}
