package com.jztey.demo.tools;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONObject;

/**
 * 向欧姆龙提交数据的
 * 
 * @author yushi 2018-03-01
 */
public class OmronCommitUtil {

    private static final Logger logger = LoggerFactory.getLogger(OmronCommitUtil.class);

    /**
     * 向欧姆龙方面提交
     * 
     * @param url 欧姆龙传递的url
     * @param map 参数集合
     * @param method 调用的方法 POST，GET，POSTWithJson等
     */
    public static String CommitOmron(String url, Map<String, Object> map, EnumUtil em) {
        String returnjson = "";
        ObjectMapper json = new ObjectMapper();
        JSONObject liftobj = null;
        // POSTWithJson的方法
        if (em == EnumUtil.POSTWITHJSON) {
            try {
                returnjson = HttpClientUtil.httpPostWithJSON(url, json.writeValueAsString(map));
                liftobj = JSONObject.fromObject(returnjson);
            } catch (JsonProcessingException e) {
                logger.error("CommitOmron--->json转换失败--->" + e);
            } catch (Exception e) {
                logger.error("CommitOmron--->提交到欧姆龙失败--->" + e);
            }
        }

        // GET方法
        if (em == EnumUtil.GET) {
            try {
                returnjson = HttpClientUtil.httpGet(url);
                liftobj = JSONObject.fromObject(returnjson);
            } catch (JsonProcessingException e) {
                logger.error("CommitOmron--->json转换失败--->" + e);
            } catch (Exception e) {
                logger.error("CommitOmron--->提交到欧姆龙失败--->" + e);
            }
        }

        if (null != liftobj.getString("code")) {
            logger.info("CommitOmron--->欧姆龙返回的code值-->" + liftobj.getString("code"));
            logger.info("CommitOmron--->欧姆龙返回的message值-->" + liftobj.getString("message"));
            if ("1".equals(liftobj.getString("code"))) {
                logger.info("CommitOmron--->http获取欧姆龙数据成功");
            } else {
                logger.error("CommitOmron--->http获取欧姆龙数据失败失败");
            }
        }

        return returnjson;

    }
}
