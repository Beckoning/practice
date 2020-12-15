package com.pay.payment.center.factoryBean;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;

@Data
public class CustomFactoryBean implements FactoryBean<CustomTimerTask> {

    private String corn;

    private ProcessorService processorService;

    @Override
    public CustomTimerTask getObject() throws Exception {
        CustomTimerTask customTask = new CustomTimerTask(processorService);
        customTask.setCorn(corn);
        return customTask;
    }

    @Override
    public Class<?> getObjectType() {
        return CustomTimerTask.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
