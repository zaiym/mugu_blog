package com.mugu.blog.article.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    /**
     * 交换机名称
     */
    public static final String EXCHANGE_NAME = "article_exchange";

    /**
     * 队列名称
     */
    public static final String QUEUE_NAME = "article_queue";

    public static final String ROUTING_KEY="article_key";

    /**
     * 创建一个队列
     *
     * @return
     */
    @Bean(QUEUE_NAME)
    public Queue ArticleQueue() {
        return new Queue(QUEUE_NAME,true);
    }

    /**
     * 创建一个交换机
     *
     * @return
     */
    @Bean(EXCHANGE_NAME)
    public DirectExchange  ArticleExchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    /**
     * @param exchange 交换机对象
     * @param queue    队列对象
     */
    @Bean
    public Binding articleBinding(@Qualifier(EXCHANGE_NAME) Exchange exchange, @Qualifier(QUEUE_NAME) Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY).noargs();
    }
}
