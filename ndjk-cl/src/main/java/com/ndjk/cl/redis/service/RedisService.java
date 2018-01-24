package com.ndjk.cl.redis.service;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisService {

    String CONTACT_HEAD = "contact-phone";

    /**
     * redismap结构，保存所有map
     *
     * @param key      redis key值
     * @param paramMap redis map值
     * @author keyu.wang
     * @date 2017/8/11 19:10
     */
    void putAll(String key, Map<String, String> paramMap);

    /**
     * 自增 key
     * @param key
     * @param by
     * @return
     */
    Long incr(String key, Long by);

    /**
     * 设置过期时间
     *
     * @param key
     * @param by
     * @param unit
     * @author TED
     * @since  2017/12/1
     */
    Boolean expire(String key, Long by, TimeUnit unit);
    /**
     * 设置过期时间
     *
     * @param key
     * @param by
     * @author TED
     * @since  2017/12/1
     */
    Boolean expire(String key, Long by);

    /**
     * redis map结构，保存所有map 并设置过期时间
     *
     * @param key      redis key值
     * @param paramMap redis map值
     * @param hours    过期时长，单位为 小时
     * @author keyu.wang
     * @date 2017/8/21 13:58
     */
    void putAll(String key, Map<String, String> paramMap, Integer hours);

    /**
     * redis获取map结构所有数据
     *
     * @param key 要查询的key
     * @author keyu.wang
     * @date 2017/8/11 19:13
     */
    Map entries(String key);

    /**
     * redis map结构存储map的一个值
     *
     * @param key   redis key
     * @param field redis map结构对应的key
     * @param value redis map结构对应的value
     * @author keyu.wang
     * @date 2017/8/11 19:17
     */
    void put(String key, String field, String value);

    /**
     * redis map结构获取map的一个值
     *
     * @param key   redis key
     * @param field redis map结构对应的key
     * @author jiapeng
     */
    Object getMap(String key, String field);

    /**
     * redis set结构添加
     *
     * @param key   redis key
     * @param value redis value
     * @author keyu.wang
     * @date 2017/8/11 19:50
     */
    void sadd(String key, String value);

    /**
     * redis set 根据key获取所有集合
     *
     * @param key redis key
     * @author keyu.wang
     * @date 2017/8/11 19:52
     */
    Set sadd(String key);

    /**
     * redis String结构添加
     *
     * @param key   redis key
     * @param value redis String结构的value
     */
    void setValue(Object key, Object value);

    /**
     * redis String结构添加
     *
     * @param key   redis key
     * @param value redis String结构的value
     * @param hours    过期时长，单位为 分钟
     */
    void setValue(Object key, Object value, Integer hours);

    /**
     * redis String 根据key获取value
     *
     * @param key redis key
     * @return
     */
    Object getValue(Object key);



    /**
     * 释放redis锁
     *
     * @param key redis业务类型前缀+业务主键
     * @author keyu.wang
     * @date 2017/9/26 11:46
     */
    void unLock(String key);

    /**
     * redis map结构，保存对象字符串 并设置过期时间
     * @author wdl
     * @date 2017/8/21 13:58
     */
    void putAllHour(String key, Object object, Integer second);

    /**
     *
     * @param key redis判断Set中是否存在
     * @param filed
     * @return
     * @author wl
     */
    public Boolean isMember(String key, String filed);

    void delete(String key);
}
