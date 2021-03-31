package com.pay.payment.center.suanfa.tree;

import lombok.Data;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * 树的左右视图
 */
public class TreeLeftView {


    /**
     * 打印左视图
     * @param treeNode
     */
    public static void printTreeLeftView(TreeNode treeNode){
        Queue<TreeNode> q1 = new LinkedList<>();
        Queue<TreeNode> q2 = new LinkedList<>();


        q1.add(treeNode);
        while(!q1.isEmpty()){
            System.out.println(q1.peek().getValue());

            while (!q1.isEmpty()){

                TreeNode tree =  q1.poll();

                if(Objects.nonNull(tree.getLeft())){
                    q2.add(tree.getLeft());
                }
                if(Objects.nonNull(tree.getRight())){
                    q2.add(tree.getRight());
                }
            }

            q1.addAll(q2) ;
            q2.clear();

        }

    }



    /**
     * 打印右视图
     * @param treeNode
     */
    public static void printTreeRightView(TreeNode treeNode){
        Queue<TreeNode> q1 = new LinkedList<>();
        Queue<TreeNode> q2 = new LinkedList<>();


        q1.add(treeNode);
        while(!q1.isEmpty()){

            while (!q1.isEmpty()){
                if(q1.size() == 1){
                    System.out.println(q1.peek().getValue());
                }
                TreeNode tree =  q1.poll();

                if(Objects.nonNull(tree.getLeft())){
                    q2.add(tree.getLeft());
                }
                if(Objects.nonNull(tree.getRight())){
                    q2.add(tree.getRight());
                }
            }

            q1.addAll(q2) ;
            q2.clear();

        }

    }


    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode r2 = new TreeNode(2);
        TreeNode r3 = new TreeNode(3);
        TreeNode r4 = new TreeNode(4);
        TreeNode r5 = new TreeNode(5);
        TreeNode r6 = new TreeNode(6);
        TreeNode r7 = new TreeNode(7);
        TreeNode r8 = new TreeNode(8);
        TreeNode r9 = new TreeNode(9);
        TreeNode r10 = new TreeNode(10);
        TreeNode r11 = new TreeNode(11);
        TreeNode r12 = new TreeNode(12);
        TreeNode r13 = new TreeNode(13);
        TreeNode r14 = new TreeNode(14);
        TreeNode r15 = new TreeNode(15);
        root.left = r2;
        root.right = r3;
        r2.left = r4;
        r2.right = r5;
        r3.left = r6;
        r3.right = r7;
        r4.left = r8;
        r4.right = r9;
        r9.left = r10;
        r6.left = r11;
        r7.right=r12;
        r12.right=r13;
        r13.right=r14;
        r14.right=r15;
        printTreeLeftView(root);
    }
}




@Data
class TreeNode{
    private int value;
     TreeNode left;
     TreeNode right;

    public TreeNode(int value) {
        this.value = value;
    }
}
