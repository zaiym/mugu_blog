package com.mugu.blog.comments.api.feign;

import com.mugu.blog.comments.api.feign.fallback.CommentFeignFallback;
import com.mugu.blog.comments.common.model.req.CommentAddReq;
import com.mugu.blog.comments.common.model.req.CommentListReq;
import com.mugu.blog.comments.common.model.vo.CommentVo;
import com.mugu.blog.comments.common.model.vo.TotalVo;
import com.mugu.blog.core.model.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "blog-comments",contextId = "comments-boot",fallback = CommentFeignFallback.class)
public interface CommentFeign {

    @PostMapping(value = "/blog-comments/comment/add")
    ResultMsg<Void> add(@RequestBody CommentAddReq param);

    @PostMapping(value = "/blog-comments/comment/list")
    ResultMsg<List<CommentVo>> list(@RequestBody CommentListReq param);


    @PostMapping(value = "/blog-comments/comment/total")
    ResultMsg<Long> total(@RequestBody CommentListReq param);

    @PostMapping(value = "/blog-comments/comment/list/total")
    ResultMsg<List<TotalVo>> listTotal(@RequestBody List<CommentListReq> param);
}
