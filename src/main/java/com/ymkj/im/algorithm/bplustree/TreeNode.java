package com.ymkj.im.algorithm.bplustree;


import java.util.LinkedList;

public class TreeNode extends Node{

  LinkedList<Node> nodes;

  public TreeNode(){
    this.nodeType = NodeType.Internal;
    this.nodes = new LinkedList<>();
  }

  public void addNode(Node node){
    nodes.add(node);
  }

  public LinkedList<Node> getNodes(){
    return nodes;
  }

  @Override
  boolean isLeaf() {
    return false;
  }

  public int getNodeIndex(Node node) {
    return nodes.indexOf(node);
  }
}
