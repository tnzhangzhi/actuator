package com.ymkj.im.algorithm.bplustree;

import java.util.LinkedList;
import java.util.Random;

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
        //从根节点向下查找
        //如果是叶子节点，直接查找合适位置插入，如果不是叶子节点，向下查找到对应的叶子节点插入
        //刚开始只有一个节点的时候，根节点即是内部节点也是叶子节点
        if(root.isLeaf()){
            LeafNode leafNode = (LeafNode)root;
            //当前节点是否满了，没满插入
            if(isFull(leafNode)){
                TreeNode treeNode = new TreeNode();
                root = treeNode;
                LeafNode right = new LeafNode(false);
                LeafNode left = new LeafNode(false);

            }else{
                int index = caculateKeyIndex(leafNode.getKeys(),key);
                leafNode.addKey(index,key);
                leafNode.addValue(index,value);
            }
        }else{

        }
    }

    public void addNonNull(){

    }

    private static int caculateKeyIndex(LinkedList<Long> keys,Long key) {
        if(keys.size()==0){
            return 0;
        }
        int start = 0;
        int end = keys.size();

        while((end-start) >0){
            int index = (end+start)/2;
            if(key > keys.get(index)){
                start = index+1;
            }else{
                end = index;
            }
        }

        return end;
    }


    public boolean isFull(Node node){
        if(node.getCapacity()>maxKey){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        for(int i=0;i<10;i++) {
            long key = new Random().nextInt(100);
            int index = caculateKeyIndex(list, key);
            list.add(index,key);
            System.out.println("key="+key);
            System.out.println(list);
        }

    }
}
