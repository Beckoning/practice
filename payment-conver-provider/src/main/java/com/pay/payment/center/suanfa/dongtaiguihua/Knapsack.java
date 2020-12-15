package com.pay.payment.center.suanfa.dongtaiguihua;

/**
 0/1背包问题的动态规划法求解，前人之述备矣，这里所做的工作，不过是自己根据理解实现了一遍，主要目的还是锻炼思维和编程能力，同时，也是为了增进对动态规划法机制的理解和掌握。
 
值得提及的一个问题是，在用 JAVA 实现时， 是按算法模型建模，还是用对象模型建模呢？
 如果用算法模型，那么 背包的值、重量就直接存入二个数组里；如果用对象模型，则要对背包以及背包问题进行对象建模。
 思来想去，还是采用了对象模型，尽管心里感觉算法模型似乎更好一些。有时确实就是这样，对象模型虽然现在很主流，但也不是万能的，采用其它的模型和视角，或许可以得到更好的解法。
 
背包建模：
Java代码*/
 
public class Knapsack {

    /** 背包重量  */
    private int weight;

    /** 背包物品价值  */
    private int value;
    /***
     * 构造器
     */
    public Knapsack(int weight, int value) {
        this.value = value;
        this.weight = weight;
    }
    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return "[weight: " + weight + " " + "value: " + value + "]";
    }
}