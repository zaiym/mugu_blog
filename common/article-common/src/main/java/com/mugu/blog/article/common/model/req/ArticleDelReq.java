package com.mugu.blog.article.common.model.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ArticleDelReq {
    @NotNull
    @ApiModelProperty(value = "文章ID",required = true)
    private Long id;
}
