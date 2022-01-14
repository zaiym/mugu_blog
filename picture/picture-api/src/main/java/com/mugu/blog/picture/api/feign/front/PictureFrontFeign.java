package com.mugu.blog.picture.api.feign.front;

import com.mugu.blog.core.model.PageData;
import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.picture.api.feign.fallback.PictureFrontFallback;
import com.mugu.blog.picture.common.model.req.PictureListReq;
import com.mugu.blog.picture.common.model.vo.PictureVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "blog-picture",contextId = "picture-boot-front",fallback = PictureFrontFallback.class)
public interface PictureFrontFeign {
    @PostMapping("/blog-picture/front/picture/list")
    ResultMsg<PageData<List<PictureVo>>> list(@RequestBody PictureListReq param);
}
