package com.fang.movie.Test;

import java.util.Arrays;

public class Test {

    public static final int[] nums = {1,9,4,7,6};

    public static void main(String[] args) {
//        maopao();
//        xuanzhe();
        charu();
    }

    private static void maopao(){
        for (int i = 0;i < nums.length-1;i++){
            for (int j=0;j<nums.length -1 -i;j++){
                if (nums[j] > nums[j+1]){
                    int temp = nums[j];
                    nums[j] = nums[j+1];
                    nums[j+1] = temp;
                }
            }
        }
//        yz();
    }

    private static void xuanzhe(){
        for (int i =0;i< nums.length-1 ;i++){
            int min = i;
            for (int j=i+1;j<nums.length;j++){
                if (nums[min] > nums[j]){
                        min = j;
                }
            }
            if (min != i){
                int temp = nums[min];
                nums[min] = nums[i];
                nums[i] = temp;
            }
        }
        yz();
    }

    private static void charu(){
        for (int i=0;i<nums.length-1;i++){
            for (int j=i+1;j>0 && nums[j] < nums[j-1];j--){
                int temp = nums[j];
                nums[j] = nums[j-1];
                nums[j-1] = temp;
            }
        }
        yz();
    }

    public static void yz(){
        System.out.println(Arrays.toString(nums));
    }

}
