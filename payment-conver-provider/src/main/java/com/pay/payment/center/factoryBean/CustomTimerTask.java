package com.pay.payment.center.factoryBean;


import lombok.Data;

@Data
public class CustomTimerTask {

    private String corn;

    private ProcessorService processorService;

    public CustomTimerTask() {
    }

    public CustomTimerTask(ProcessorService processorService) {
        this.processorService = processorService;
    }

    public void execute() {
        //do something
        processorService.doTask();
    }
}
