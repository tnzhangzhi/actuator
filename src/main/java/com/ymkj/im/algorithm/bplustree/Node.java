package com.ymkj.im.algorithm.bplustree;

import lombok.Data;

import java.util.LinkedList;

@Data
public abstract class Node {

    private LinkedList<Long> keys;
    protected boolean isRoot;
    protected NodeType nodeType;
    private Node pre;
    private Node next;
    private Node parent;

    public Node(){
        keys = new LinkedList<>();
    }

    public int getCapacity(){
        return keys.size();
    }

    public void addKey(int index,Long key){
        keys.add(index,key);
    }

    public void addKey(Long key){
        keys.add(key);
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
