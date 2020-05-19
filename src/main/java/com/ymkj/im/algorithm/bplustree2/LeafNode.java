package com.ymkj.im.algorithm.bplustree2;

import com.ymkj.im.algorithm.bplustree.NodeType;
import lombok.Data;

import java.util.LinkedList;

@Data
public class LeafNode extends Node{

    LinkedList<String> values;
    Long prepoint;
    Long nextpoint;

    public LeafNode(NodeType nodeType, Long pageIndex,Long pre,Long next) {
        super(nodeType, pageIndex);
        this.prepoint = pre;
        this.nextpoint = next;
        this.values = new LinkedList<>();
    }
}
