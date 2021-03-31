package com.pay.payment.center.suanfa.lianbiao;

import java.util.HashSet;
import java.util.Objects;

public class lianbiaoyouhuan {


    static class ListNode{

        int value;
        ListNode next ;

        public ListNode(int
                                value) {
            this.value = value;
        }
    }


    public static boolean hasCycleForSet(ListNode head) {
        HashSet<ListNode> hashSet = new HashSet();

        while (Objects.nonNull(head)){
            if(hashSet.contains(head)){
                return true;
            }
            hashSet.add(head);
            head = head.next;
        }
        return false;
    }

    public static boolean hasCycleForPointer(ListNode head) {

        if(Objects.isNull(head) || Objects.isNull(head.next) || Objects.isNull(head.next.next)){
            return false;
        }
        ListNode slowPointer = head.next;
        ListNode fastPointer = head.next.next;


        while (Objects.nonNull(slowPointer) && Objects.nonNull(fastPointer) && Objects.nonNull(slowPointer.next) && Objects.nonNull(fastPointer.next)){
            if(slowPointer == fastPointer){
                return true;
            }
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
        }
        return false;
    }

    public static void main(String[] args) {
        ListNode list1 = new ListNode(1);
        ListNode list2 = new ListNode(2);
        ListNode list3 = new ListNode(3);
        ListNode list4 = new ListNode(4);
        ListNode list5 = new ListNode(5);
        ListNode list6 = new ListNode(6);
        ListNode list7 = new ListNode(7);
        ListNode list8 = new ListNode(8);
        ListNode list9 = new ListNode(9);

        list1.next = list2;
        list2.next = list3;
        list3.next = list4;
        list4.next = list5;
        list5.next = list6;
        list6.next = list7;
        list7.next = list8;
        list8.next = list9;
        list9.next = list1;


        System.out.println(hasCycleForPointer(list1));
    }




}
