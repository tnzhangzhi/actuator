package com.ymkj.im.algorithm.bplustree2;

import com.ymkj.im.algorithm.bplustree.NodeType;

import java.util.LinkedList;

public class LeatNode extends Node{

    LinkedList<Long> values;

    public LeatNode(NodeType nodeType, Long pageIndex) {
        super(nodeType, pageIndex);
    }
}
