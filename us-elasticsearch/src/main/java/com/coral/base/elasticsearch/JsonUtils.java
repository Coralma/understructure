package com.coral.base.elasticsearch;

import com.alibaba.fastjson.JSON;

public class JsonUtils {

    public static <T> String toString(T obj) {
        return JSON.toJSONString(obj);
    }

    public static <T> T parseObject(String str, Class<T> clazz) {
        try {
            return JSON.parseObject(str, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
