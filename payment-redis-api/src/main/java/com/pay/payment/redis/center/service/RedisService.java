package com.pay.payment.redis.center.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存基本操作类
 */
public interface RedisService {

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value);

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime);

    /**
     *
     * 根据key 重偏移位置覆盖value
     *
     * @param key key值
     * @param value 值
     * @param offset 偏移位置
     * @return
     */
    public boolean setOverwriteValue(String key, Object value, long offset);

    /**
     * 判断key值是否存在，如果存在设置新值
     * @param key
     * @param value
     * @return
     */
    public boolean getAndSet(String key,Object value);



    public boolean setBit(String key, long offset,boolean status);
    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys);
    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern);

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key);
    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key);
    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key);

    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value);
    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey);
    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    public void lPush(String k, Object v);

    /**
     * 列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(String k, long l, long l1) ;

    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    public void addSet(String key, Object value);

    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    public Set<Object> getSet(String key);

    /**
     * 判断集合中知否存在某个值
     * @param key
     * @param obj
     * @return
     */
    public Boolean checkKeyAndValue(String key,Object obj);

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */
    void zSet(String key, Object value, double scoure);

    /**
     * 获取分数最小值和最大值之间的 value
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    Set<Object> rangeByScore(String key, double min, double max);


    /**
     * 通过分数返回有序集合指定区间内的成员(min,max)，
     * 并在索引范围内(offset,count)，
     * 其中有序集成员按分数值递增(从小到大)顺序排列
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    Set<Object> rangeByScore(String key, double min, double max,long offset,long count);

    /**
     * 获取分数最小值和最大值之间的 value和分数（score）
     * @param key
     * @param min
     * @param max
     * @return
     */
    Map<Object,Double> rangeByScoreWithScores(String key, double min, double max);

    /**
     * 通过分数返回有序集合指定区间内的成员(min,max)，
     * 并在索引范围内(offset,count)，
     * 其中有序集成员按分数值递增(从小到大)顺序排列
     * 获取value，score值
     * @param key
     * @param min
     * @param max
     * @return
     */
    Map<Object,Double> rangeByScoreWithScores(String key, double min, double max,long offset,long count);

    /**
     * 海量数据 添加（排重）
     * 用户 pv uv统计
     * @param key
     * @param value
     * @return
     */
    long hyperLogLogAdd(String key, Object ... value );


    /**
     * 海量数据统计数量
     * @param key
     * @return
     */
    long hyperLogLogSize(String key );


    /**
     * 支持多个key海量插叙
     * 会出现的问题：All keys must map to same slot for pfcount in cluster mode
     * 原因：多个key在不同的hash槽中
     * @param key
     * @return
     */
    long hyperLogLogSize(String ... key);


    /**
     * 将多个key中的值合并到另外一个key中
     * 返回新key（桶）的个数
     *
     * 会出现的问题：All keys must map to same slot for pfcount in cluster mode
     * 原因：多个key在不同的hash槽中
     * @param newKey
     * @param key
     * @return
     */
    long hyperLogLogMerge(String newKey,String ... key);
}
