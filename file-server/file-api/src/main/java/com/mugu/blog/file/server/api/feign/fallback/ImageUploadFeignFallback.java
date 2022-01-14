package com.mugu.blog.file.server.api.feign.fallback;

import com.mugu.blog.core.model.ResultCode;
import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.file.server.api.feign.ImageUploadFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class ImageUploadFeignFallback implements ImageUploadFeign {
    @Override
    public ResultMsg<String> upload(MultipartFile file) {
        log.error("file Server Fallback..........");
        return ResultMsg.resultFail(ResultCode.SERVER_FALLBACK.getCode(),ResultCode.SERVER_FALLBACK.getMsg());
    }
}
