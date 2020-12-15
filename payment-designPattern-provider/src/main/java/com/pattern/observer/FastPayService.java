package com.pattern.observer;

import java.util.Observable;
import java.util.Observer;

public class FastPayService implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("FastPayService"+arg);
    }
}
