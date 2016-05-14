package com.clear.bootstarp;

import com.clear.path.CreateInfo;
import com.clear.path.impl.CreateAppContentPath;
import com.clear.path.impl.CreateInstanceContentPath;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by chengweisen on 2014/11/16.
 */
public final class AutoCommons {

    public static void main(String[] args) {
        String baseDir = System.getProperty("base.dir");
         /* 加载配置文件 */
        Properties resourceBundle = new Properties();
        try {
            resourceBundle.load(new FileReader(new File(baseDir + "/conf/conf.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
         /*用户要创建的应用域*/
        String userRoot = args[0];
        if (userRoot.equals("")) {
            throw new NullPointerException("webapp is not null...");
        }
         /*创建WEB根目录*/
        String root = resourceBundle.getProperty("path.tomcat.instance.webroot");
        CreateInfo createApp = new CreateAppContentPath(root, userRoot);
        /* 创建WEB实例目录 */
        String serverRoot = resourceBundle.getProperty("path.tomcat.instance.home");
        String tomcatRoot = resourceBundle.getProperty("path.tomcat.catalina.home");
        /* config */
        String configs = resourceBundle.getProperty("temp.conf");
        CreateInfo createServer = new CreateInstanceContentPath(serverRoot, userRoot, tomcatRoot, configs, root);

    }


}
