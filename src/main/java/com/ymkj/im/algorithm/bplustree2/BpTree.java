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
    int maxPageNum=0; //最大页编号
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
        File file = new File(filePath);
        if(file.exists()){
            load();
        }else{
            //0-1024文件头占用,节点page页从1024开始
            root = new LeafNode(NodeType.Leaf,1024L);
            try {
                //读写模式，rw不存在则创建
                treeFile = new RandomAccessFile("data.bin","rw");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            writeHeader();
            writeNode(root);
        }


    }

    private void writeHeader() throws IOException {
        treeFile.seek(0);
        treeFile.writeInt(maxPageNum);
        treeFile.writeInt(totalPages);
    }

    private void writeNode(Node node) throws IOException {
        if(node.nodeType == NodeType.Leaf){
            LeafNode leafNode = (LeafNode) node;
            treeFile.seek(node.getPageIndex());
            treeFile.writeShort(1);//1代表叶子节点 0内部节点
            treeFile.writeInt(leafNode.getKeys().size());
            treeFile.writeLong(leafNode.getPrepoint());
            treeFile.writeLong(leafNode.getNextpoint());
            for(int i=0;i<leafNode.getKeys().size();i++){
                treeFile.writeLong(leafNode.getKeys().get(i));
                treeFile.write(leafNode.getValues().get(i).getBytes(StandardCharsets.UTF_8));
            }

        }else{
            InterNode interNode = (InterNode) node;
            treeFile.seek(node.getPageIndex());
            treeFile.writeShort(0);//1代表叶子节点 0内部节点
            treeFile.writeInt(interNode.getKeys().size());
            for(int i=0;i<interNode.getKeys().size();i++){
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
        if(nodeType==NodeType.Leaf){
            LeafNode leafNode = new LeafNode(nodeType,index);
            leafNode.setPrepoint(treeFile.readLong());
            leafNode.setNextpoint(treeFile.readLong());
            for(int i=0;i<length;i++){
                leafNode.getKeys().add(treeFile.readLong());
                byte[] bytes = new byte[10];
                treeFile.read(bytes);
                leafNode.getValues().add(new String(bytes,StandardCharsets.UTF_8));
            }
            return leafNode;
        }else{
            InterNode interNode = new InterNode(nodeType,index);
            for(int i=0;i<length;i++){
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



    public void insert(Long key,String value){
        if(isFull(root)){
            InterNode parent = new InterNode(NodeType.Internal,);
            splitNode(root,key);
        }else {
            insertData(root, key, value);
        }
    }

    private boolean isFull(Node node) {
        return node.getKeys().size()>maxKey?true:false;
    }

    public void insertData(Node node,Long key,String value){
        if(node.getNodeType()==NodeType.Leaf){

        }else{

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

}
