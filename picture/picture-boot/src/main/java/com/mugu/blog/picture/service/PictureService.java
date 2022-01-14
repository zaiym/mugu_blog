package com.mugu.blog.picture.service;

import com.mugu.blog.core.model.PageData;
import com.mugu.blog.picture.common.model.req.PictureAddReq;
import com.mugu.blog.picture.common.model.req.PictureDelReq;
import com.mugu.blog.picture.common.model.req.PictureListReq;
import com.mugu.blog.picture.common.model.vo.PictureVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PictureService {
    void add(PictureAddReq param, MultipartFile file);

    PageData<List<PictureVo>> list(PictureListReq param);

    void del(PictureDelReq param);
}
