package com.mugu.blog.picture.api.feign.admin;

import com.mugu.blog.core.model.PageData;
import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.picture.api.feign.config.UploadFileConfig;
import com.mugu.blog.picture.api.feign.fallback.PictureAdminFallback;
import com.mugu.blog.picture.api.feign.fallback.PictureFrontFallback;
import com.mugu.blog.picture.common.model.req.PictureAddReq;
import com.mugu.blog.picture.common.model.req.PictureDelReq;
import com.mugu.blog.picture.common.model.req.PictureListReq;
import com.mugu.blog.picture.common.model.vo.PictureVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@FeignClient(value = "blog-picture",contextId = "picture-boot-admin",fallback = PictureAdminFallback.class)
public interface PictureAdminFeign {
    @PostMapping("/blog-picture/admin/picture/list")
    ResultMsg<PageData<List<PictureVo>>> list(@RequestBody PictureListReq param);

    @PostMapping("/blog-picture/admin/picture/del")
    ResultMsg<Void> del(@Valid @RequestBody PictureDelReq param);
}
