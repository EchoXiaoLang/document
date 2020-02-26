package com.hismart.document.common.service;


import java.util.concurrent.TimeUnit;

public interface CacheService {


    /**
     * 测试 Redis是否连接成功
     */
    void testConnect() throws Exception;



    /**
     * 判断key是否存在
     *
     * @param key key
     * @return true表示存在
     */
    boolean hasKey(Object key);

    /**
     * 根据key获取值
     *
     * @param key key
     * @return value
     */
    <T> T get(Object key);

    /**
     * 根据key获取值
     *
     * @param key     key
     * @param hashKey hashKey
     * @return value
     */
    <T> T get(Object key, Object hashKey);

    /**
     * 设置单个值，如果key已经存在，则会被覆盖
     *
     * @param key   key
     * @param value 数据对象
     */
    void set(Object key, Object value);

    /**
     * 设置单个值，如果key已经存在，则会被覆盖
     *
     * @param key           key
     * @param value         数据对象
     * @param timeoutSecond 到期时间（单位秒）
     */
    void set(Object key, Object value, long timeoutSecond);

    /**
     * 设置单个值，如果key已经存在，则会被覆盖
     * hashKey，如果缓存没有hash存储功能，则应该key.append(hashKey)作为key
     *
     * @param key     key
     * @param hashKey hashKey
     * @param value   数据对象
     */
    void put(Object key, Object hashKey, Object value);

    /**
     * 根据key设置过期时间
     *
     * @param key           key
     * @param timeoutSecond 到期时间（单位秒）
     */
    void expire(Object key, int timeoutSecond);

    /**
     * 根据key,删除单个值
     *
     * @param key key
     * @return 删除的值，没有则返回null
     */
    <T> T remove(Object key);

    /**
     * 根据key,删除单个值
     *
     * @param key key
     */
    void removeNoReturn(Object key);

    /**
     * 计数器自增
     *
     * @param key key
     * @return 计数器当前值
     */
    long counterIncrement(Object key);

    /**
     * 获取计数器值
     *
     * @param key key
     * @return 计数器当前值
     */
    long getCounter(Object key);

    void  set(Object key, Object value1, long value2, TimeUnit timeoutSecond);



}
