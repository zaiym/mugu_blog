package com.mugu.blog.picture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mugu.blog.common.utils.AssertUtils;
import com.mugu.blog.core.utils.OauthUtils;
import com.mugu.blog.core.model.PageData;
import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.file.server.api.feign.ImageUploadFeign;
import com.mugu.blog.mybatis.config.utils.PageUtils;
import com.mugu.blog.picture.common.model.po.Picture;
import com.mugu.blog.picture.common.model.req.PictureAddReq;
import com.mugu.blog.picture.common.model.req.PictureDelReq;
import com.mugu.blog.picture.common.model.req.PictureListReq;
import com.mugu.blog.picture.common.model.vo.PictureVo;
import com.mugu.blog.picture.dao.PictureMapper;
import com.mugu.blog.picture.service.PictureService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private ImageUploadFeign imageUploadFeign;

    @GlobalTransactional(name = "addPicture")
    @SneakyThrows
    @Override
    public void add(PictureAddReq param, MultipartFile file) {

        //上传图片
        ResultMsg<String> imageRes = imageUploadFeign.upload(file);
        AssertUtils.assertTrue(ResultMsg.isSuccess(imageRes), imageRes.getCode(),imageRes.getMsg());

        Picture picture = new Picture();
        BeanUtil.copyProperties(param, picture);
        //图片的url
        picture.setUrl(imageRes.getData());
        picture.setCreateTime(new Date());
        picture.setUpdateTime(new Date());
        picture.setStatus(1);
        picture.setUserId(OauthUtils.getCurrentUser().getUserId());
        pictureMapper.insert(picture);
    }

    @Override
    public PageData<List<PictureVo>> list(PictureListReq param) {
        return PageUtils.getPage(param, req1 -> pictureMapper.pageList(param));
    }

    @Override
    public void del(PictureDelReq param) {
        Picture picture = new Picture();
        picture.setId(param.getId());
        picture.setStatus(0);
        pictureMapper.update(picture);
    }

}
