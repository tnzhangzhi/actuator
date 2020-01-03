package com.ymkj.im.algorithm;

public class Sort {
    //插入排序:从数组的第二个元素开始，取出来，和它前面的元素挨个比较，如果它前面的元素比它大，则把他前面的元素右移
    //特点是：每次循环比较完，左边的元素都是有序的。就像打扑克，把右侧没排序的往左边插，左边都是排好序的
    public static void insertSort(int[] a){
        for(int i=1;i<a.length;i++){
            int key = a[i];
            int j = i-1;
            while(j>=0 && a[j]>key){ //和它前面的元素挨个比较
                a[j+1] = a[j] ;//元素右移
                j--;//没左移一次减1
            }
            a[j+1]=key;//上面循环完表示，和左边的比较完毕，左移了几次，就放在对应的位置上
        }
    }

    //合并排序
    public static void mergeSort(){

    }

    public static void main(String[] args) {
        int[] a = new int[]{5,9,4,2,7,8,1,3,6};
        Sort.insertSort(a);
        System.out.println(a);
    }
}
