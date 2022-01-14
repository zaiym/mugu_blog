package com.mugu.blog.file.server.api.feign;

import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.file.server.api.feign.config.UploadFileConfig;
import com.mugu.blog.file.server.api.feign.fallback.ImageUploadFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "blog-file-server",contextId = "blog-file-server"
        ,fallback = ImageUploadFeignFallback.class
        ,configuration = UploadFileConfig.class)
public interface ImageUploadFeign {
    @PostMapping(value = "/blog-file-server/image/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResultMsg<String> upload(@RequestPart(value = "file") MultipartFile file);
}
