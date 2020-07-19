package com.example.rabbitmq_study.config;


import com.example.rabbitmq_study.handler.ConfirmCallBackHandler;
import com.example.rabbitmq_study.handler.ReturnCallBackHandler;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {


    //创建业务队列交换机
    public static final String BUSINESS_EXCHANGE_NAME = "dead.letter.demo.simple.business.exchange";
    //创建业务队列A
    public static final String BUSINESS_QUEUEA_NAME = "dead.letter.demo.simple.business.queuea";
    //创建业务队列B
    public static final String BUSINESS_QUEUEB_NAME = "dead.letter.demo.simple.business.queueb";
    // 创建死信队列交换机
    public static final String DEAD_LETTER_EXCHANGE = "dead.letter.demo.simple.deadletter.exchange";
    // 创建死信队列路由A key
    public static final String DEAD_LETTER_QUEUEA_ROUTING_KEY = "dead.letter.demo.simple.deadletter.queuea.routingkey";
    // 创建死信队列路由B key
    public static final String DEAD_LETTER_QUEUEB_ROUTING_KEY = "dead.letter.demo.simple.deadletter.queueb.routingkey";
    // 创建死信队列A
    public static final String DEAD_LETTER_QUEUEA_NAME = "dead.letter.demo.simple.deadletter.queuea";
    // 创建死信队列B
    public static final String DEAD_LETTER_QUEUEB_NAME = "dead.letter.demo.simple.deadletter.queueb";

    @Bean
    @Scope("prototype")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMandatory(true);
        template.setMessageConverter(new SerializerMessageConverter());
        return template;
    }

    // 声明业务 广播模式 Exchange
    @Bean("businessExchange")
    public FanoutExchange businessExchange(){
        return new FanoutExchange(BUSINESS_EXCHANGE_NAME);
    }

    // 声明死信 单波博士 Exchange
    @Bean("deadLetterExchange")
    public DirectExchange deadLetterExchange(){
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    // 声明业务队列A 并绑定死信队列 exchange 和exchange key A
    @Bean("businessQueueA")
    public Queue businessQueueA(){
        Map<String, Object> args = new HashMap<>(2);
//       x-dead-letter-exchange    这里声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
//       x-dead-letter-routing-key  这里声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUEA_ROUTING_KEY);
        return QueueBuilder.durable(BUSINESS_QUEUEA_NAME).withArguments(args).build();
    }

    // 声明业务队列B 并绑定死信队列 exchange 和exchange key  B
    @Bean("businessQueueB")
    public Queue businessQueueB(){
        Map<String, Object> args = new HashMap<>(2);
//       x-dead-letter-exchange    这里声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
//       x-dead-letter-routing-key  这里声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUEB_ROUTING_KEY);
        return QueueBuilder.durable(BUSINESS_QUEUEB_NAME).withArguments(args).build();
    }

    // 声明死信队列A
    @Bean("deadLetterQueueA")
    public Queue deadLetterQueueA(){
        return new Queue(DEAD_LETTER_QUEUEA_NAME);
    }

    // 声明死信队列B
    @Bean("deadLetterQueueB")
    public Queue deadLetterQueueB(){
        return new Queue(DEAD_LETTER_QUEUEB_NAME);
    }

    // 声明业务队列A 与业务路由绑定关系
    @Bean
    public Binding businessBindingA(@Qualifier("businessQueueA") Queue queue,
                                    @Qualifier("businessExchange") FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }

    // 声明业务队列B与业务路由绑定关系
    @Bean
    public Binding businessBindingB(@Qualifier("businessQueueB") Queue queue,
                                    @Qualifier("businessExchange") FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }

    // 声明死信队列A绑定关系
    @Bean
    public Binding deadLetterBindingA(@Qualifier("deadLetterQueueA") Queue queue,
                                      @Qualifier("deadLetterExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_QUEUEA_ROUTING_KEY);
    }

    // 声明死信队列B绑定关系
    @Bean
    public Binding deadLetterBindingB(@Qualifier("deadLetterQueueB") Queue queue,
                                      @Qualifier("deadLetterExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_QUEUEB_ROUTING_KEY);
    }
}
