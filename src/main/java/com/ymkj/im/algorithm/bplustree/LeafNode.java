package com.ymkj.im.algorithm.bplustree;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.LinkedList;

@Data
public class LeafNode extends Node{

    @JSONField(serialize = false)
    private LinkedList<String> values;

    @JSONField(serialize = false)
    private Node next;

    @JSONField(serialize = false)
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
