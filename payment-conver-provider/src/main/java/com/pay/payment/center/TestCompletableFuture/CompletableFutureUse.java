package com.pay.payment.center.TestCompletableFuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureUse {


   static  ExecutorService threadPool = Executors.newFixedThreadPool(10);

    /**
     * 总结
     *      不带Async方法异步任务间使用相同线程
     *      带额外的Executor参数的方法使用的是指定线程池执行任务,不带额外的Executor参数的方法就是用自己的线程执行
     *      run开头方法没有最终异步任务返回值, 也无法获取到上级任务结果
     *      acept开头方法没有最终异步任务返回值, 但是获取到上级任务结果
     *      apply开头方法 有最终异步任务返回值, 也获取到上级任务结果
     *
     *
     *
     *
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args)throws Exception {



        //异步执行
//        testAsyncTask();
        //任务执行完的回调且带有返回值
        testhandler();
        //线程串行化- 任务串行执行
//        threadSerialization();
        //任务组合（任务都完成才会调用）
//        taskMix();
        //任务组合（一个任务执行完就会调用）
//        taskMixOne();
//        taskMixAll();



//        Thread.currentThread().join();
    }

    final static int TASK_COUNT = 5;

    /**
     * 多个任务都执行完/然后干一件事
     * 多个任务只要有一个执行完/然后干一件事
     * @throws Exception
     */
    private static void taskMixAll()throws Exception {
        // 初始化5个异步任务
        CompletableFuture<String>[] taskArr = new CompletableFuture[TASK_COUNT];
        for (int i = 0; i < TASK_COUNT; i++) {
            int a = i;
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                System.out.println("任务"+a+"开始 [ " + Thread.currentThread().getName());
                try {
                    Thread.sleep(new Random().nextInt(5)*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务"+a+"结束 [ " + Thread.currentThread().getName());
                return "任务"+a;
            });
            taskArr[i] = future;
        }

        // 1 - allOf  -
//         CompletableFuture<Void> allOf = CompletableFuture.allOf(taskArr);
//         Void result = allOf.get(); // 阻塞等待所有任务结束,返回值为空
//         System.out.println("结果为: "+result);


        // 2- anyOf
//        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(taskArr);
//        Object result = anyOf.get(); // 阻塞等待任意一个任务结束
//        System.out.println("结果为: "+result); // 返回那一个先结束任务的结果

        // 依次获得其他任务结果
        for (int i = 0; i < TASK_COUNT; i++) {
            System.out.println("任务"+i+"的结果为: "+taskArr[i].get());
        }
    }

    /**
     * 一个任务执行完 然后触发自定义任务
     *
     * runAfterEither ：无返回值 无法获取上级任务的结果
     * acceptEitherAsync：无返回值，可以获取到上级任务的结果
     * applyToEitherAsync 有返回值，可以获取到上级任务的结果
     */
    private static void taskMixOne() throws Exception{
        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务A开始 [ " + Thread.currentThread().getName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务A结束 [ " + Thread.currentThread().getName());
            return "A---->";
        }, threadPool);

        CompletableFuture<String> futureB = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务B开始 [ " + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务B结束 [ " + Thread.currentThread().getName());
            return "Sb";
        }, threadPool);

       /* CompletableFuture<Void> future = futureA.runAfterEither(futureB, () -> {
            System.out.println("任务A或者B完成了 [ " + Thread.currentThread().getName());
        });*/

      /*  CompletableFuture<Void> future = futureA.acceptEitherAsync(futureB, (result) -> {
            System.out.println("任务A或者B完成了 [ " + Thread.currentThread().getName());
            System.out.println("任务A或者B的结果为:  [ " + result);
        });*/

        CompletableFuture<Object> future = futureA.applyToEitherAsync(futureB, (result) -> {
            System.out.println("任务A或者B完成了 [ " + Thread.currentThread().getName());
            System.out.println("任务A或者B的结果为:  " + result);
            return result + "******";
        }, threadPool);

        System.out.println(future.get());
    }


    /**
     * 任务组合（都要完成后才会调用）
     *  组合任务都完成后触发自定义任务
     *  runAfterBoth、runAfterBothAsync   组合两个future 最终不返回future的结果  runAfterBoth（同步）、runAfterBothAsync（异步）
     *  thenCombine 、  thenCombineAsync  可以获取到两个任务future的结果，有返回值  thenCombine（同步） 、  thenCombineAsync（异步）
     *  thenAcceptBoth 、thenAcceptBothAsync  可以获取到两个任务future的结果，无返回值 thenAcceptBoth（同步执行） 、thenAcceptBothAsync（异步执行）
     *
     * @throws Exception
     */
    private static void taskMix() throws Exception{
        CompletableFuture<Integer> futureA = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务A开始 [ " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务A结束 [ " + Thread.currentThread().getName());
            return 520;
        }, threadPool);

        CompletableFuture<String> futureB = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务B开始 [ " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务B结束 [ " + Thread.currentThread().getName());
            return "Sb";
        }, threadPool);


        // 2-thenAcceptBothAsync    -回调可拿到任务AB的结果

