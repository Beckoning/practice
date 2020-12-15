package com.pay.payment.center.suanfa;

import com.alibaba.fastjson.JSON;

public class MergeSort {


    public static void main(String[] args) {

        int[] arr = { 49, 38, 97, 65, 23, 22, 21, 1, 5, 8, 2, 0, -1, 22 };
        int[] temp = new int[14];
        System.out.println(JSON.toJSONString(arr)+"1212121212121");
        merge_sort(arr,0,13,temp);
//        System.out.println(JSON.toJSON(temp));
//        System.out.println(JSON.toJSON(arr));

    }

    public static void merge_sort(int a[],int first,int last,int temp[]){

        if(first < last){
            int middle = (first + last)/2;
            merge_sort(a,first,middle,temp);//左半部分排好序
            merge_sort(a,middle+1,last,temp);//右半部分排好序
            mergeArray(a,first,middle,last,temp); //合并左右部分
        }
    }

    //合并 ：将两个序列a[first-middle],a[middle+1-end]合并
    public static void mergeArray(int a[],int first,int middle,int end,int temp[]){
        int i = first;
        int m = middle;
        int j = middle+1;
        int n = end;
        int k = 0;
        while(i<=m && j<=n){
            if(a[i] <= a[j]){
                temp[k] = a[i];
                k++;
                i++;
            }else{
                temp[k] = a[j];
                k++;
                j++;
            }
        }
        while(i<=m){
            temp[k] = a[i];
            k++;
            i++;
        }
        while(j<=n){
            temp[k] = a[j];
            k++;
            j++;
        }

        for(int ii=0;ii<k;ii++){
            a[first + ii] = temp[ii];
        }
        System.out.println(JSON.toJSONString(a));
    }
}
