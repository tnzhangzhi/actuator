package com.ymkj.im.algorithm.bplustree;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
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
            insert((LeafNode) root,key,value);
        }else{
            Node node = getLeaf((TreeNode) root,key);
            while(!node.isLeaf()){
                node = getLeaf((TreeNode)node,key);
            }
            insert((LeafNode)node,key,value);
        }
    }

    private Node getLeaf(TreeNode node,Long key) {
        int keyIndex = caculateKeyIndex(node.getKeys(),key);
        System.out.println("key="+key);
        System.out.println("keys="+node.getKeys());
        System.out.println("nodesize="+node.getNodes().size());
        System.out.println("nodes="+node.getNodes());
        System.out.println("index="+keyIndex);
        return node.getNodes().get(keyIndex);
    }

    public void insert(LeafNode leafNode,Long key,String value){
        int index = caculateKeyIndex(leafNode.getKeys(),key);
        leafNode.addKey(index,key);
        leafNode.addValue(index,value);
        updateNode(leafNode);
    }

    private void updateNode(Node node) {
        if(isFull(node)){
            TreeNode parent = (TreeNode) node.getParent();
            if(parent == null){
                parent = new TreeNode();
                root = parent;
            }
            if(node.isLeaf()) {
                //当前节点作为左节点，新建一个右节点
                LeafNode right = new LeafNode(false);
                right.setParent(parent);
                node.setParent(parent);
                int nodeIndex = parent.getNodeIndex(node);
                int keyIndex = (node.getKeys().size() - 1) / 2;
                Long key = node.getKeys().get(keyIndex);

                for (int i = keyIndex; i < node.getKeys().size(); i++) {
                    right.getKeys().add(node.getKeys().remove(i));
                    right.getValues().add(((LeafNode) node).getValues().remove(i));
                    i--;
                }

                if (nodeIndex >= 0) {
                    parent.getNodes().add(nodeIndex + 1, right);
                } else {
                    parent.getNodes().add(node);
                    parent.getNodes().add(right);
                }
                parent.addKey(nodeIndex < 0 ? 0 : nodeIndex, key);
            }else{
                TreeNode right = new TreeNode();
                right.setParent(parent);
                node.setParent(parent);
                int nodeIndex = parent.getNodeIndex(node);
                int keyIndex = (node.getKeys().size() - 1) / 2;
                Long key = node.getKeys().get(keyIndex);

                for (int i = keyIndex; i < node.getKeys().size(); i++) {
                    right.getKeys().add(node.getKeys().remove(i));
                    i--;
                }
                for (int i = keyIndex; i < ((TreeNode)node).getNodes().size(); i++) {
                    right.getNodes().add(((TreeNode)node).getNodes().remove(i));
                    i--;
                }

                if (nodeIndex >= 0) {
                    parent.getNodes().add(nodeIndex + 1, right);
                } else {
                    parent.getNodes().add(node);
                    parent.getNodes().add(right);
                }
                parent.addKey(nodeIndex < 0 ? 0 : nodeIndex, key);
            }
            updateNode(parent);
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
        BPlusTree tree = new BPlusTree();
        for(int i=0;i<100;i++) {
            long key = new Random().nextInt(10000);
            tree.insert(key,key+"");
        }
        printTree2(tree);

    }

    private static void printTree2(BPlusTree tree) {
        Node node = tree.root;
        Map map = new HashMap();
        if(node.isLeaf()){

        }else{
            TreeNode treeNode = (TreeNode) node;
            printNode2(treeNode);
        }

    }

    private static void printNode2(TreeNode treeNode,Map map) {
        map.put("name",treeNode.getKeys());
        Object[] a = new Object[treeNode.getNodes().size()];
        for(int i=0;i<treeNode.getNodes().size();i++){
            a[i] = treeNode.getNodes().get(i).getKeys();
        }
        map.put("children",a);
    }

    private static void printTree(BPlusTree tree) {
        Node node = tree.root;
        printNode(node);

    }

    private static void printNode(Node node) {
        if(node.isLeaf()){
            LeafNode leaf = (LeafNode) node;
            System.out.println("##########################");
            System.out.println(leaf.getKeys());
            System.out.println(leaf.getValues());
            System.out.println("########################");
        }else{
            TreeNode treeNode = (TreeNode) node;
            System.out.println("***********************");
            System.out.println(node.getKeys());
            System.out.println("***********************");
            LinkedList<Node> nodeList = treeNode.getNodes();
            for(int i=0;i<nodeList.size();i++){
                printNode(nodeList.get(i));
            }
        }
    }
}
