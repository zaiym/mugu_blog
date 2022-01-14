package com.mugu.blog.article.service.impl;

import com.mugu.blog.article.common.model.po.Type;
import com.mugu.blog.article.common.model.req.TypeListReq;
import com.mugu.blog.article.common.model.vo.TypeVo;
import com.mugu.blog.article.dao.BlogTypesMapper;
import com.mugu.blog.article.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private BlogTypesMapper typesMapper;

    @Transactional
    @Override
    public void add(Type type) {
        type.setCreateTime(new Date());
        type.setUpdateTime(new Date());
        typesMapper.insert(type);
    }

    @Override
    public List<TypeVo> list(TypeListReq param) {
        return typesMapper.list(param);
    }

    @Override
    public Type getById(Long id) {
        return typesMapper.selectById(id);
    }
}
