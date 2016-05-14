package com.clear.bootstarp;

import com.clear.domain.TemplateBean;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

import java.io.File;

/**
 * Created by Administrator on 2015/7/17.
 *
 * 此类用于生成本地MapDB文件，存储Tomcat端口信息
 * ${base.dir}/temp下面
 */
public class MapDBUtils {

    private static MapDBUtils ourInstance = new MapDBUtils();

    private String BASE_DIR = System.getProperty("base.dir");

    private DB make;

    private HTreeMap<String, TemplateBean> hashMap;

    public static MapDBUtils getInstance() {
        return ourInstance;
    }

    private MapDBUtils() {
        DBMaker dbMaker = DBMaker.newFileDB(new File(BASE_DIR + "/temp/AUTO.DB")).closeOnJvmShutdown();
        make = dbMaker.make();
        hashMap = make.getHashMap("AUTO");
    }

    public TemplateBean getTempBean(String key) {
        return hashMap.get(key);
    }

    public void setTempBean(String key, TemplateBean templateBean) {
        hashMap.put(key, templateBean);
        make.commit();
    }

}
