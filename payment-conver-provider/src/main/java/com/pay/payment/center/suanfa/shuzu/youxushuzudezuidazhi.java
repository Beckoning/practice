package com.pay.payment.center.suanfa.shuzu;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 一个数组里有多个递增数据，查找某一个递增数据的和的最大值
 */
public class youxushuzudezuidazhi {


    public static void main(String[] args) {


        int [] nums = {1,2,5,4,2,2,2,9};
        System.out.println(JSON.toJSON(aa(nums)));
        System.out.println(JSON.toJSON(addNums(nums)));
        new ConcurrentHashMap<>().size();
    }


    public static int addNums(int [] nums){

        int temp = 0;
        int temp1 = 0;
        for(int i = 0;i<nums.length;i++){
            if( i<nums.length-1 && nums[i]<=nums[i+1] ){
                temp1 += nums[i];
            }else {
                temp1 += nums[i];
                if (temp < temp1) {
                    temp = temp1;
                }
                temp1 = 0;
                continue;
            }
        }
        return temp;

    }


    public static int[] aa(int[] array) {
        // 下标  长度 // 结果
        int[] store = new int[]{-1, -1, -1};
        //左边下标
        int lIndex = 0;
        //右边下标
        int rIndex = 0;
        //临时变量
        int tmp = 0;
        while (rIndex < array.length) {
            tmp += array[rIndex];
            if (rIndex < array.length - 1 && array[rIndex + 1] >= array[rIndex]) {
                rIndex++;
            } else {
                if (tmp > store[2]) {
                    store[0] = lIndex;
                    store[1] = rIndex - lIndex + 1;
                    store[2] = tmp;
                }
                lIndex = ++rIndex;
                tmp = 0;
            }
        }
        int[] newArray = new int[store[1]];
        System.arraycopy(array, store[0], newArray, 0, store[1]);
        return newArray;
    }
}
