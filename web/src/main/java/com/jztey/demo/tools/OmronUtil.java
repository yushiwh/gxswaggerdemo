package com.jztey.demo.tools;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

/**
 * 工具类的方法
 * 
 * @author yushi 2017-10-26
 */
@Repository
public class OmronUtil {

    private static final Logger logger = LoggerFactory.getLogger(OmronUtil.class);

    /**
     * 日期格式化方法
     * 
     * @param dateStr
     * @return
     */
    public String dateChange(Object dateStr) {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // 这个就是把时间戳经过处理得到期望格式的时间
            str = sdf.format(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

}
