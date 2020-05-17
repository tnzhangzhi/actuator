package com.ymkj.im.algorithm.bplustree2;

import com.ymkj.im.algorithm.bplustree.NodeType;
import lombok.Data;

import java.util.LinkedList;

@Data
public abstract class Node {
    LinkedList<Long> keys;
    Long pageIndex;
    NodeType nodeType;

    public Node(NodeType nodeType,Long pageIndex){
        this.nodeType = nodeType;
        this.pageIndex = pageIndex;
    }
}
