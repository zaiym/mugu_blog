package com.mugu.blog.article.mq.model;

import com.mugu.blog.article.common.model.po.Article;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ArticleMq extends Article implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "操作标记位：1. 新增 2 更新 3 删除")
    private Integer flag;
}
