package com.jztey.demo.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.jztey.demo.domain.MessageVo;

/**
 * 消息的接受者
 * 
 * @author yushi 2018-03-08
 */
@Component
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    private OmoronJms           omoronJms;

    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = "test.queue")
    public void receiveQueue(MessageVo mv) {
        logger.info("队列名--->test.queue" + "     Consumer收到的报文为:" + mv.toString());
        omoronJms.sendOpenIdToOmron(mv);

    }
}
