package com.example.rabbitmq_study.handler;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description:通过实现ReturnCallback接口
 * 如果消息从交换器发送到对应队列失败时触发（比如根据发送消息时指定的routingKey找不到队列时会触发）
 * @Author: lay
 * @Date: Created in 10:31 2018/12/20
 * @Modified By:IntelliJ IDEA
 */
@Component
public class ReturnCallBackHandler implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("消息主体 message："+message);
        System.out.println("应答码 replyCode: ："+replyCode);
        System.out.println("原因描述 replyText："+replyText);
        System.out.println("交换机 exchange："+exchange);
        System.out.println("消息使用的路由键 routingKey："+routingKey);
    }
}