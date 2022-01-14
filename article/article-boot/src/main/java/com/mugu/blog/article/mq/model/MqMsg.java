package com.mugu.blog.article.mq.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MqMsg {
    @ApiModelProperty(value = "唯一ID")
    private Long id;

    @ApiModelProperty(value = "消息体")
    private String msg;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "状态 1 已发送 0 发送失败 2 发送成功")
    private Integer status;

    @ApiModelProperty(value = "路由名称")
    private String exchange;

    @ApiModelProperty(value = "队列名称")
    private String queue;

    @ApiModelProperty(value = "路由键")
    private String routingKey;
}
