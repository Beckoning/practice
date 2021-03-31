package com.pay.payment.center.suanfa.sort;

import com.alibaba.fastjson.JSON;

/**
 * 最小k个数
 * 给定一个数组，找出其中最小的K个数。
 * 例如数组元素是4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4。
 * 如果K>数组的长度，那么返回一个空的数组
 * <p>
 * <p>
 * 先排序然后或者k个数
 */
public class zuixiaokgeshu {


    public static void quickSort(int arr[], int low, int high) {

        if(low <high){
           int index =  getIndex(arr,low,high);

           quickSort(arr,0,index -1);
           quickSort(arr,index +1,high);

        }


    }

    private static int getIndex(int[] arr, int low, int high) {
        int temp = arr[low];
        while(low<high){
            //先排高位
            while(low<high && arr[high]>=temp){
                high --;
            }
            arr[low] = arr[high];

            //在排低位
            while (low<high && temp >= arr[low]){
                low ++;
            }
            arr[high] = arr[low];


        }
        arr[low] = temp;
        System.out.println(JSON.toJSONString(arr) + "_" +low);

        return low;
    }

    public static void main(String[] args) {
        int[] arr = { 49, 38, 65, 97, 23, 22, 76, 1, 5, 8, 2, 0, -1, 22 };
        quickSort(arr, 0, arr.length - 1);
        System.out.println("排序后:");
//		for (int i : arr) {
//			System.out.println(i);
//		}

    }



}
