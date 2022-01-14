package com.mugu.blog.article.es.dao;

import com.mugu.blog.article.es.model.ArticleEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleRepository extends ElasticsearchRepository<ArticleEs,String> {

    Page<ArticleEs> findByContentLikeOrTitleLike(String content, String title, Pageable pageable);
}
