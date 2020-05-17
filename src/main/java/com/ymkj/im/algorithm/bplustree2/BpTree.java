package com.ymkj.im.algorithm.bplustree2;

import com.ymkj.im.algorithm.bplustree.NodeType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BpTree {

    int totalPages=0; //总页数
    int maxPageNum=0; //最大页编号
    int maxKey; //每个节点最大key个数
    int m = 3;//2m为树的阶数;
    Node root;
    int pageSize = 1024;//每页大小,单位字节
    RandomAccessFile treeFile;
    int currentIndex = 0;//？
    String filePath = "data.bin";

    public BpTree() throws IOException {
        maxKey = 2*m-1;
        File file = new File(filePath);
        if(file.exists()){
            load();
        }else{
            //0-1024文件头占用,节点page页从1024开始
            root = new LeatNode(NodeType.Leaf,1024L);
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

    private void writeNode(Node root) {
    }

    private void load() {
    }

    public void insert(){

    }
}
