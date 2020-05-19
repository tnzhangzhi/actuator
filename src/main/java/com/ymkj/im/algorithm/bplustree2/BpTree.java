package com.ymkj.im.algorithm.bplustree2;

import com.ymkj.im.algorithm.bplustree.NodeType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class BpTree {

    int totalPages=0; //总页数
    Long maxPageNum=0L; //最大页编号
    int maxKey; //每个节点最大key个数
    int minKey; //ceil(2m/2)-1
    int m = 3;//2m为树的阶数;
    int valLength=10;
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
        long pre = treeFile.readLong();
        long next = treeFile.readLong();
        NodeType nodeType = NodeType.get(type);
        if (nodeType == NodeType.Leaf) {
            LeafNode leafNode = new LeafNode(nodeType, index,pre,next);
            for (int i = 0; i < length; i++) {
                leafNode.getKeys().add(treeFile.readLong());
                byte[] bytes = new byte[10];
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
            LeafNode left = (LeafNode) root;
            root = parent;
            parent.getPointers().add(left.getPageIndex());
            splitNode(parent,left,key);
            insertData(parent,key,value);
        }else {
            insertData(root, key, value);
        }
    }

    private void splitNode(InterNode parent,Node left, Long key) throws IOException {
        if(left.nodeType == NodeType.Internal){
            InterNode right = new InterNode(NodeType.Internal,getCurrentIndex());

        }else{
            LeafNode right = new LeafNode(NodeType.Leaf,getCurrentIndex(),left.getPageIndex(),((LeafNode)left).getNextpoint());
            writeHeader(right);
            int keyIndex = parent.pointers.indexOf(left.getPageIndex());
            parent.getPointers().add(keyIndex+1,right.getPageIndex());
            ((LeafNode)left).setNextpoint(right.getPageIndex());
            int index = m-1;
            for(int i=0;i<index;i++){
                right.getValues().push(((LeafNode) left).getValues().removeLast());
                right.getKeys().push(left.getKeys().removeLast());

            }
            parent.getKeys().add(keyIndex,right.getKeys().get(0));
            writeNode(parent);
            writeNode(right);
            writeNode(left);
        }
    }

    private int caculateIndex(Node node,Long key) {
        int start = 0;
        int end = node.getKeys().size()-1;
        if(start>end){
            return 0;
        }else if(start==end){
            if(key>node.getKeys().get(0)){
                return 1;
            }else{
                return 0;
            }
        } else{
            while(start < end) {
                int m = (start + end) / 2;
                Long temp = node.getKeys().get(m);
                if (key > temp) {
                    start = m + 1;
                } else if (key < temp) {
                    end = m - 1;
                }
            }
            return end<0?0:end;
        }
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
            Node left = readNode(((InterNode)node).getPointers().get(index));
            if(isFull(left)){
                splitNode((InterNode)node,left,key);
                Node right = readNode(((InterNode) node).getPointers().get(index));
                insertData(left,key,value);
            }else{
                insertData(left,key,value);
            }
        }
    }

    private String fillString(String s) {
        if(s == null) {
            s = " ";
        }

        if(s.length() >= this.valLength) {
            s = s.substring(0, this.valLength);
        } else{
            int add = this.valLength - s.length();
            for(int i = 0; i < add; i++) {
                s = s + " ";
            }
        }
        return s;
    }

    public static void main(String[] args) throws IOException {
        Integer[] keys = new Integer[]{3633, 1713, 1687, 2257, 742, 4031, 477, 4604, 9713, 9210, 9860, 4917, 4727, 1622, 8852, 1859, 3952, 3218,
                8680, 2739, 5591, 6315, 3749, 5417, 1873, 9891, 2891, 1416};
        BpTree bpTree = new BpTree();
        for(int i=0;i<6;i++){
            bpTree.insert((long)keys[i],keys[i]+"你好!");
        }

 

    }

}
