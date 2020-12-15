package com.pay.payment.redis.center.service;

import com.pay.payment.redis.center.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 排名实现类
 */
@Slf4j
@Service
public class RankServiceImpl implements RankService{


    @Resource
    private RedisService redisService;


    public void setRank( double avatarId,  int points) {
        long updateTime = System.currentTimeMillis();

        double timeRank = points + 1 - updateTime / Math.pow(10, (int) Math.log10(updateTime) + 1);

        redisService.zSet(Constant.RANK_TIME, timeRank, avatarId);

        log.info("updateWanxianzhePoints avatarId=", avatarId, "	points=", points);
    }
}

