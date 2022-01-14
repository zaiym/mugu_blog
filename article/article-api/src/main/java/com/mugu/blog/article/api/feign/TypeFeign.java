package com.mugu.blog.article.api.feign;

import com.mugu.blog.article.common.model.po.Type;
import com.mugu.blog.article.common.model.req.TypeAddReq;
import com.mugu.blog.article.common.model.req.TypeListReq;
import com.mugu.blog.article.common.model.vo.TypeVo;
import com.mugu.blog.core.model.PageData;
import com.mugu.blog.core.model.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "blog-article",contextId = "type-boot")
public interface TypeFeign {

    @PostMapping("/blog-article/type/add")
    ResultMsg<Void> add(@RequestBody TypeAddReq param);

    @PostMapping("/blog-article/type/list")
    ResultMsg<PageData<TypeVo>> list(@RequestBody TypeListReq param);

    @GetMapping("/blog-article/type/getById")
    ResultMsg<Type> getById(@RequestParam(value = "id") Long id);
}