//        CompletableFuture<Void> future =  futureA.thenAcceptBothAsync(futureB,(resultA,resultB)->{
//            System.out.println("任务AB都完成了 [ " + Thread.currentThread().getName());
//            System.out.println("任务A结果为: "+resultA+"、任务B结果为： "+ resultB);
//        },threadPool);
//
//        System.out.println(future.get());

//        CompletableFuture<Void> future = futureA.thenAcceptBoth(futureB,(resultA,resultB)->{
//            // 示例不带Async 用的是 A 或者 B 的线程
//            System.out.println("任务AB都完成了 [ " + Thread.currentThread().getName());
//            System.out.println("任务A结果为: "+resultA+"、任务B结果为： "+ resultB);
//        });
//        System.out.println(future.get());



        // 1-runAfterBothAsync
//        CompletableFuture<Void> future  = futureA.runAfterBothAsync(futureB,() -> {
//            System.out.println("任务AB都完成了 [ " + Thread.currentThread().getName());
//        },threadPool);
//        CompletableFuture<Void> future =  futureA.runAfterBoth(futureB,()->{
//            System.out.println("任务AB都完成了 [ " + Thread.currentThread().getName());
//
//        });






//        // 3-thenCombineAsync    -最终异步任务有返回结果
//        CompletableFuture<String> future = futureA.thenCombineAsync(futureB, (resultA, resultB) -> {
//            System.out.println("任务AB都完成了 [ " + Thread.currentThread().getName());
//            System.out.println("任务A结果为: " + resultA + "、任务B结果为： " + resultB);
//            return resultA + "------>" + resultB;
//        }, threadPool);
//        System.out.println(future.get());

        CompletableFuture<String> future = futureA.thenCombine(futureB, (resultA, resultB) -> {
            System.out.println("任务AB都完成了 [ " + Thread.currentThread().getName());
            System.out.println("任务A结果为: " + resultA + "、任务B结果为： " + resultB);
            return resultA + "------>" + resultB;
        });
        System.out.println(future.get());

    }

    /**
     *
     * 任务串行化，上一任务执行完 后一个任务才能执行
     *
     * thenRun 、thenRunAsync  任务A和和任务B之间没关系（即任务B拿不到任务A的返回结果），最终不返回任务结果（即 future.get() 没有返回值）
     * thenAccept 、thenAcceptAsync  任务B可以拿到任务A的结果，最终不返回任务结果
     * thenApply、thenApplyAsync 任务B可以拿到任务A的结果， 最终返回任务结果
     *
     * @throws Exception
     */
    private static void threadSerialization() throws Exception{
        System.out.println("1");
        // 注意： 最后执行完future.get()没有返回值
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务A开始 [ " + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务A结束 [ " + Thread.currentThread().getName());
            return "Sb";
        }, threadPool).thenRun(() -> {
            System.out.println("任务B开始结束 [ " + Thread.currentThread().getName());
        }).thenRun(() -> {
            System.out.println("任务C开始结束 [ " + Thread.currentThread().getName());
        }).thenRun(() -> {
            System.out.println("任务D开始结束 [ " + Thread.currentThread().getName());
        }).thenRun(() -> {
            System.out.println("任务E开始结束 [ " + Thread.currentThread().getName());
        });

//        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("任务A开始 [ " + Thread.currentThread().getName());
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("任务A结束 [ " + Thread.currentThread().getName());
//            return "Sb";
//        }, threadPool).thenAcceptAsync((result) -> {
//            System.out.println("任务B开始 [ " + Thread.currentThread().getName());
//            System.out.println("任务A的结果为:" + result);
//            System.out.println("任务B结束 [ " + Thread.currentThread().getName());
//        }).thenAcceptAsync((result) -> {
//            System.out.println("任务B的结果为:" + result);
//            System.out.println("任务C开始结束 [ " + Thread.currentThread().getName());
//        }).thenAcceptAsync((result) -> {
//            System.out.println("任务C的结果为:" + result);
//            System.out.println("任务D开始结束 [ " + Thread.currentThread().getName());
//        }).thenAcceptAsync((result) -> {
//            System.out.println("任务D的结果为:" + result);
//            System.out.println("任务E开始结束 [ " + Thread.currentThread().getName());
//        });

