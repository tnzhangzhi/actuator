package com.ymkj.im.algorithm.bplustree2;

import com.ymkj.im.algorithm.bplustree.NodeType;

import java.util.LinkedList;

public class InterNode extends Node{
    LinkedList<Long> pointers;

    public InterNode(NodeType nodeType, Long pageIndex) {
        super(nodeType, pageIndex);
    }
}
