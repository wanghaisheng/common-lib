package com.clear.v3.conf;

import java.util.List;

/**
 * Created by chengweisen on 2015/1/22.
 */
public class ConfingInit {

    private static ConfingInit ourInstance = new ConfingInit();

    private static List<ConfigBean> configBeanList;

    public static ConfingInit getInstance() {
        return ourInstance;
    }

    public List<ConfigBean> getConfigBeans() {
        if (configBeanList == null) {
            configBeanList = LoadConfig.getConfigBeans();
        }
        return configBeanList;
    }

    private ConfingInit() {
    }
}
