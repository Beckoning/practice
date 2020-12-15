package com.pay.payment.center.suanfa;

import java.util.LinkedList;

public class BfsRatMaze {
    /*****重要组成-方向******/
    int[][] direct={{0,1},{0,-1},{-1,0},{1,0}};//四个方向，上下左右
    /******输入数组*****/
    int[][] array={
            {0,0,0,0},
            {0,0,1,1},
            {0,0,0,0},
            {0,0,1,0}
           };
    public static void main(String[] args) throws InterruptedException {
        new BfsRatMaze().BFS();
    }
    /*****重要组成-封装数组点，用坐标表示位置******/
     class Node{
         int row;
         int column;
         int round;
         Node pre;
         Node(int row,int column,int round,Node pre) {
            this.row=row;
            this.column=column;
            this.round=round;
            this.pre=pre;
        }
    }
    public void BFS(){//广度搜索算法
        Node start=new Node(0,0,0,null);
        /*****重要组成-待搜索队列的每个对象都是接下来要所搜的值******/
        LinkedList<Node> queue=new LinkedList<>();//待搜索队列
        queue.offer(start);
        /*****重要组成-持续搜索的标志。待搜索队列里有东西******/
        while(!queue.isEmpty()){
            Node temp=queue.poll();
            for(int i=0;i<4;i++){//尝试搜索四个方向的点，如果满足就加入待搜索队列中
                int new_row=temp.row+direct[i][0];
                int new_column=temp.column+direct[i][1];
                if(new_row<0||new_column<0||new_row>=4||new_column>=4)
                    continue;//该方向上出界，考虑下一方向
                if(array[new_row][new_column]==1)
                    continue;
                Node next=new Node(new_row, new_column,temp.round+1,temp);
                if(new_row==3&&new_column==3)//找到了出口
                {
                    queue.clear();
                    queue.offerFirst(next);
                    while(next.pre!=null){
                        queue.offerFirst(next.pre);//以前获取父节点
                        next=next.pre;
                    }
                    for(Node node:queue)
                    {
                        System.out.println("("+node.row+","+node.column+"),");
                    }
                }
                array[new_row][new_column]=1;
                queue.offer(next);
            }
        }
    }
}