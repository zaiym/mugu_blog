package com.mugu.blog.article.common.model.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ArticleDelReq {
    @NotBlank
    @ApiModelProperty(value = "文章唯一ID",required = true)
    private String id;
}
