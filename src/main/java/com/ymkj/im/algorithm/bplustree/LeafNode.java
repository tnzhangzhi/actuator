package com.ymkj.im.algorithm.bplustree;


import lombok.Data;

import java.util.LinkedList;

@Data
public class LeafNode extends Node{

    private LinkedList<String> values;
    private Node next;
    private Node pre;

    public LeafNode(boolean isRoot){
        this.isRoot = isRoot;
        this.nodeType = NodeType.Leaf;
        this.values = new LinkedList<>();
    }

    public void addValue(int index,String value){
        values.add(index,value);
    }

    @Override
    boolean isLeaf() {
        return nodeType==NodeType.Leaf?true:false;
    }
}
