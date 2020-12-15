package com.pay.payment.center.suanfa.knn;

import java.util.ArrayList;
import java.util.List;


/**
 *  K最近邻算法
 *
 *  KNN算法三要素
 *  1、要素是k值的选取，
 *  2、距离度量的方式
 *  3、分类决策规则
 */
public class KNNTest {

    public static void main(String[] args) {
        
        List<KNNData> kd = new ArrayList<KNNData>();
        //训练集
        kd.add(new KNNData(1.2,1.1,0.1,"A"));
        kd.add(new KNNData(1.2,1.1,0.1,"A"));
        kd.add(new KNNData(7,1.5,0.1,"B"));
        kd.add(new KNNData(6,1.2,0.1,"B"));
        kd.add(new KNNData(2,2.6,0.1,"C"));
        kd.add(new KNNData(2,2.6,0.1,"C"));
        kd.add(new KNNData(2,2.6,0.1,"C"));
        kd.add(new KNNData(100,1.1,0.1,"D"));

        System.out.println(KNN.knnCal(3, new KNNData(1.1,1.1,0.1,"N/A"), kd));
    }
}