package com.mugu.blog.article.service;


public interface SendMsgService {
    void sendMsg(String exchange,String routingKey,String data);

    /**
     * 定时重发消息
     */
    void reSendMsg();

}
