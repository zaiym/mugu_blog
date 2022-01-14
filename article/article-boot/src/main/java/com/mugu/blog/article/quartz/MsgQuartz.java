package com.mugu.blog.article.quartz;

import com.mugu.blog.article.service.SendMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MsgQuartz {

    @Autowired
    private SendMsgService sendMsgService;


    /**
     * 定时扫描失败的消息重发消息
     * 每两分钟执行一次
     */

    @Scheduled(cron = "0 0/2 * * * ? ")
    @Async
    public void synSendMsg(){
        sendMsgService.reSendMsg();
    }

}
