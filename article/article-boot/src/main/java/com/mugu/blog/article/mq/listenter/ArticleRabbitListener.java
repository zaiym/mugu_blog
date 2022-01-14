package com.mugu.blog.article.mq.listenter;

import com.alibaba.fastjson.JSON;
import com.mugu.blog.article.es.dao.ArticleRepository;
import com.mugu.blog.article.es.model.ArticleEs;
import com.mugu.blog.article.mq.config.RabbitMqConfig;
import com.mugu.blog.article.mq.model.ArticleMq;
import com.mugu.blog.core.constant.KeyConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * MQ的监听器，用来监听文章的队列，将其放入ElasticSearch
 * TODO 死信队列、定时任务重发推送失败的消息
 */
@SuppressWarnings("ALL")
@Component
@Slf4j
public class ArticleRabbitListener {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 监听文章的队列，将文章存入ElasticSearch中
     * @param message  消息体 其中包含消息头、消息体（文章的JSON数据）
     * @param channel 管道，用于ack消息
     * @param deliveryTag 消息的唯一标识
     */
    @RabbitListener(queues = {RabbitMqConfig.QUEUE_NAME})
    public void consumerArticleQueue(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag){
        MessageProperties properties = message.getMessageProperties();
        String messageId = properties.getMessageId();

        //保证消息的幂等性，使用setNx，如果插入成功则是第一次消费
         if(!stringRedisTemplate.opsForValue().setIfAbsent(KeyConstant.ARTICLE_MESSAGE_KEY+messageId, "1",5, TimeUnit.HOURS))
             return;
        String data = new String(message.getBody());
        log.debug("接收到一条消息，内容为：{}，唯一ID为：{}", data,deliveryTag);
        ArticleMq article = JSON.parseObject(data, ArticleMq.class);
        ArticleEs articleEs = new ArticleEs();
        BeanUtils.copyProperties(article,articleEs);
        articleEs.setId(String.valueOf(article.getId()));
        dohandlerMQ(articleEs,article.getFlag());
        try {
            //手动确认消息
            //multiple：为了减少网络流量，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
            channel.basicAck(deliveryTag,false);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("处理文章消息失败：{}",e.getMessage());
        }
    }

    private void dohandlerMQ(ArticleEs article,Integer flag){
        //删除
        if (Integer.valueOf(3).equals(flag)){
            articleRepository.deleteById(article.getId());
        }else{
            //新增、更新
            articleRepository.save(article);
        }
    }

}
