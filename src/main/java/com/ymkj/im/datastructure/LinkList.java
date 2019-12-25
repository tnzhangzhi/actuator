package com.ymkj.im.datastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LinkList<T> {

    public synchronized void add(T val){
        Node node = new Node(val);
        Node t =tail;
        tail=node;
        if(t==null){
            head = tail = node;
        }else{
            t.next=node;
            node.pre = t;

        }
         size++;
    }

    public T get(int index){
        if(index==0){
            return head.val;
        }
        Node<T> node = head;
        for(int i=0;i<index;i++){
            node = node.next;
        }
        return node.val;
    }

    public void remove(int index){
        if(index == 0){
            Node node = head.next;
            Node temp = head;
            head = node;
            temp = null;
        }
        Node node =head;
        for(int i=0;i<index;i++){
            node = node.next;
        }
        Node pre = node.pre;
        Node next = node.next;
        if(next!=null){
            pre.next=next;
            next.pre = pre;
        }
        node = null;
        size--;
    }

    public Integer size(){
        return size;
    }

    Node<T> head;
    Node<T> tail;
    Integer size=0;

    static class Node<T>{
        T val;

        Node pre;
        Node next;

        public Node(){

        }

        public Node(T o){
            this.val = o;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LinkList<String> list = new LinkList<>();
        List<Thread> ts = new ArrayList<>();
        for(int i=0;i<10;i++){
            Thread s = new Thread(()->{
                for(int k=0;k<100;k++) {
                    list.add(Thread.currentThread().getName() + ":" + k);
                }
            },"t"+i);
            ts.add(s);
        }
        for(Thread t : ts){
            t.start();
        }
        for(Thread t : ts){
            t.join();
        }
        int s = list.size();
        System.out.println(s);
        for(int n=0;n<s;n++){
            System.out.println(list.get(n));
        }
    }
}
