package com.pattern.observer;

public class ObserverManager {


    public static void main(String[] args) {
        PostSubject postSubject = new PostSubject();
        postSubject.addListener(new DkPayService());
        postSubject.addListener(new FastPayService());
        postSubject.addListener(new WgPayService());
        postSubject.publish("zoula");

    }
}
