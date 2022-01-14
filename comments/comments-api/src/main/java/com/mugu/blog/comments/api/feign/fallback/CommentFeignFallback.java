package com.mugu.blog.comments.api.feign.fallback;

import com.mugu.blog.comments.api.feign.CommentFeign;
import com.mugu.blog.comments.api.feign.MessageFeign;
import com.mugu.blog.comments.common.model.req.CommentAddReq;
import com.mugu.blog.comments.common.model.req.CommentListReq;
import com.mugu.blog.comments.common.model.req.MessageAddReq;
import com.mugu.blog.comments.common.model.req.MessageListReq;
import com.mugu.blog.comments.common.model.vo.CommentVo;
import com.mugu.blog.comments.common.model.vo.MessageVo;
import com.mugu.blog.core.model.ResultCode;
import com.mugu.blog.core.model.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CommentFeignFallback implements CommentFeign, MessageFeign {

    @Override
    public ResultMsg<Void> add(CommentAddReq param) {
        log.error("Comment Server Fallback..........");
        return ResultMsg.resultFail(ResultCode.SERVER_FALLBACK.getCode(),ResultCode.SERVER_FALLBACK.getMsg());
    }

    @Override
    public ResultMsg<List<CommentVo>> list(CommentListReq param) {
        log.error("Comment Server Fallback..........");
        return ResultMsg.resultFail(ResultCode.SERVER_FALLBACK.getCode(),ResultCode.SERVER_FALLBACK.getMsg());
    }

    @Override
    public ResultMsg<Long> total(CommentListReq param) {
        log.error("Comment Server Fallback..........");
        return ResultMsg.resultFail(ResultCode.SERVER_FALLBACK.getCode(),ResultCode.SERVER_FALLBACK.getMsg());
    }

    @Override
    public ResultMsg<Void> add(MessageAddReq param) {
        log.error("Comment Server Fallback..........");
        return ResultMsg.resultFail(ResultCode.SERVER_FALLBACK.getCode(),ResultCode.SERVER_FALLBACK.getMsg());
    }

    @Override
    public ResultMsg<List<MessageVo>> list(MessageListReq param) {
        log.error("Comment Server Fallback..........");
        return ResultMsg.resultFail(ResultCode.SERVER_FALLBACK.getCode(),ResultCode.SERVER_FALLBACK.getMsg());
    }

    @Override
    public ResultMsg<Long> total() {
        log.error("Comment Server Fallback..........");
        return ResultMsg.resultFail(ResultCode.SERVER_FALLBACK.getCode(),ResultCode.SERVER_FALLBACK.getMsg());
    }
}
