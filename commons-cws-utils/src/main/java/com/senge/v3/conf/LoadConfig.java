package com.clear.v3.conf;

import com.alibaba.druid.pool.DruidDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by chengweisen on 2015/1/22.
 */
public final class LoadConfig {

    public static List<ConfigBean> getConfigBeans() {
        Properties properties = new Properties();
        String osbase = System.getProperty("os.base");
        String name = osbase + "/conf/ds.properties";
        try {
            properties.load(new FileInputStream(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<ConfigBean> confs = new ArrayList<ConfigBean>();
        ConfigBean configBean;
        String[] strings = properties.getProperty("root").split(",");
        for (String ds : strings) {
            configBean = new ConfigBean();
            String subName = properties.getProperty(ds + ".subName");
            String database = properties.getProperty(ds + ".database");
            String subType = properties.getProperty(ds + ".subType");
            String userName = properties.getProperty(ds + ".username");
            String password = properties.getProperty(ds + ".passoword");
            String url = properties.getProperty(ds + ".url");
            String tables = properties.getProperty(ds + ".tables");
            configBean.setSubName(subName);
            configBean.setDatabase(database);
            configBean.setSubType(Integer.parseInt(subType));
            configBean.setTables(tables.split(","));
            configBean.setUsername(userName);
            configBean.setPassword(password);
            configBean.setUrl(url);
            confs.add(configBean);
        }
        createDataSources(confs);
        createTarDataSources(properties, confs);
        return confs;
    }

    /**
     * 创建目标库连接
     *
     * @param properties
     * @param configBeans
     */
    private static void createTarDataSources(Properties properties, List<ConfigBean> configBeans) {
        String username = properties.getProperty("username");
        String passoword = properties.getProperty("passoword");
        String url = properties.getProperty("url");
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(passoword);
        druidDataSource.setUrl(url);
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setMaxActive(80);
        druidDataSource.setMaxWait(60000);
        druidDataSource.setMinIdle(5);
        for (ConfigBean configBean : configBeans) {
            configBean.setTargetDs(druidDataSource);
        }
    }

    /**
     * 创建源库连接
     *
     * @param configBeans
     */
    private static void createDataSources(List<ConfigBean> configBeans) {
        DruidDataSource druidDataSource;
        for (ConfigBean configBean : configBeans) {
            druidDataSource = new DruidDataSource();
            druidDataSource.setUsername(configBean.getUsername());
            druidDataSource.setPassword(configBean.getPassword());
            druidDataSource.setUrl(configBean.getUrl());
            druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
            druidDataSource.setMaxActive(80);
            druidDataSource.setMaxWait(60000);
            druidDataSource.setMinIdle(5);
            configBean.setSourceDs(druidDataSource);
        }
    }

}
