package com.example.rabbitmq_study.controller;

import com.example.rabbitmq_study.sender.BusinessMessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("rabbitmq")
@RestController
@Slf4j
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

    @RequestMapping("delayMsg2/{delayTime}")
    public void delayMsg2(String msg, @PathVariable Integer delayTime) {
        log.info("当前时间：{},收到请求，msg:{},delayTime:{}", new Date(), msg, delayTime);
        sender.sendDelayMsg(msg, delayTime);
    }
}