package com.clear.web;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengweisen on 2015/10/27.
 *
 * @author chengweisen
 */
public class JvmTest {


    /**
     * java.lang.OutOfMemoryError: Java heap space
     * -Xms10m 堆的最大内存
     * -Xmx10m 堆的最小内存
     */
    @Test
    public void addUser() {
        List<String> strings = new ArrayList<String>();
        while (true) {
            strings.add("aaaaaaaaaaaaaaaa");
        }
    }


    /**
     * java.lang.StackOverflowError
     * -Xss1024k 设置stack大小，每个线程都是一独立的stack
     */
    @Test
    public void addPerson() {

        while (true) {
            addNum();
        }
    }


    public void addNum() {
        while (true) {
            addPerson();
        }
    }

}
