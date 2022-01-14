package com.mugu.blog.comments.controller;

import com.mugu.blog.comments.aspect.AvoidRepeatableCommit;
import com.mugu.blog.comments.common.model.req.MessageAddReq;
import com.mugu.blog.comments.common.model.req.MessageListReq;
import com.mugu.blog.comments.common.model.vo.MessageVo;
import com.mugu.blog.comments.service.MessageService;
import com.mugu.blog.core.model.ResultMsg;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@ApiOperation(value = "留言板接口")
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @AvoidRepeatableCommit
    @ApiOperation(value = "添加留言")
    @PostMapping(value = "/add")
    public ResultMsg<Void> add(@RequestBody @Valid MessageAddReq param){
        messageService.add(param);
        return ResultMsg.resultSuccess();
    }

    @ApiOperation(value = "获取留言列表")
    @PostMapping(value = "/list")
    public ResultMsg<List<MessageVo>> list(@RequestBody @Valid MessageListReq param){
        return ResultMsg.resultSuccess(messageService.list(param));
    }

    @ApiOperation(value = "获取留言总数")
    @PostMapping(value = "/total")
    public ResultMsg<Long> total(){
        return ResultMsg.resultSuccess(messageService.total());
    }
}
