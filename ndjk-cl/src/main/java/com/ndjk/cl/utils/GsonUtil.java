package com.ndjk.cl.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * @Author: wl
 * @Description: GsonUtil工具类
 * @Date: 2018/1/12  16:33
 * @Version: 2.0
 *
 */
public class GsonUtil {
    private static Logger logger = LoggerFactory.getLogger(GsonUtil.class);

    /**
     * 将字json字符串转换为制定的java对象
     *
     * @param tClass
     * @param jsonParam
     * @author wl
     * @date 2018/1/12  16:33
     */
    public static <T> T fromJson(Class<T> tClass, String jsonParam) {
        T object = null;

        if (StringUtil.isEmpty(jsonParam)) {//转换参数为空
            logger.info("json字符串转换为实体发生异常，jsonParam为空");
            return null;
        }

        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new DateTypeAdapter());
            Gson gson = builder.create();
            object = gson.fromJson(jsonParam, tClass);
        } catch (JsonSyntaxException e) {
            logger.error("json字符串转换为实体发生未知异常，jsonParam：" + jsonParam);
            logger.error(e.getMessage());
        }
        return object;
    }

    /**
     * 将java对象转换为字符串
     *
     * @param objParam java对象
     * @author wl
     * @date 2018/1/12  16:33
     */
    public static String toJson(Object objParam) {

        String json = null;

        if (objParam == null) {
            logger.info("java实体转换为字符串发生异常，objParam 为 null");
            return null;
        }

        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new DateTypeAdapter());
            Gson gson = builder.create();
            json = gson.toJson(objParam);
        } catch (Exception e) {
            logger.error("java实体转换为字符串发生未知异常",e);
            logger.error(e.getMessage());
        }
        return json;
    }

    public static <T> T fromJsonMap(String json, TypeToken<T> typeToken) {

        Gson gson = new GsonBuilder()
                /**
                 * 重写map的反序列化
                 */
                .registerTypeAdapter(new TypeToken<Map<String, Object>>() {
                }.getType(), new MapTypeAdapter()).create();

        return gson.fromJson(json, typeToken.getType());
    }


}
