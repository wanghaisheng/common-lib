package com.clear.dao.impl;

import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by chengweisen on 2014/11/1.
 * 监控文件、目录的变化事件
 * 只要文件变化，就会将变化的内容读取出来
 * RandomAccessFile.class
 */
public class MyBasic2 {


    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
        RandomAccessFile accessFile = null;
        try {
            accessFile = new RandomAccessFile(new File("D:/source.txt"),"rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final RandomAccessFile finalAccessFile = accessFile;
        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {

                    try {
                        String line = finalAccessFile.readLine();
                        finalAccessFile.seek(finalAccessFile.getFilePointer());
                        System.out.println("line = " + new String(line.getBytes("ISO-8859-1"),"UTF-8"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1, 2, TimeUnit.SECONDS);
    }

}
