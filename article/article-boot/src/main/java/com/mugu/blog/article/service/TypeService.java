package com.mugu.blog.article.service;


import com.mugu.blog.article.common.model.po.Type;
import com.mugu.blog.article.common.model.req.TypeListReq;
import com.mugu.blog.article.common.model.vo.TypeVo;

import java.util.List;


public interface TypeService {
    void add(Type type);

    List<TypeVo> list(TypeListReq param);

    Type getById(Long id);


}
