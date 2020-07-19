package com.example.rabbitmq_study.sender;

import com.example.rabbitmq_study.handler.ConfirmCallBackHandler;
import com.example.rabbitmq_study.handler.ReturnCallBackHandler;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.example.rabbitmq_study.config.RabbitMQConfig.*;

@Component
public class BusinessMessageSender{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String msg){
//        //消息发送确认，发送到交换器Exchange后触发回调

        rabbitTemplate.setConfirmCallback(new ConfirmCallBackHandler());
//        //消息发送确认，如果消息从交换器发送到对应队列失败时触发（比如根据发送消息时指定的routingKey找不到队列时会触发）
        rabbitTemplate.setReturnCallback(new ReturnCallBackHandler());
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //当把 mandotory 参数设置为 true 时，如果交换机无法将消息进行路由时，
        // 会将该消息返回给生产者需要实现RabbitTemplate.ReturnCallback接口，
        // 而如果该参数设置为false，如果发现消息无法进行路由，则直接丢弃
//        rabbitTemplate.setMandatory(true);
//        rabbitTemplate.convertSendAndReceive(BUSINESS_EXCHANGE_NAME, "", msg);

        System.out.println("fewfewfewf"+rabbitTemplate);
        rabbitTemplate.convertAndSend(BUSINESS_EXCHANGE_NAME, "key", msg,correlationData);
    }
}