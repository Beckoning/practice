package com.pay.payment.center.suanfa.lianbiao;

import java.util.Objects;

/**
 * 链表反转
 * //递归实现
 */
public class LianBiaoReverse {

    //递归实现
    public static ListNode reverseList(ListNode head) {
//1
        if (head == null || head.next == null) {
            return head;
        }
//2
        ListNode p = reverseList(head.next);
//3
        head.next.next = head;
//4
        head.next = null;
//5
        return p;
    }


    /**
     * 练习递归(O(N))
     * @param head
     * @return
     */
    public static ListNode reverseList1(ListNode head) {

       if(Objects.isNull(head) || Objects.isNull(head.next)){
           return head;
       }

       ListNode p =  reverseList1(head.next);
       head.next.next = head;
       head.next = null;
       return p;

    }


    /**
     * 头插法（O(N)）
     * @param listNode
     * @return
     */
    public static ListNode reverseListByInsert(ListNode listNode){
        //定义一个带头节点的
        ListNode resultList = null;
        //循环节点
        ListNode p = listNode;
        while(p!= null){
            //保存插入点之后的数据
            ListNode tempList = p.next;
            p.next = resultList;
            resultList = p;
            p = tempList;
        }
        return resultList;
    }

    /**
     * 头插法
     * @param listNode
     * @return
     */
    public static ListNode reverseListByInsert1(ListNode listNode){

        ListNode head = null;
        ListNode p = listNode;
        while(Objects.nonNull(p)){
            ListNode temp = p.next;
            p.next = head;
            head = p;
            p = temp;
        }

        return head;

    }


    /**
     * 就地反转(O(N))
     * @param listNode
     * @return
     */
    public static ListNode reverseListByLocal(ListNode listNode){
        ListNode resultList = new ListNode(-1);
        resultList.next= listNode;
        ListNode p = listNode;
        ListNode pNext = p.next;
        while (pNext!=null){
            p.next = pNext.next;
            pNext.next = resultList.next;
            resultList.next = pNext;
            pNext=p.next;
        }
        return resultList.next;
    }



    static class ListNode{

        int value;
        ListNode next ;

        public ListNode(int
                                value) {
            this.value = value;
        }
    }


    public static void main(String[] args) {

        ListNode list1 = new ListNode(1);
        ListNode list2 = new ListNode(2);
        ListNode list3 = new ListNode(3);
        ListNode list4 = new ListNode(4);
        ListNode list5 = new ListNode(5);
        list1.next = list2;
        list2.next = list3;
        list3.next = list4;
        list4.next = list5;

        ListNode listNode = list1;
        while (Objects.nonNull(listNode)){
            System.out.print(listNode.value + " ");
            listNode = listNode.next;
        }

        System.out.println();

        listNode =  reverseListByLocal(list1);
        while (Objects.nonNull(listNode)){
            System.out.print(listNode.value +" ");
            listNode = listNode.next;
        }
    }
}
