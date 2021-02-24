package com.pay.payment.center.current;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ThreadLocalTest {
    public final static ThreadLocal<String> threadLocal = new ThreadLocal<String>();
    public final static ThreadLocal<String> thread = new ThreadLocal<String>();

    public static void main(String[] args)throws Exception {
        threadLocal.set("2323");
        thread.set("2424");

        new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                threadLocal.set("w323");
                thread.set("2525");

                return "";
            }
        }).run();

    }

}
