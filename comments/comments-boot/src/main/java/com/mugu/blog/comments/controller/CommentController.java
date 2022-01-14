package com.mugu.blog.comments.controller;

import com.mugu.blog.comments.aspect.AvoidRepeatableCommit;
import com.mugu.blog.comments.common.model.req.CommentAddReq;
import com.mugu.blog.comments.common.model.req.CommentListReq;
import com.mugu.blog.comments.common.model.vo.CommentVo;
import com.mugu.blog.comments.service.CommentService;
import com.mugu.blog.core.model.ResultMsg;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@ApiOperation(value = "文章留言接口")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @AvoidRepeatableCommit
    @ApiOperation(value = "添加评论")
    @PostMapping(value = "/add")
    public ResultMsg<Void> add(@RequestBody @Valid CommentAddReq param){
        commentService.add(param);
        return ResultMsg.resultSuccess();
    }


    @ApiOperation(value = "获取评论列表")
    @PostMapping(value = "/list")
    public ResultMsg<List<CommentVo>> list(@RequestBody @Valid CommentListReq param){
        return ResultMsg.resultSuccess(commentService.list(param));
    }

    @ApiOperation(value = "获取文章的评论总数")
    @PostMapping(value = "/total")
    public ResultMsg<Long> total(@RequestBody @Valid CommentListReq param){
        return ResultMsg.resultSuccess(commentService.total(param));
    }
}
