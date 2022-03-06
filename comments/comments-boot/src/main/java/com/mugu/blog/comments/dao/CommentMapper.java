package com.mugu.blog.comments.dao;

import com.mugu.blog.comments.common.model.po.Comment;
import com.mugu.blog.comments.common.model.req.CommentListReq;
import com.mugu.blog.comments.common.model.req.MessageListReq;
import org.apache.ibatis.annotations.Mapper;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

@Mapper
public interface CommentMapper {

    /**
    * 新增
    * @date 2022/01/24
    **/
    int insert(Comment comment);

    /**
    * 刪除
    * @date 2022/01/24
    **/
    int delete(int id);

    /**
    * 更新
    * @date 2022/01/24
    **/
    int update(Comment comment);

    /**
    * 查询 根据主键 id 查询
    * @date 2022/01/24
    **/
    Comment load(String commentId);

    /**
    * 查询 分页查询
    * @date 2022/01/24
    **/
    List<Comment> pageList(CommentListReq req);

    List<Comment> pageMsgList(MessageListReq req);

    /**
    * 查询 分页查询 count
    * @date 2022/01/24
    **/
    int pageListCount(int offset,int pagesize);

    long totalComment(CommentListReq req);

    long totalMsg();

}