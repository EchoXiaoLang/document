package com.hismart.document.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * json工具类
 *
 * @author renhua.zhang
 * @create 2018-06-26 20:16
 **/
public class JsonUtils {

    /**
     * ObjectMapper 忽略json中多余字段   jackson动态过滤属性
     * 设置在反序列化时忽略在JSON字符串中存在，而在Java中不存在的属性
     */
    private static ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    /**
     * 将对象转json字符串
     *
     * @param obj 对象
     * @return json字符串
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转对象
     *
     * @param json json字符串
     * @param <T>  对象class
     * @return T
     */
    public static <T> T parseObject(String json, Class<T> t) {
        if (StringUtils.isBlank(json)) {
            return null;
        }

        try {
            return mapper.readValue(json, t);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转对象
     *
     * @param json json字符串
     * @param <T>  JavaType
     * @return T
     */
    public static <T> T parseObject(String json, JavaType javaType) {
        if (StringUtils.isBlank(json)) {
            return null;
        }

        try {
            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}