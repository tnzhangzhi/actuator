package com.ymkj.im.algorithm.bplustree2;

import com.ymkj.im.algorithm.bplustree.NodeType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class BpTree {

    int totalPages=0; //总页数
    Long maxPageNum=0L; //最大页编号
    int maxKey; //每个节点最大key个数
    int minKey; //ceil(2m/2)-1
    int m = 3;//2m为树的阶数;
    int valLength=20;
    Node root;
    int pageSize = 1024;//每页大小,单位字节
    RandomAccessFile treeFile;
    int currentIndex = 0;//当前页位置
    String filePath = "data.bin";

    public BpTree() throws IOException {
        maxKey = 2*m-1;
        minKey = m-1;
        treeFile = new RandomAccessFile("data.bin","rw");
        readHeader();
        if(totalPages>0){
            load();
        }else{
            //0-1024文件头占用,节点page页从1024开始
            root = new LeafNode(NodeType.Leaf,1024L,-1L,-1L);
            writeNode(root);
            writeHeader(root);
        }


    }



    private void readHeader() {
        try {
            long length = treeFile.length();
            if (length > 0) {
                maxPageNum = treeFile.readLong();
                totalPages = treeFile.readInt();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void writeHeader(Node node) throws IOException {
        totalPages++;
        treeFile.seek(0);
        treeFile.writeLong(node.getPageIndex());
        treeFile.writeInt(totalPages);
    }

    private void writeNode(Node node) throws IOException {
        if (node.nodeType == NodeType.Leaf) {
            LeafNode leafNode = (LeafNode) node;
            treeFile.seek(node.getPageIndex());
            treeFile.writeShort(1);//1代表叶子节点 0内部节点
            treeFile.writeInt(leafNode.getKeys().size());
            treeFile.writeLong(leafNode.getPrepoint());
            treeFile.writeLong(leafNode.getNextpoint());
            for (int i = 0; i < leafNode.getKeys().size(); i++) {
                treeFile.writeLong(leafNode.getKeys().get(i));
                String val = leafNode.getValues().get(i);
                treeFile.write(leafNode.getValues().get(i).getBytes(StandardCharsets.UTF_8));
            }
        } else {
            InterNode interNode = (InterNode) node;
            treeFile.seek(node.getPageIndex());
            treeFile.writeShort(0);//1代表叶子节点 0内部节点
            treeFile.writeInt(interNode.getKeys().size());
            for (int i = 0; i < interNode.getKeys().size(); i++) {
                treeFile.writeLong(interNode.getKeys().get(i));
                treeFile.writeLong(interNode.getPointers().get(i));
            }
            treeFile.writeLong(interNode.getPointers().get(interNode.getKeys().size()));
        }

    }

    private Node readNode(Long index) throws IOException {
        treeFile.seek(index);
        int type = treeFile.readShort();
        int length = treeFile.readInt();
        NodeType nodeType = NodeType.get(type);
        if (nodeType == NodeType.Leaf) {
            long pre = treeFile.readLong();
            long next = treeFile.readLong();
            LeafNode leafNode = new LeafNode(nodeType, index,pre,next);
            for (int i = 0; i < length; i++) {
                leafNode.getKeys().add(treeFile.readLong());
                byte[] bytes = new byte[valLength];
                treeFile.read(bytes);
                leafNode.getValues().add(new String(bytes, StandardCharsets.UTF_8));
            }
            return leafNode;
        } else {
            InterNode interNode = new InterNode(nodeType, index);
            for (int i = 0; i < length; i++) {
                interNode.getKeys().add(treeFile.readLong());
                interNode.getPointers().add(treeFile.readLong());
            }
            interNode.getPointers().add(treeFile.readLong());
            return interNode;
        }
    }

    private void load() throws IOException {
        root = readNode(1024L);
    }



    public void insert(Long key,String value) throws IOException {
        if(isFull(root)){
            InterNode parent = new InterNode(NodeType.Internal,getCurrentIndex());
            writeHeader(parent);
            Node left = root;
            root = parent;
            parent.getPointers().add(left.getPageIndex());
            splitNode(parent, left, key);
            insertData(parent, key, value);
        }else {
            insertData(root, key, value);
        }
    }

    private void splitNode(InterNode parent,Node left, Long key) throws IOException {
        Node right;
        int index = m-1;
        int keyIndex = parent.pointers.indexOf(left.getPageIndex());

        if(left.nodeType == NodeType.Internal){
            right = new InterNode(NodeType.Internal,getCurrentIndex());
        }else {
            right = new LeafNode(NodeType.Leaf, getCurrentIndex(), left.getPageIndex(), ((LeafNode) left).getNextpoint());
            ((LeafNode)left).setNextpoint(right.getPageIndex());
        }
        writeHeader(right);
        parent.getPointers().add(keyIndex+1,right.getPageIndex());

        for(int i=0;i<index;i++){
            if(left.nodeType ==NodeType.Leaf) {
                ((LeafNode) right).getValues().push(((LeafNode) left).getValues().removeLast());
            }else{
                ((InterNode) right).getPointers().push(((InterNode) left).getPointers().removeLast());
            }
            right.getKeys().push(left.getKeys().removeLast());
        }

        if(left.nodeType ==NodeType.Leaf) {
            parent.getKeys().add(keyIndex, right.getKeys().get(0));
        }else{
            parent.getKeys().add(keyIndex, left.getKeys().removeLast());
            ((InterNode) right).getPointers().push(((InterNode) left).getPointers().removeLast());
        }
        writeNode(parent);
        writeNode(right);
        writeNode(left);
    }

    private int caculateIndex(Node node,Long key) {
        if(node.getKeys().size()==0){
            return 0;
        }
        int start = 0;
        int end = node.getKeys().size();
        while((end-start) >0){
            int index = (end+start)/2;
            if(key > node.getKeys().get(index)){
                start = index+1;
            }else{
                end = index;
            }
        }
        return end;
    }



    public Long getCurrentIndex(){
        return (long)(pageSize*(totalPages+1));
    }

    private boolean isFull(Node node) {
        return node.getKeys().size()==maxKey?true:false;
    }

    public void insertData(Node node,Long key,String value) throws IOException {
        int index = caculateIndex(node,key);
        if(node.getNodeType()==NodeType.Leaf){
            node.getKeys().add(index,key);
            ((LeafNode)node).getValues().add(index,fillString(value));
            writeNode(node);
        }else{
            //查找下一级判断是否满了，循环
            Node child = readNode(((InterNode)node).getPointers().get(index));
            if(isFull(child)){
                splitNode((InterNode)node,child,key);
                insertData(node,key,value);
            }else{
                insertData(child,key,value);
            }
        }
    }

    private String fillString(String s) {
        if(s == null) {
            s = " ";
        }
        int len = s.getBytes(StandardCharsets.UTF_8).length;
        if(len >= this.valLength) {
            s=s.substring(0,s.length()-1);
            while(s.getBytes(StandardCharsets.UTF_8).length > valLength){
                s=s.substring(0,s.length()-1);
            }
            len = s.getBytes(StandardCharsets.UTF_8).length;
            int add = valLength-len;
            if(add > 0){
                addSpace(s,add);
            }
        } else{
            int add = this.valLength - len;
            s = addSpace(s,add);
        }
        return s;
    }

    public String addSpace(String s,int num){
        StringBuffer sbf = new StringBuffer(s);
        for(int i = 0; i < num; i++) {
            sbf.append(" ");
        }
        return sbf.toString();
    }

    public static void main(String[] args) throws IOException {
//        Integer[] keys = new Integer[]{3633, 1713, 1687, 2257, 742, 4031, 477, 4604, 9713, 9210, 9860, 4917, 4727, 1622, 8852, 1859, 3952, 3218,
//                8680, 2739, 5591, 6315, 3749, 5417, 1873, 9891, 2891, 1416};
//        BpTree bpTree = new BpTree();
//        for(int i=0;i<keys.length;i++){
//            System.out.println(keys[i]);
//            bpTree.insert((long)keys[i],keys[i]+"你好!");
//        }



 

    }

}
