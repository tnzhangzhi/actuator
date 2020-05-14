package com.ymkj.im.algorithm.bplustree;

import java.util.LinkedList;

public abstract class Node {

    private LinkedList<Long> keys;
    protected boolean isRoot;
    protected NodeType nodeType;
    private Node pre;
    private Node next;

    public int getCapacity(){
        return keys.size();
    }

    public void addKey(int index,Long key){
        keys.add(index,key);
    }

    boolean isLeaf(){
        return nodeType==NodeType.Leaf?true:false;
    }

    public LinkedList<Long> getKeys() {
        return keys;
    }

    public void setKeys(LinkedList<Long> keys) {
        this.keys = keys;
    }
}
