package com.mugu.blog.file.server.model.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FilePO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 文件的md5
     */
    private String md5;

    /**
     * 文件路径
     */
    private String location;

    /**
     * 状态 1 已保存 2 已发布 0 删除
     */
    private Integer status;

    /**
     * create_time
     */
    private Date createTime;

    /**
     * update_time
     */
    private Date updateTime;

}