package com.clear.dao.impl;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

import java.io.File;

/**
 * Created by chengweisen on 2014/12/18.
 */
public class MapdbTest {
    public static void main(String[] args) {
        DB treeMap = DBMaker.newFileDB(new File("d:/aa.db"))
                .closeOnJvmShutdown()
                .encryptionEnable("password")
                .make();
        HTreeMap<Integer, Integer> map = treeMap.getHashMap("st_stock_m");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            map.put(i, i);
        }
        treeMap.close();
        System.out.println("treeMap = " + (System.currentTimeMillis() - start) / (1000 * 60));
    }
}
