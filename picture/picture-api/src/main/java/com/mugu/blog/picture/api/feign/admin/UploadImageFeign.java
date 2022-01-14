package com.mugu.blog.picture.api.feign.admin;

import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.picture.api.feign.config.UploadFileConfig;
import com.mugu.blog.picture.api.feign.fallback.PictureAdminFallback;
import com.mugu.blog.picture.common.model.req.PictureAddReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传图片的Feign接口，UploadFileConfig这个配置类定义了表单提交的方式
 */
@FeignClient(value = "blog-picture",contextId = "picture-boot-upload",fallback = PictureAdminFallback.class,configuration = UploadFileConfig.class)
public interface UploadImageFeign {
    /**
     * consumes 指定表单提交
     */
    @PostMapping(value = "/blog-picture/admin/picture/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResultMsg<Void> add(@SpringQueryMap PictureAddReq param, @RequestPart(value = "file") MultipartFile file);
}
