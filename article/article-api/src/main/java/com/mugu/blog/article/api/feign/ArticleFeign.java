package com.mugu.blog.article.api.feign;

import com.mugu.blog.article.common.model.req.ArticleAddReq;
import com.mugu.blog.article.common.model.req.ArticleDelReq;
import com.mugu.blog.article.common.model.req.ArticleInfoReq;
import com.mugu.blog.article.common.model.req.ArticleListReq;
import com.mugu.blog.article.common.model.vo.ArticleVo;
import com.mugu.blog.core.model.BaseParam;
import com.mugu.blog.core.model.PageData;
import com.mugu.blog.core.model.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "blog-article",contextId = "article-boot")
public interface ArticleFeign {
    @PostMapping("/blog-article/article/add")
    ResultMsg<Void> add(@RequestBody ArticleAddReq req);

    @PostMapping("/blog-article/article/list")
    ResultMsg<PageData<ArticleVo>> list(@RequestBody  ArticleListReq req);

    @PostMapping("/blog-article/article/del")
    ResultMsg<Void> delById(@RequestBody List<ArticleDelReq> params);

    @PostMapping("/blog-article/article/getById")
    ResultMsg<ArticleVo> getById(@RequestBody  ArticleInfoReq req);

    @PostMapping("/blog-article/article/search")
    ResultMsg<PageData<ArticleVo>> search(@RequestBody BaseParam param);
}
