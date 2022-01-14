package com.mugu.blog.comments.service;

import com.mugu.blog.comments.common.model.req.MessageAddReq;
import com.mugu.blog.comments.common.model.req.MessageListReq;
import com.mugu.blog.comments.common.model.vo.MessageVo;

import java.util.List;

public interface MessageService {

    void add(MessageAddReq req);

    List<MessageVo> list(MessageListReq param);

    Long total();
}
