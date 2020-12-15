package com.pay.payment.redis.center.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 缓存基本操作类
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            redisTemplate.opsForGeo();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     *
     * 根据key 重偏移位置覆盖value(方法不好用)
     *
     * @param key key值
     * @param value 值
     * @param offset 偏移位置
     * @return
     */
    public boolean setOverwriteValue(String key, Object value,long offset){
        boolean result = false;
        try{

            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key,value,offset);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 判断key值是否存在，如果存在设置新值
     *
     * @param key
     * @param value
     * @return
     */
    public boolean getAndSet(String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.getAndSet(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 如果存在设置新value
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setIfAbsent(String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            return operations.setIfAbsent(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean setBit(String key, long offset,boolean status) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            return operations.setBit(key, offset,status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    @Override
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    @Override
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    @Override
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    @Override
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    @Override
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    @Override
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    @Override
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    @Override
    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    @Override
    public void addSet(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    @Override
    public Set<Object> getSet(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }


    /**
     * 判断集合中知否存在某个值
     *
     * @param key
     * @param obj
     * @return
     */
    @Override
    public Boolean checkKeyAndValue(String key, Object obj) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.isMember(key, obj);
    }

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */
    @Override
    public void zSet(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, scoure);
    }

    /**
     * 获取分数最小值和最大值之间的 value
     *
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    @Override
    public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

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
    @Override
    public Set<Object> rangeByScore(String key, double min, double max,long offset,long count) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, min, max,offset,count);
    }


    /**
     * 获取分数最小值和最大值之间的 value和分数（score）
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Map<Object,Double> rangeByScoreWithScores(String key, double min, double max) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<Object>> objectSet = zset.rangeByScoreWithScores(key, min, max);
        Map<Object,Double> resMap = new HashMap<>();
        for (ZSetOperations.TypedTuple<Object> value : objectSet){

            resMap.put(value.getValue(),value.getScore());
        }
        return resMap;
    }


    public Map<Object,Double> rangeByScoreWithScores(String key, double min, double max,long offset,long count) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<Object>> objectSet = zset.rangeByScoreWithScores(key, min, max,offset,count);
        Map<Object,Double> resMap = new HashMap<>();
        for (ZSetOperations.TypedTuple<Object> value : objectSet){

            resMap.put(value.getValue(),value.getScore());
        }
        return resMap;
    }


    /**
     *
     * @MethodName：cacheGeo
     * @param key
     * @param x
     * @param y
     * @param member
     * @param time(单位秒)  <=0 不过期
     * @return
     * @ReturnType：boolean
     * @Description：缓存地理位置信息
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:30:23
     * @Modifier：
     * @ModifyTime：
     */
    public boolean cacheGeo(String key, double x, double y, String member, long time) {
        try {
            GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
            geoOps.geoAdd(key, new Point(x, y) , member);
            if (time > 0) redisTemplate.expire(key, time, TimeUnit.SECONDS);
        } catch (Throwable t) {
            log.error("缓存[" + key +"]" + "失败, point["+ x + "," +
                    y + "], member[" + member + "]" +", error[" + t + "]");
        }
        return true;
    }

    /**
     *
     * @MethodName：cacheGeo
     * @param key
     * @param locations
     * @param time(单位秒)  <=0 不过期
     * @return
     * @ReturnType：boolean
     * @Description：
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:31:33
     * @Modifier：
     * @ModifyTime：
     */
    public boolean cacheGeo(String key, Iterable<RedisGeoCommands.GeoLocation<String>> locations, long time) {
        try {
            for (RedisGeoCommands.GeoLocation<String> location : locations) {
                cacheGeo(key, location.getPoint().getX(), location.getPoint().getY(), location.getName(), time);
            }
        } catch (Throwable t) {
            log.error("缓存[" + key + "]" + "失败" +", error[" + t + "]");
        }
        return true;
    }

    /**
     *
     * @MethodName：removeGeo
     * @param key
     * @param members
     * @return
     * @ReturnType：boolean
     * @Description：移除地理位置信息
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午10:53:01
     * @Modifier：
     * @ModifyTime：
     */
    public boolean removeGeo(String key, String...members) {
        try {
            GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
            geoOps.geoRemove(key, members);
        } catch (Throwable t) {
            log.error("移除[" + key +"]" + "失败" +", error[" + t + "]");
        }
        return true;
    }

    /**
     *
     * @MethodName：distanceGeo
     * @param key
     * @param member1
     * @param member2
     * @return Distance
     * @ReturnType：Distance
     * @Description：根据两个成员计算两个成员之间距离
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午10:58:33
     * @Modifier：
     * @ModifyTime：
     */
    public Distance distanceGeo(String key, String member1, String member2) {
        try {
            GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
            return geoOps.distance(key, member1, member2);
        } catch (Throwable t) {
            log.error("计算距离[" + key +"]" + "失败, member[" + member1 + "," + member2 +"], error[" + t + "]");
        }
        return null;
    }

    /**
     *
     * @MethodName：getGeo
     * @param key
     * @param members
     * @return
     * @ReturnType：List<Point>
     * @Description：根据key和member获取这些member的坐标信息
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:02:01
     * @Modifier：
     * @ModifyTime：
     */
    public List<Point> getGeo(String key, String...members) {
        try {
            GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
            return geoOps.geoPos(key, members);
        } catch (Throwable t) {
            log.error("获取坐标[" + key +"]" + "失败]" + ", error[" + t + "]");
        }
        return null;
    }

    /**
     *
     * @MethodName：radiusGeo
     * @param key
     * @param x
     * @param y
     * @param distance km
     * @return
     * @ReturnType：GeoResults<GeoLocation<String>>
     * @Description：通过给定的坐标和距离(km)获取范围类其它的坐标信息
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:09:10
     * @Modifier：
     * @ModifyTime：
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> radiusGeo(String key, double x, double y, double distance, Sort.Direction direction, long limit) {
        try {
            GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();

            //设置geo查询参数
            RedisGeoCommands.GeoRadiusCommandArgs geoRadiusArgs = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs();
            geoRadiusArgs = geoRadiusArgs.includeCoordinates().includeDistance();//查询返回结果包括距离和坐标
            if (Sort.Direction.ASC.equals(direction)) {//按查询出的坐标距离中心坐标的距离进行排序
                geoRadiusArgs.sortAscending();
            } else if (Sort.Direction.DESC.equals(direction)) {
                geoRadiusArgs.sortDescending();
            }
            geoRadiusArgs.limit(limit);//限制查询数量
            GeoResults<RedisGeoCommands.GeoLocation<String>> radiusGeo = geoOps.geoRadius(key, new Circle(new Point(x, y), new Distance(distance, RedisGeoCommands.DistanceUnit.METERS)), geoRadiusArgs);

            return radiusGeo;
        } catch (Throwable t) {
            log.error("通过坐标[" + x + "," + y +"]获取范围[" + distance + "km的其它坐标失败]" + ", error[" + t + "]");
        }
        return null;
    }


    /**
     * 数据添加 排重
     * @param key
     * @param value
     * @return
     */
    @Override
    public long hyperLogLogAdd(String key, Object ... value ) {
        HyperLogLogOperations<String, Object> hyperLogLog = redisTemplate.opsForHyperLogLog();
        return  hyperLogLog.add(key,value);

    }

    /**
     * 海量数据统计数量
     * @param key
     * @return
     */
    @Override
    public long hyperLogLogSize(String key) {
        HyperLogLogOperations<String, Object> hyperLogLog = redisTemplate.opsForHyperLogLog();
        return hyperLogLog.size(key);
    }


    /**
     * 海量数据统计数量
     * @param key
     * @return
     */
    @Override
    public long hyperLogLogSize(String ... key) {
        HyperLogLogOperations<String, Object> hyperLogLog = redisTemplate.opsForHyperLogLog();
        return hyperLogLog.size(key);
    }


    /**
     * 将多个key中的值合并到另外一个key中
     * 返回新key（桶）的个数
     * @param newKey
     * @param key
     * @return
     */
    public long hyperLogLogMerge(String newKey,String ... key) {
        HyperLogLogOperations<String, Object> hyperLogLog = redisTemplate.opsForHyperLogLog();
        return hyperLogLog.union(newKey,key);
    }
}