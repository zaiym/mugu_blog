package com.mugu.blog.article.service.impl;

import cn.hutool.core.date.DateUtil;
import com.mugu.blog.article.common.model.po.Article;
import com.mugu.blog.article.common.model.vo.FooterTotalVo;
import com.mugu.blog.article.service.ArticleService;
import com.mugu.blog.article.service.FooterService;
import com.mugu.blog.comments.api.feign.CommentFeign;
import com.mugu.blog.comments.api.feign.MessageFeign;
import com.mugu.blog.comments.common.model.req.CommentListReq;
import com.mugu.blog.core.constant.KeyConstant;
import com.mugu.blog.core.model.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class FooterServiceImpl implements FooterService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CommentFeign commentFeign;

    @Autowired
    private MessageFeign messageFeign;

    @Autowired
    private ArticleService articleService;


    @Override
    public FooterTotalVo total() {

        FooterTotalVo vo = new FooterTotalVo();

        //获取当日访问量
        Long todayVisitNum = stringRedisTemplate.opsForHyperLogLog().size(KeyConstant.UV+ DateUtil.today());
        vo.setTodayVisitNum(todayVisitNum);

        Long visitNum = stringRedisTemplate.opsForHyperLogLog().size(KeyConstant.UV_TOTAL);
        vo.setVisitNum(visitNum);

        //获取评论总数
        CommentListReq commentListReq = new CommentListReq();
        ResultMsg<Long> commentsRes = commentFeign.total(commentListReq);
        if (ResultMsg.isSuccess(commentsRes)){
            vo.setCommentNum(commentsRes.getData());
        }

        //获取留言总数
        ResultMsg<Long> messageRes = messageFeign.total();
        if (ResultMsg.isSuccess(messageRes)){
            vo.setMessageNum(messageRes.getData());
        }

        //获取文章总数
        Long articleNum = articleService.total();
        vo.setArticleNum(articleNum);

        return vo;
    }
}
