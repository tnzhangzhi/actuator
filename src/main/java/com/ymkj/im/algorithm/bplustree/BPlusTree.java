package com.ymkj.im.algorithm.bplustree;

public class BPlusTree {

    private int order = 5;
    private int maxKey = 4; //order-1;
    private int minKey = 2; //Math.ceil(order/2)-1;
    Node root;
    Node leafHead;

    public BPlusTree(){
        root = new LeafNode(true);
    }

    public void insert(Long key,String value){
        if(root.getCapacity())
    }

    public boolean isFull(Node node){
        if(node.getCapacity()>maxKey){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(Math.ceil(5d/2));
    }
}