//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("任务A开始 [ " + Thread.currentThread().getName());
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("任务A结束 [ " + Thread.currentThread().getName());
//            return "Sb";
//        }, threadPool).thenApply((result) -> {
//            System.out.println("任务B开始 [ " + Thread.currentThread().getName());
//            System.out.println("任务A的结果为:" + result);
//            System.out.println("任务B结束 [ " + Thread.currentThread().getName());
//            return result + "B is you ? ";
//        }).thenApply((result) -> {
//            System.out.println("任务B的结果为:" + result);
//            System.out.println("任务C开始结束 [ " + Thread.currentThread().getName());
//            return result + "C is you ? ";
//        }).thenApply((result) -> {
//            System.out.println("任务C的结果为:" + result);
//            System.out.println("任务D开始结束 [ " + Thread.currentThread().getName());
//            return result + "D is you ? ";
//
//        }).thenApply((result) -> {
//            System.out.println("任务D的结果为:" + result);
//            System.out.println("任务E开始结束 [ " + Thread.currentThread().getName());
//            return result + "E is you ? ";
//
//        });
//
//        System.out.println(future.get());
//        System.out.println("3");
    }

    /**
     * 任务执行完的回调，相当于
     * handle 、handleAsync 执行任务完进行回调、 有返回值        handle 同步  handleAsync 异步
     * whenComplete whenCompleteAsync 执行任务完进行回调  无返回值    whenComplete 同步  whenCompleteAsync 异步
     *
     *
     *
     * @throws Exception
     */
    private static void testhandler()throws Exception {
        System.out.println("1");
        //任务执行完回调   有返回值 -同步
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("开始 [ " + Thread.currentThread().getName());
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("结束 [ " + Thread.currentThread().getName());
//            return "Sb";
//        }, threadPool).handle((result,exception)->{
//            System.out.println("异步任务结束触发回调 [ " + Thread.currentThread().getName());
//            return result +" is you !"; //将任务结果重新计算返回
//        });



        //任务执行完回调  无返回值-异步
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("开始 [ " + Thread.currentThread().getName());
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("结束 [ " + Thread.currentThread().getName());
//            return "Sb";
//        }, threadPool).handleAsync((result,exception)->{
//            System.out.println("异步任务结束触发回调 [ " + Thread.currentThread().getName());
//
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return result +" is you !"; //将任务结果重新计算返回
//        });
//

        //任务执行完回调 无返回值  异步
        //执行线程：如果不使用线程池线程为ForkJoinPool.commonPool中的线程（JDK）
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("开始 [ " + Thread.currentThread().getName());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
////            int i = 10 / 0;
//            System.out.println("结束 [ " + Thread.currentThread().getName());
//            return "Sb";
//        }, threadPool).whenCompleteAsync((result,exception)-> {
//            System.out.println("异步任务结束触发回调 [ " + Thread.currentThread().getName());
//            System.out.println("上一级任务结果为： " + result);
//            System.out.println("上一级任务异常为: " + exception);
//        }).exceptionally((exception) -> {return "SB失败";});


        //当方法执行成功后回调，同步 回调的线程为执行任务的线程
        //任务执行完回调  无返回值  同步
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("开始 [ " + Thread.currentThread().getName());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
////            int i = 10 / 0;
//            System.out.println("结束 [ " + Thread.currentThread().getName());
//            return "Sb";
//        }, threadPool).whenComplete((result,exception)-> {
//            System.out.println("异步任务结束触发回调 [ " + Thread.currentThread().getName());
//            System.out.println("上一级任务结果为： " + result);
//            System.out.println("上一级任务异常为: " + exception);
//        }).exceptionally((exception) -> {return "SB失败";});



        //使用CompletableFuture设置返回值，同步
        CompletableFuture completableFuture = CompletableFuture.completedFuture("value");
        completableFuture.whenComplete((result,exception)-> {
                    try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务结束触发回调 [ " + Thread.currentThread().getName());
            System.out.println("上一级任务结果为： " + result);
            System.out.println("上一级任务异常为: " + exception);
        }).exceptionally((exception) -> {return "SB失败";});
        System.out.println("4");
        System.out.println("结束++++++++++ [ " + Thread.currentThread().getName());

//        String s = future.get();
//        System.out.println("---------"+s);
        System.out.println("3");
    }


    /**
     *
     */



    /**
     * 执行异步任务
     *
     * runAsync 、supplyAsync 都是异步执行任务
     * runAsync区别是supplyAsync可以有返回值，通过future.get();获取  但是该方法是阻塞方法，会导调用线程阻塞
     */
    private static void testAsyncTask()throws Exception {

        System.out.println("1");
        CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
            System.out.println("开始 [ "+Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("结束 [ "+Thread.currentThread().getName());
        },threadPool);


        //如果传递线程池，执行方法的时候就使用线程池中的线程/否者使用ForkJoinPool.commonPool中的线程
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("开始 [ " + Thread.currentThread().getName());
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("结束 [ " + Thread.currentThread().getName());
//            return "[1,2,3,4]";
//        }, threadPool);


        System.out.println("2");

        // 阻塞等待异步任务的结果返回
        System.out.println(future.get());

        System.out.println("3");
    }
}
