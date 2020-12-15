package com.pay.payment.redis.center.service;

/**
 * 排名开发
 */
public interface RankService {

    /**
     * 设置排名
     * @param avatarId
     * @param points
     */
    void setRank( double avatarId,  int points);
}
