package com.mugu.blog.comments.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mugu.blog.comments.common.model.po.Comment;
import com.mugu.blog.comments.common.model.req.CommentAddReq;
import com.mugu.blog.comments.common.model.req.CommentListReq;
import com.mugu.blog.comments.common.model.vo.CommentVo;
import com.mugu.blog.comments.common.model.vo.TotalVo;
import com.mugu.blog.comments.dao.CommentMapper;
import com.mugu.blog.comments.service.CommentService;
import com.mugu.blog.common.utils.AssertUtils;
import com.mugu.blog.core.model.ResultCode;
import com.mugu.blog.core.utils.SnowflakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper mapper;

    @Transactional
    @Override
    public void add(CommentAddReq param) {

        //TODO 针对回复的内容发送邮件通知

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
        comment.setCommentId(String.valueOf(SnowflakeUtil.nextId()));
        mapper.insert(comment);
    }

    /**
     * 考虑到博客评论比较少，这里不分页了
     */
    @Override
    public List<CommentVo> list(CommentListReq param) {
        //获取所有评论
        List<Comment> comments = mapper.pageList(param);
        //TODO 目前只展示二级评论，后面平铺展示，不再盖楼
        //获取所有的顶层评论
        List<Comment> pComments = comments.stream().filter(p -> Objects.isNull(p.getPid())).collect(Collectors.toList());
        List<CommentVo> pList=new ArrayList<>();
        for (Comment comment : pComments) {
            CommentVo vo = new CommentVo();
            BeanUtil.copyProperties(comment,vo);
            //获取子评论
            List<CommentVo> cList = comments.stream().filter(p -> Objects.nonNull(p.getPid())&&p.getPid().equals(comment.getCommentId()))
                    .map(p -> {
                        CommentVo child = new CommentVo();
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
    public Long total(CommentListReq param) {
        return mapper.totalComment(param);
    }

    @Override
    public List<TotalVo> listTotal(List<CommentListReq> params) {
        List<TotalVo> list=new ArrayList<>();
        for (CommentListReq param : params) {
            //无事务，直接调用
            Long total = this.total(param);
            TotalVo totalVo = new TotalVo();
            totalVo.setTotal(total);
            totalVo.setArticleId(param.getArticleId());
            list.add(totalVo);
        }
        return list;
    }
}
