package com.pay.payment.center.suanfa.lianbiao;

import java.util.List;
import java.util.Objects;

/**
 * 链表每n个节点反转
 */
public class meingejiedianfanzhuan {


    public static ListNode reverseKGroup (ListNode head, int k) {
        if(head==null || head.next==null || k<2){
            return head;
        }

        ListNode hh = new ListNode(0);
        hh.next = head; //头节点的前一个结点
        int len=0;

        //计算链表的长度
        while(head!=null){
            len++;
            head = head.next;
        }

        //将链表进行翻转
        //pre:当前组头节点的前一个结点；first当前组翻转前的头节点；
        ListNode pre = hh;
        ListNode first = hh.next;
        ListNode temp;
        for(int i=0;i<len/k;i++){//对每一组进行翻转
            for(int j=1;j<k;j++){//对当前组进行翻转（不考虑翻转前的头节点）
                //例：对于 4567 1234 8546 k=4
                //1)交换第二组 1234 -> 2 134 -> 32 14 -> 432 1;
                //其中pre = 7 即当前组的前一个结点；first = 1
                //第二组交换介绍后，此时所有元素序列为：4567 4321 8546
                //2)那么交换第三组时，pre = first = 1;  first = pre.next = 1.next=8
                temp = first.next;//获取翻转节点；
                first.next = temp.next;//修改翻转节点的前一个结点的指针,指向翻转节点的下一个节点
                temp.next = pre.next;//将翻转结点插入到第一个节点位置
                pre.next = temp;

            }
            pre = first;//修改下一组的pre节点
            first = pre.next;//修改下一组翻转前的第一个节点
        }
        return hh.next;
    }


    /**
     * 通过循环实现
     * 练习
     * @param head
     * @param k
     * @return
     */
    public static ListNode reverseKGroup1 (ListNode head, int k) {

       if(Objects.isNull(head) || Objects.isNull(head.next) || k<2){
           return head;
       }

       int len = 0;
       ListNode hh = new ListNode(0);
       hh.next = head;
       while(Objects.nonNull(head)){
           len ++;
           head = head.next;
       }

       ListNode pre = hh;
       ListNode first = hh.next;
       ListNode temp ;

       for(int i=0;i<len/k;i++){
           for (int j=1;j<k;j++){
               temp = first.next;
               first.next = temp.next;
               temp.next = pre.next;
               pre.next = temp;
           }

           pre = first;
           first = pre.next;
       }

      return hh.next;

    }



    static class ListNode{

        int value;
        ListNode next ;

        public ListNode(int value) {
            this.value = value;
        }
    }


    public static void main(String[] args) {

        ListNode list1 = new ListNode(1);
        ListNode list2 = new ListNode(2);
        ListNode list3 = new ListNode(3);
        ListNode list4 = new ListNode(4);
        ListNode list5 = new ListNode(5);
        ListNode list6 = new ListNode(6);
        ListNode list7 = new ListNode(7);

        list1.next = list2;
        list2.next = list3;
        list3.next = list4;
        list4.next = list5;
        list5.next = list6;
        list6.next = list7;


        ListNode listNode = list1;
        while (Objects.nonNull(listNode)){
            System.out.print(listNode.value + " ");
            listNode = listNode.next;
        }

        System.out.println();

        listNode =  reverseKGroup3(list1,4);
        while (Objects.nonNull(listNode)){
            System.out.print(listNode.value +" ");
            listNode = listNode.next;
        }


    }

    /**
     * 通过递归实现
     *
     * @param head
     * @param k
     * @return
     */
    public static  ListNode reverseKGroup2(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode tail = head;
        for (int i = 0; i < k; i++) {
            //剩余数量小于k的话，则不需要反转。
            if (tail == null) {
                return head;
            }
            tail = tail.next;
        }
        // 反转前 k 个元素
        ListNode newHead = reverse(head, tail);
        //下一轮的开始的地方就是tail
        head.next = reverseKGroup(tail, k);

        return newHead;
    }

//    /**
//     * 小区间的链表反转
//     * @param head
//     * @param tail
//     * @return
//     */
//    private static ListNode reverse(ListNode head, ListNode tail) {
//        ListNode pre = null;
//        ListNode next = null;
//        while (head != tail) {
//            next = head.next;
//            head.next = pre;
//            pre = head;
//            head = next;
//        }
//        return pre;
//    }

    /** 反转区间 [a, b) 的元素，注意是左闭右开 */
    static ListNode  reverse(ListNode a, ListNode b) {
        ListNode pre, cur, nxt;
        pre = null; cur = a;
        // while 终止的条件改一下就行了
        while (cur != b) {
            nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }
        // 返回反转后的头结点
        return pre;
    }

    /**
     * 练习
     * @param head
     * @param k
     * @return
     */
    public static  ListNode reverseKGroup3(ListNode head, int k) {

        if(Objects.isNull(head) || Objects.isNull(head.next) || k<2){
            return head;
        }

        ListNode tail = head;
        for(int i=0;i<k;i++){
            if(Objects.isNull(tail)){
                return head;
            }
            tail = tail.next;
        }

        ListNode newHead = reverse3(head,tail);
        head.next = reverseKGroup3(tail,k);

        return newHead;
    }

    private static ListNode reverse3(ListNode head, ListNode tail) {

        ListNode cur = head;
        ListNode first = null;
        ListNode temp ;
        while(cur != tail){
            temp = cur.next;
            cur.next = first;
            first = cur;
            cur = temp;
        }
        return first;
    }


}
