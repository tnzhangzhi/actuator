package com.ymkj.im.algorithm.bplustree;

public enum NodeType {
    Internal(0),Leaf(1);

    private int type;
     NodeType(int type){
        this.type = type;
    }

    public static NodeType get(int type){
         for(NodeType nodeType :NodeType.values()){
             if(nodeType.type == type){
                 return nodeType;
             }
         }
         return null;
    }
}
