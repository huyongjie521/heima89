package com.itheima.demo;

import com.itheima.pojo.CheckItem;

public class Demo {
    public static void main(String[] args) {
        CheckItem checkItem = new CheckItem();
        checkItem.setName("zhangsan");

        CheckItem checkItem2 = new CheckItem();
        checkItem2 = checkItem;

        checkItem2.setName("lisi");

        System.out.println(checkItem.getName());
    }
}
