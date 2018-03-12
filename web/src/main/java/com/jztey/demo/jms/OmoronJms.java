package com.jztey.demo.jms;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jztey.demo.domain.MessageVo;

/**
 * 向欧姆龙提交数据
 * 
 * @author yushi
 */
@Service
public class OmoronJms {

    private static final Logger logger = LoggerFactory.getLogger(OmoronJms.class);

    /**
     * 向omuron提交数据，用消息队列来控制
     */
    public void sendOpenIdToOmron(MessageVo mv) {
        try {
            logger.info("通过消息队列获取的消息实体:" + mv.toString());
            /// 下面可以处理自己的任何业务
        } catch (Exception e) {
            logger.info("saveValueMemberidOmronidBinding--->提交到欧姆龙出现异常" + e);
        }
    }
}
