package com.mugu.blog.comments.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mugu.blog.comments.common.model.po.Comment;
import com.mugu.blog.comments.common.model.req.MessageAddReq;
import com.mugu.blog.comments.common.model.req.MessageListReq;
import com.mugu.blog.comments.common.model.vo.CommentVo;
import com.mugu.blog.comments.common.model.vo.MessageVo;
import com.mugu.blog.comments.dao.CommentMapper;
import com.mugu.blog.comments.service.MessageService;
import com.mugu.blog.common.utils.AssertUtils;
import com.mugu.blog.core.model.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private CommentMapper mapper;

    @Override
    public void add(MessageAddReq param) {
        Comment comment = new Comment();
        if (Objects.nonNull(param.getPid())){
            Comment comm = mapper.load(param.getPid());
            //默认只允许二级评论，因此回复的pid必须是null
            AssertUtils.assertTrue(Objects.nonNull(comm)&&Objects.isNull(comm.getPid()), ResultCode.ARTICLE_NOT_EXIST);
            comment.setReplyName(comm.getNickName());
        }
        BeanUtil.copyProperties(param,comment);
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        comment.setStatus(1);
        mapper.insert(comment);
    }

    @Override
    public List<MessageVo> list(MessageListReq param) {
        //获取所有评论
        List<Comment> comments = mapper.pageMsgList(param);
        //TODO 目前只展示二级评论，后面平铺展示，不再盖楼
        //获取所有的顶层评论
        List<Comment> pComments = comments.stream().filter(p -> Objects.isNull(p.getPid())).collect(Collectors.toList());
        List<MessageVo> pList=new ArrayList<>();
        for (Comment comment : pComments) {
            MessageVo vo = new MessageVo();
            BeanUtil.copyProperties(comment,vo);
            //获取子评论
            List<MessageVo> cList = comments.stream().filter(p -> Objects.nonNull(p.getPid())&&p.getPid().equals(comment.getId()))
                    .map(p -> {
                        MessageVo child = new MessageVo();
                        BeanUtil.copyProperties(p, child);
                        return child;
                    })
                    .collect(Collectors.toList());
            vo.setChild(cList);
            pList.add(vo);
        }
        return pList;
    }

    @Override
    public Long total() {
        return mapper.totalMsg();
    }
}
