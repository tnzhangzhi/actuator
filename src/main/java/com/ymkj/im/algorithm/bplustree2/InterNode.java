package com.ymkj.im.algorithm.bplustree2;

import com.ymkj.im.algorithm.bplustree.NodeType;
import lombok.Data;

import java.util.LinkedList;

@Data
public class InterNode extends Node{
    LinkedList<Long> pointers;

    public InterNode(NodeType nodeType, Long pageIndex) {
        super(nodeType, pageIndex);
        this.pointers = new LinkedList<>();
    }
}
