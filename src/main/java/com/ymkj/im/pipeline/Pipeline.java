package com.ymkj.im.pipeline;

public class Pipeline {
    Node head;
    Node tail;
    public void addLast(BusinessService service){
        Node node = new Node(service);
        Node pre = tail;
        if(pre == null){
            head = tail = node;
        }else{
            pre.next = node;
            node.pre=pre;
            tail = node;
        }
    }

    public void request(){
        boolean next = true;
        Node node = head;
        while(next) {
            if(node!=null) {
                BusinessService service = node.businessService;
                if (service.check()) {
                    node=node.next;
                }else{
                    System.out.println("执行完毕");
                    next = false;
                }
            }else{
                next = false;
            }
        }
    }

    static class Node{
        Node next;
        Node pre;
        BusinessService businessService;

        public Node(BusinessService businessService){
            this.businessService = businessService;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPre() {
            return pre;
        }

        public void setPre(Node pre) {
            this.pre = pre;
        }
    }
}
