package com.example.rabbitmq_study.controller;

import com.example.rabbitmq_study.sender.BusinessMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("rabbitmq")
@RestController
public class RabbitMQMsgController {

    @Autowired
    private BusinessMessageSender sender;

    @RequestMapping("sendmsg")
    public void sendMsg(String msg){
        sender.sendMsg(msg);
    }

    /**
     * 模拟延迟队列消息
     * @param msg
     */
    @RequestMapping("delaymsg")
    public void delaymsg(String msg){
        sender.sendDelayMsg(msg);
    }
}