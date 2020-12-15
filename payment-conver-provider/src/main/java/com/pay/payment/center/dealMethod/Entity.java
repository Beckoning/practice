package com.pay.payment.center.dealMethod;

import lombok.Data;

import java.util.Collections;

@Data
public class Entity {
    private String name;


    public Entity(String name) {
        this.name = name;
        startTask();
    }

    private void startTask() {
        AbstractTask.EntityProvider entityProvider = () -> Collections.singletonList(Entity.this);
        System.out.println(entityProvider.getEntity().toString());
    }


    public static void main(String[] args) {
        Entity entity = new Entity("232");
    }
}
