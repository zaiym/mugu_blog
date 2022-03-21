package com.mugu.blog.friendlinks.service.impl;

import com.mugu.blog.common.utils.AssertUtils;
import com.mugu.blog.core.model.BaseParam;
import com.mugu.blog.core.model.PageData;
import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.file.server.api.feign.ImageUploadFeign;
import com.mugu.blog.friend.links.common.model.po.FriendLinks;
import com.mugu.blog.friend.links.common.model.req.FriendLinksAddReq;
import com.mugu.blog.friend.links.common.model.req.FriendLinksDelReq;
import com.mugu.blog.friend.links.common.model.vo.FriendLinksVo;
import com.mugu.blog.friendlinks.dao.FriendLinksMapper;
import com.mugu.blog.friendlinks.service.FriendLinksService;
import com.mugu.blog.mybatis.config.utils.PageUtils;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class FriendLinksServiceImpl implements FriendLinksService {

    @Autowired
    private FriendLinksMapper mapper;

    @Autowired
    private ImageUploadFeign imageUploadFeign;

    @Autowired
    private ApplicationContext applicationContext;


    @GlobalTransactional(name = "friendLinksAdd")
    @SneakyThrows
    @Override
    public void add(FriendLinksAddReq param, MultipartFile file) {
        //上传图片
        ResultMsg<String> imageRes = imageUploadFeign.upload(file);
        //判断是否上传成功，断言，不成功则抛出异常，也为了防止服务降级导致的事务不回滚
        AssertUtils.assertTrue(ResultMsg.isSuccess(imageRes), imageRes.getCode(),imageRes.getMsg());
        FriendLinks links = new FriendLinks();
        links.setCreateTime(new Date());
        links.setUpdateTime(new Date());
        links.setBlogName(param.getBlogName());
        links.setImageUrl(imageRes.getData());
        links.setBlogUrl(param.getBlogUrl());
        links.setStatus(1);
        FriendLinksService service = applicationContext.getBean(FriendLinksService.class);
        int i = service.add(links);
        AssertUtils.assertTrue(i>0);
    }

    @Transactional
    @Override
    public int add(FriendLinks param) {
        return mapper.insert(param);
    }

    @Override
    public PageData<List<FriendLinksVo>> list(BaseParam param) {
        return PageUtils.getPage(param, req1 -> mapper.pageList(param));
    }

    @Override
    public void del(FriendLinksDelReq param) {
        FriendLinks links = new FriendLinks();
        links.setId(param.getId());
        links.setStatus(0);
        mapper.update(links);
    }
}
