package com.jztey.demo.jms;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import com.jztey.demo.domain.MessageVo;


/**
 * 消息的生产者
 * 
 * @author yushi 2018-03-08
 */
@Service("producer")
public class Producer {

    @Autowired // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
    private JmsMessagingTemplate jmsTemplate; // 发送消息，destination是发送到的队列，message是待发送的消息

    public void sendMessage(Destination destination, MessageVo mv) {
        jmsTemplate.convertAndSend(destination, mv);
    }
}
