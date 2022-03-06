package com.mugu.blog.comments.service;

import com.mugu.blog.comments.common.model.req.CommentAddReq;
import com.mugu.blog.comments.common.model.req.CommentListReq;
import com.mugu.blog.comments.common.model.vo.CommentVo;
import com.mugu.blog.comments.common.model.vo.TotalVo;

import java.util.List;

public interface CommentService {
    void add(CommentAddReq param);

    List<CommentVo> list(CommentListReq param);

    Long total(CommentListReq param);

    List<TotalVo> listTotal(List<CommentListReq> params);
}
