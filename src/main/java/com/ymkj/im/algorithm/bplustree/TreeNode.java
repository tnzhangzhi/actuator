package com.ymkj.im.algorithm.bplustree;

import com.ymkj.im.datastructure.LinkList;

public class TreeNode extends Node{

  LinkList<Node> nodes;

  @Override
  boolean isLeaf() {
    return false;
  }
}
