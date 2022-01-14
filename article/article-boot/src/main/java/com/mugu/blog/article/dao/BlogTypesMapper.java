package com.mugu.blog.article.dao;

import com.mugu.blog.article.common.model.po.Type;
import com.mugu.blog.article.common.model.req.TypeListReq;
import com.mugu.blog.article.common.model.vo.TypeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface BlogTypesMapper {
    int insert(Type type);

    List<TypeVo> list(TypeListReq param);

    Type selectById(Long id);

}
