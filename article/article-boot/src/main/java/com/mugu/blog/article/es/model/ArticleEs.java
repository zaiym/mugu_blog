package com.mugu.blog.article.es.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = "article" , type = "blog")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleEs implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @ApiModelProperty(value ="id")
    private String id;

    /**
     * 作者userid
     */
    @ApiModelProperty(value ="作者userid")
    private String authorId;

    /**
     * 分类id
     */
    @ApiModelProperty(value ="分类id")
    private Long typeId;

    /**
     * 标题
     */
    @ApiModelProperty(value ="标题")
    @Field(type = FieldType.Text)
    private String title;

    /**
     * 内容
     */
    @ApiModelProperty(value ="内容")
    @Field(type = FieldType.Text)
    private String content;

    /**
     * 来源：1 原创 2 转载 3 翻译
     */
    @ApiModelProperty(value ="来源：1 原创 2 转载 3 翻译")
    private Integer source;

    /**
     * 阅读人数
     */
    @ApiModelProperty(value ="阅读人数")
    private Long viewNum;

    @ApiModelProperty(value = "摘要-描述")
    @Field(type = FieldType.Text)
    private String describe;

    /**
     * 文章首图链接
     */
    @ApiModelProperty(value ="文章首图链接")
    private String imgUrl;

    /**
     * 是否推荐 1 推荐 0 不推荐
     */
    @ApiModelProperty(value ="id")
    private Integer isRecommend;

    /**
     * 转载说明 1 说明 0 不说明
     */
    @ApiModelProperty(value ="转载说明 1 说明 0 不说明")
    private Integer isExplain;

    /**
     * 是否赞赏
     */
    @ApiModelProperty(value ="是否赞赏")
    private Integer isAppreciate;

    /**
     * 是否评论
     */
    @ApiModelProperty(value ="是否评论")
    private Integer isComment;

    /**
     * 状态 1 已保存 2 已发布 0 删除
     */
    @ApiModelProperty(value ="状态 1 已保存 2 已发布 0 删除")
    private Integer status;

//    @Field(type = FieldType.Date, format = DateFormat.custom,pattern = "yyyy-MM-dd")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

//    @Field(type = FieldType.Date, format = DateFormat.custom,pattern = "yyyy-MM-dd")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd",timezone="GMT+8")
    private Date updateTime;
}
