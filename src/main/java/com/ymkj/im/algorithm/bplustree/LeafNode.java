package com.ymkj.im.algorithm.bplustree;


import java.util.LinkedList;

public class LeafNode extends Node{

    private LinkedList<String> values;

    public LeafNode(boolean isRoot){
        this.isRoot = isRoot;
        this.nodeType = NodeType.Leaf;
    }

    public void addValue(int index,String value){
        values.add(index,value);
    }

    @Override
    boolean isLeaf() {
        return nodeType==NodeType.Leaf?true:false;
    }
}
