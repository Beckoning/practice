package com.pay.payment.center.suanfa.shuzu;

/**
 * 获取一个先增后减数组的最大值
 * {1,2,3,4,5，6，7,8,9 2，4,5,6}
 * 解题思路：最大的值 既大于左边的数据，又大于右边数据
 */
public class shuzuzuidazhi {


    public static void main(String[] args) {
        int [] nums = {1,2,3,4,5,6,7,8,9 ,2,4,5,6};
        System.out.println(findPeakElement(nums));
    }

    public static int findPeakElement(int[] nums) {
        return findPeakElement(nums,0,nums.length-1);
    }

    public static int findPeakElement(int[] nums,int start,int end){
        int mid = (start+end)/2;
        int leftvalue = nums[mid-1];
        int midValue = nums[mid];
        int rightValue = nums[mid+1];
        //找到数据
        if(leftvalue < midValue && midValue>rightValue){
            return midValue;
        }else if(leftvalue>midValue){//去左半区域查找
            return findPeakElement(nums,start,mid-1);
        }else if(midValue<rightValue){//去右半区域查找
            return findPeakElement(nums,mid+1,end);
        }else{
            return -1;
        }


    }

}
