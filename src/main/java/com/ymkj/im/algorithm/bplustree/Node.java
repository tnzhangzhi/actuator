package com.ymkj.im.algorithm.bplustree;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.LinkedList;

@Data
public abstract class Node {

    @JSONField(name = "name")
    private LinkedList<Long> keys;
    @JSONField(serialize = false)
    protected boolean isRoot;
    @JSONField(serialize = false)
    protected NodeType nodeType;

    @JSONField(serialize = false)
    private Node pre;
    @JSONField(serialize = false)
    private Node next;
    @JSONField(serialize = false)
    private Node parent;

    public Node(){
        keys = new LinkedList<>();
    }

    @JSONField(serialize = false)
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
