package com.jztey.demo.tools;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Util {

    private static final Logger log = LoggerFactory.getLogger(MD5Util.class);

    /**
     * 密码MD5加密 转换成32位MD5校验码(未加token验证)
     * 
     * @param String类型密码
     * @return
     */
    public static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            log.error("error", e);
            return null;
        }
    }
}
