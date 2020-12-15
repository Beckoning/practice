package com.pay.payment.center.dealMethod;

import java.util.Collection;

public abstract class AbstractTask {


    private final EntityProvider entityProvider;

    public AbstractTask(EntityProvider entityProvider) {
        this.entityProvider = entityProvider;
    }

    interface EntityProvider {
        // 获得所有的通道集合，需要心跳的通道数组
        Collection<Entity> getEntity();
    }
}
