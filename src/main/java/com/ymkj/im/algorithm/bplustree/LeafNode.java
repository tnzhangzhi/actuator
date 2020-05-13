package com.ymkj.im.algorithm.bplustree;

import com.ymkj.im.datastructure.LinkList;

public class LeafNode extends Node{

    private LinkList<String> values;

    public LeafNode(boolean isRoot){
        this.isRoot = isRoot;
        this.nodeType = NodeType.Leaf;
    }

    @Override
    boolean isLeaf() {
        return nodeType==NodeType.Leaf?true:false;
    }
}
