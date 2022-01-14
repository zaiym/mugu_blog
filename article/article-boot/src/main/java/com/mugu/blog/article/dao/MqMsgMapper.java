package com.mugu.blog.article.dao;

import com.mugu.blog.article.mq.model.MqMsg;

import java.util.List;

public interface MqMsgMapper  {

    /**
     * 新增
     * @author zhengkai.blog.csdn.net
     * @date 2022/01/20
     **/
    int insert(MqMsg mqMsg);

    /**
     * 刪除
     * @author zhengkai.blog.csdn.net
     * @date 2022/01/20
     **/
    int delete(int id);

    /**
     * 更新
     * @author zhengkai.blog.csdn.net
     * @date 2022/01/20
     **/
    int update(MqMsg mqMsg);

    /**
     * 查询 根据主键 id 查询
     * @author zhengkai.blog.csdn.net
     * @date 2022/01/20
     **/
    MqMsg load(int id);

    /**
     * 查询 分页查询
     * @author zhengkai.blog.csdn.net
     * @date 2022/01/20
     **/
    List<MqMsg> pageList(int offset, int pagesize);

    /**
     * 查询 分页查询 count
     * @author zhengkai.blog.csdn.net
     * @date 2022/01/20
     **/
    int pageListCount(int offset,int pagesize);

    List<MqMsg> listNoSendSuccess();

}
