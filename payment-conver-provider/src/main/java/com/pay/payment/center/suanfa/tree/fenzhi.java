package com.pay.payment.center.suanfa.tree;

import com.alibaba.fastjson.JSON;

public class fenzhi {

    public static void main(String[] args) {
        int[] arr = {1 , 38, 65, 97, 23, 22, 76, 1, 5, 8, 2, 0, -1, 22 };
        fenzhi(arr, 0, arr.length - 1);
        System.out.println("排序后:");
//		for (int i : arr) {
//			System.out.println(i);
//		}
    }

    private static void fenzhi(int[] arr, int low, int high) {

        if (low < high) {
            int index = getIndex(arr, low, high);

            fenzhi(arr, low, index - 1);
            fenzhi(arr, index + 1, high);
        }

    }

    private static int getIndex(int[] arr, int low, int high) {
        // 基准数据
        int tmp = arr[low];
        while (low < high) {
            // 当队尾的元素大于等于基准数据时,向前挪动high指针
            while (low < high && arr[high] >= tmp) {
                high--;
            }
            // 如果队尾元素小于tmp了,需要将其赋值给low
            arr[low] = arr[high];
            // 当队首元素小于等于tmp时,向前挪动low指针
            while (low < high && arr[low] <= tmp) {
                low++;
            }
            // 当队首元素大于tmp时,需要将其赋值给high
            arr[high] = arr[low];

        }
        // 跳出循环时low和high相等,此时的low或high就是tmp的正确索引位置
        // 由原理部分可以很清楚的知道low位置的值并不是tmp,所以需要将tmp赋值给arr[low]
        arr[low] = tmp;
        System.out.println(JSON.toJSONString(arr) + "_" +low);
        return low; // 返回tmp的正确位置
    }
}
