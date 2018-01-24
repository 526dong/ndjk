package com.ndjk.cl.redis.service.impl;


import com.ndjk.cl.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service("redisService")
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * redis map结构，保存所有map
     *
     * @param key      redis key值
     * @param paramMap redis map值
     * @author keyu.wang
     * @date 2017/8/11 19:10
     */
    public void putAll(String key, Map<String, String> paramMap) {
        HashOperations ops = this.redisTemplate.opsForHash();
        ops.putAll(key, paramMap);
    }

    /**
     * 自增 key
     * @param key
     * @param by
     * @return
     */
    @Override
    public  Long incr(String key, Long by){
        return redisTemplate.opsForValue().increment(key, by);
    }
    /**
     * 设置过期时间
     *
     * @param key
     * @param by
     * @param unit
     * @author TED
     * @since  2017/12/1
     */
    @Override
    public  Boolean expire(String key, Long by, TimeUnit unit){
        return redisTemplate.expire(key, by,unit);
    }
    /**
     * 设置过期时间
     *
     * @param key
     * @param by
     * @author TED
     * @since  2017/12/1
     */
    @Override
    public  Boolean expire(String key, Long by){
        return this.expire(key, by,TimeUnit.SECONDS);
    }



    /**
     * redis map结构，保存所有map 并设置过期时间
     *
     * @param key      redis key值
     * @param paramMap redis map值
     * @param hours    过期时长，单位为 小时
     * @author keyu.wang
     * @date 2017/8/21 13:58
     */
    public void putAll(String key, Map<String, String> paramMap, Integer hours) {
        HashOperations ops = this.redisTemplate.opsForHash();
        ops.putAll(key, paramMap);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (hours != null && hours.intValue() > 0) {
            calendar.add(Calendar.HOUR, hours);
        } else {
            calendar.add(Calendar.HOUR, 2);
        }
        this.redisTemplate.expireAt(key, calendar.getTime());
    }

    /**
     * redis map结构，保存所有map 并设置过期时间
     * @author wdl
     * @date 2017/8/21 13:58
     */
    public void putAllHour(String key, Object object, Integer hour) {
        ValueOperations ops = this.redisTemplate.opsForValue();
        ops.set(key, object);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (hour != null && hour.intValue() > 0) {
            calendar.add(Calendar.HOUR_OF_DAY, hour);
        } else {
            calendar.add(Calendar.HOUR_OF_DAY, 2);
        }
        this.redisTemplate.expireAt(key, calendar.getTime());
    }

    /**
     * redis获取map结构所有数据
     *
     * @param key 要查询的key
     * @author keyu.wang
     * @date 2017/8/11 19:13
     */
    public Map entries(String key) {
        HashOperations ops = this.redisTemplate.opsForHash();
        Map entries = ops.entries(key);
        return entries;
    }

    /**
     * redis map结构存储map的一个值
     *
     * @param key   redis key
     * @param field redis map结构对应的key
     * @param value redis map结构对应的value
     * @author keyu.wang
     * @date 2017/8/11 19:17
     */
    public void put(String key, String field, String value) {
        HashOperations ops = this.redisTemplate.opsForHash();
        ops.put(key, field, value);
    }

    /**
     * redis map结构获取map的一个值
     *
     * @param key   redis key
     * @param field redis map结构对应的key
     * @author jiapeng
     */
    public Object getMap(String key, String field) {
        HashOperations ops = this.redisTemplate.opsForHash();
        Object value = ops.get(key, field);
        return value;
    }

    /**
     * redis set结构添加
     *
     * @param key   redis key
     * @param value redis value
     * @author keyu.wang
     * @date 2017/8/11 19:50
     */
    public void sadd(String key, String value) {
        SetOperations ops = this.redisTemplate.opsForSet();
        ops.add(key, value);
    }

    /**
     * redis set 根据key获取所有集合
     *
     * @param key redis key
     * @author keyu.wang
     * @date 2017/8/11 19:52
     */
    public Set sadd(String key) {
        SetOperations ops = this.redisTemplate.opsForSet();
        Set members = ops.members(key);
        return members;
    }

    /**
     * redis String结构添加
     *
     * @param key   redis key
     * @param value redis String结构的value
     */
    @Override
    public void setValue(Object key, Object value) {
        ValueOperations ops = this.redisTemplate.opsForValue();
        ops.set(key, value);
    }

    /**
     * redis String结构添加
     *
     * @param key   redis key
     * @param value redis String结构的value
     * @param hours 过期时长，单位为 小时
     */
    @Override
    public void setValue(Object key, Object value, Integer hours) {
        ValueOperations ops = this.redisTemplate.opsForValue();
        ops.set(key, value);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 5);
        this.redisTemplate.expireAt(key, calendar.getTime());
    }


    /**
     * redis String 根据key获取value
     *
     * @param key redis key
     * @return
     */
    @Override
    public Object getValue(Object key) {
        ValueOperations ops = this.redisTemplate.opsForValue();
        Object value = ops.get(key);
        return value;
    }

    /**
     * redis上锁 , 20分钟后锁自动释放锁
     *
     * @param key redis业务类型前缀+业务主键
     * @return boolean 是否加锁成功
     * @author keyu.wang
     * @date 2017/9/26 11:40
     */
    public Boolean lock(String key) {
        ValueOperations ops = this.redisTemplate.opsForValue();
        String value = (String) ops.get(key);
        if (value != null && !"".equals(value)) {
            return false;
        } else {
            ops.set(key, key, 20L, TimeUnit.MINUTES);
            return true;
        }
    }


    /**
     * redis上锁 , 并在指定时间后自动释放锁
     *
     * @param key     redis业务类型前缀+业务主键
     * @param minutes 锁定时长
     * @return boolean 是否加锁成功
     * @author keyu.wang
     * @date 2017/9/26 11:40
     */
    public Boolean lock(String key, Long minutes) {
        ValueOperations ops = this.redisTemplate.opsForValue();
        String value = (String) ops.get(key);
        if (value != null && !"".equals(value)) {
            return false;
        } else {
            if (minutes == null || minutes <= 0) {
                minutes = 20L;
            }
            ops.set(key, key, minutes, TimeUnit.MINUTES);
            return true;
        }
    }

    /**
     * 释放redis锁
     *
     * @param key redis业务类型前缀+业务主键
     * @author keyu.wang
     * @date 2017/9/26 11:46
     */
    public void unLock(String key) {
        this.redisTemplate.delete(key);
    }

    /**
     * @param key   redis判断Set中是否存在
     * @param filed
     * @return
     * @author wl
     */
    public Boolean isMember(String key, String filed) {
        SetOperations setOperations = this.redisTemplate.opsForSet();
        Boolean member = setOperations.isMember(key, filed);
        return member;
    }


    /**
     * 按照key删除
     * @param key
     * @return
     * @author fuwei.zheng
     */
    @Override
    public void delete(String key){
        redisTemplate.delete(key);
    }
}
