package com.pattern.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class PostSubject extends Observable {

    List<Observer> observerList = new ArrayList<>();


    public void addListener(Observer sub) {
        observerList.add(sub);
    }

    public void removeListener(Observer sub) {
        if (observerList.contains(sub)) {
            observerList.remove(sub);
        }
    }

    /**
     * 发布消息
     */
    public void publish(String name) {
        for (Observer observer : observerList) {
            observer.update(this, name);
        }
    }


}
