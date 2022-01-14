package com.mugu.blog.comments.api.feign;

import com.mugu.blog.comments.api.feign.fallback.CommentFeignFallback;
import com.mugu.blog.comments.common.model.req.MessageAddReq;
import com.mugu.blog.comments.common.model.req.MessageListReq;
import com.mugu.blog.comments.common.model.vo.MessageVo;
import com.mugu.blog.core.model.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "blog-comments",contextId = "message-boot",fallback = CommentFeignFallback.class)
public interface MessageFeign {

    @PostMapping(value = "/blog-comments/message/add")
    ResultMsg<Void> add(@RequestBody  MessageAddReq param);

    @PostMapping(value = "/blog-comments/message//list")
    ResultMsg<List<MessageVo>> list(@RequestBody  MessageListReq param);

    @PostMapping(value = "/blog-comments/message/total")
    ResultMsg<Long> total();
}
