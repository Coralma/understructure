package com.coral.base.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * JsonUtil转化处理
 */
public final class JsonUtils {

    private JsonUtils() {
    }

    /**
     * 将对象转换为json字符串.
     *
     * @param obj 被转换的对象
     * @param <T> 类型参数
     * @return json字符串
     */
    public static <T> String toString(final T obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 通过指定序列化参数，将对象转换为json字符串
     *
     * @param obj      被转换的对象
     * @param features 序列化参数
     * @param <T>      类型参数
     * @return json字符串
     */
    public static <T> String toString(final T obj, final SerializerFeature... features) {
        return JSON.toJSONString(obj, features);
    }

    /**
     * 将json串转换为类型为className的对象. 处理如下json：{'field1':1,'field2':'a'}
     *
     * @param <T>        类型参数
     * @param jsonString json字符串
     * @param className  对象的Class类型对象
     * @return 对象
     */
    public static <T> T toObject(final String jsonString, final Class<T> className) {
        return JSON.parseObject(jsonString, className);
    }

    /**
     * 将json串转换为类型为className的对象集合. 处理如下json：[{'field1':1,'field2':'a'},{'field1':2,'field2':'b'}]
     *
     * @param <T>        类型参数
     * @param jsonString json字符串
     * @param className  Class类型对象
     * @return 转换成的list
     */
    public static <T> List<T> stringToArray(final String jsonString, final Class<T> className) {
        return JSON.parseArray(jsonString, className);
    }

    /**
     * JSONObject转Map<String,String>.
     *
     * @param jsonObject jsonObject
     * @return map
     */
    public static Map<String, String> jsonObjectToMap(final JSONObject jsonObject) {
        if (jsonObject != null) {
            final Map<String, String> result = Maps.newHashMap();
            final Iterator<String> iterator = jsonObject.keySet().iterator();
            String key;
            String value;
            while (iterator.hasNext()) {
                key = iterator.next();
                value = jsonObject.getString(key);
                result.put(key, value);
            }
            return result;
        }
        return null;
    }

    /**
     * jsonArray转List<Map<String, String>>.
     *
     * @param jsonArray json数组
     * @return 转换后的list
     */
    public static List<Map<String, String>> jsonArrayToListMap(final JSONArray jsonArray) {
        final List<Map<String, String>> result = Lists.newArrayList();
        for (int i = 0, len = jsonArray.size(); i < len; i++) {
            result.add(jsonObjectToMap(jsonArray.getJSONObject(i)));
        }
        return result;
    }

    /**
     * Map<String, String>转字符串.
     *
     * @param map Map<String, String>对象
     * @return json字符串
     */
    public static String mapToJsonString(final Map<String, String> map) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for (final String key : map.keySet()) {
            stringBuilder.append("\"");
            stringBuilder.append(key);
            stringBuilder.append("\"");
            stringBuilder.append(":");
            stringBuilder.append("\"");
            stringBuilder.append(map.get(key));
            stringBuilder.append("\"");
            stringBuilder.append(",");
        }
        String result = stringBuilder.toString();
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }
        return result + "}";
    }
}
