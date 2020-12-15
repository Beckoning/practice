package com.pattern.observer;

import java.util.Observable;
import java.util.Observer;

public class DkPayService implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("DkPayService"+arg);
    }
}
