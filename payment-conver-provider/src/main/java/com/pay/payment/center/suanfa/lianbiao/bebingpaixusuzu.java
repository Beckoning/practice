package com.pay.payment.center.suanfa.lianbiao;


class bebingpaixusuzu {



    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        }

        ListNode fakeHead = new ListNode(-1);
        ListNode curNode = fakeHead;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                curNode.next = l1;
                l1 = l1.next;
            } else {
                curNode.next = l2;
                l2 = l2.next;
            }

            curNode = curNode.next;
        }
        curNode.next = l1==null ? l2 : l1;
        return fakeHead.next;
    }

    static class ListNode{

        int val;
        ListNode next ;

        public ListNode(int val) {
            this.val = val;
        }
    }
}

