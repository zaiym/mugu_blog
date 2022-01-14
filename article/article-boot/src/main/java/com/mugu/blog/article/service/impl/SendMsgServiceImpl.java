package com.mugu.blog.article.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.mugu.blog.article.dao.MqMsgMapper;
import com.mugu.blog.article.mq.model.MqMsg;
import com.mugu.blog.article.service.SendMsgService;
import com.mugu.blog.common.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Slf4j
public class SendMsgServiceImpl implements SendMsgService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MqMsgMapper msgMapper;

    @PostConstruct
    public void init() {
        //消息未送达队列触发回调
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.error("消息发送失败，未送达队列，message：{},replyCode：{}，replyText：{}，exchange：{}，exchange：{}", JSON.toJSONString(message), replyCode, replyText, exchange, routingKey);
            MqMsg msg = JSON.parseObject(new String(message.getBody()), MqMsg.class);
            //设置消息的状态为发送失败
            MqMsg msg1 = MqMsg.builder()
                    .id(msg.getId())
                    //设置状态为未送达队列
                    .status(3)
                    .build();
            msgMapper.update(msg1);
        });
        //消息进入到Exchange触发回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            String id = Objects.requireNonNull(correlationData.getId());
            if (!ack) {
                log.error("消息未发送成功，返回信息：{}", cause);
                //设置消息的状态为发送失败
                MqMsg msg1 = MqMsg.builder()
                        .id(Long.valueOf(id))
                        .status(0)
                        .build();
                msgMapper.update(msg1);
            } else { //消息发送成功
                //设置消息的状态为发送成功
                MqMsg msg1 = MqMsg.builder()
                        .id(Long.valueOf(id))
                        .status(2)
                        .build();
                msgMapper.update(msg1);
            }
        });
    }

    @Transactional
    @Override
    public void sendMsg(String exchange, String routingKey, String data) {
        //消息入库，返回唯一ID
        MqMsg msg = MqMsg.builder()
                .createTime(new Date())
                .exchange(exchange)
                .routingKey(routingKey)
                .msg(data)
                .updateTime(new Date())
                .status(1)
                .build();
        int i = msgMapper.insert(msg);
        AssertUtils.assertTrue(i == 1);
        //组装消息内容
        MessageProperties properties = new MessageProperties();
        //消息唯一ID，用力防止幂等性
        properties.setMessageId(msg.getId().toString());
        Message message = new Message(data.getBytes(StandardCharsets.UTF_8), properties);
        //发送消息,利用CorrelationData将ID传递下去
        //这里不必try-catch 即使发送失败
        rabbitTemplate.convertAndSend(exchange, routingKey, message, new CorrelationData(String.valueOf(msg.getId())));
    }

    @Override
    public void reSendMsg() {
        //查询消息表中未发送成功的消息
        List<MqMsg> msgs = msgMapper.listNoSendSuccess();
        if (CollectionUtil.isEmpty(msgs))
            return;
        //发送消息
        for (MqMsg msg : msgs) {
            try{
                //组装消息内容
                MessageProperties properties = new MessageProperties();
                //消息唯一ID，用力防止幂等性
                properties.setMessageId(msg.getId().toString());
                Message message = new Message(msg.getMsg().getBytes(StandardCharsets.UTF_8), properties);
                //发送消息,利用CorrelationData将ID传递下去
                rabbitTemplate.convertAndSend(msg.getExchange(), msg.getRoutingKey(), message, new CorrelationData(String.valueOf(msg.getId())));
            }catch (Exception ex){
                log.error("发送一条消息失败，消息ID：{}，异常信息：{}",msg.getId(),ex.getMessage());
            }
        }
    }
}
