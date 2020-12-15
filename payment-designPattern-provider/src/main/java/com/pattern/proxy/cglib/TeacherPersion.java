package com.pattern.proxy.cglib;

import com.pattern.proxy.jdk.Persion;

public class TeacherPersion implements Persion {
    @Override
    public void say() {
        System.out.println("上课");
    }
}
