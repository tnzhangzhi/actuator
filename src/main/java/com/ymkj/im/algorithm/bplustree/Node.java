package com.ymkj.im.algorithm.bplustree;

import com.ymkj.im.datastructure.LinkList;

public abstract class Node {
    private LinkList<Long> keys;
    protected boolean isRoot;
    protected NodeType nodeType;
    private Node pre;
    private Node next;

    public int getCapacity(){
        return keys.size();
    }

    boolean isLeaf(){
        return nodeType==NodeType.Leaf?true:false;
    }
}
