package com.example.rabbitmq_study.handler;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description:通过实现ConfirmCallBack接口，消息发送到交换器Exchange后触发回调。
 * @Author: lay
 * @Date: Created in 10:20 2018/12/20
 * @Modified By:IntelliJ IDEA
 */
@Component
public class ConfirmCallBackHandler implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("消息唯一标识："+correlationData);
        System.out.println("确认结果："+ack);
        System.out.println("失败原因："+cause);
    }
}
