package com.clear.html;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by chengweisen on 2015/1/6.
 */
public class ClockSchedul {

    private final static Logger logger = LoggerFactory.getLogger(ClockSchedul.class);

    private final static ClockQaurtz quartzManager = ClockQaurtz.instance();

    public static void main(String[] args) {
        String property = System.getProperty("os.base");
        String configFilename = property + "/conf/log4j.properties";
        PropertyConfigurator.configure(configFilename);
        String cron = ClockUtils.getCron();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(property + "/conf/conf.properties"));
            String cronBar = properties.getProperty("cron");
            if (cronBar.isEmpty()) {
                quartzManager.addJob("job1", new MyJob(), cron);
                quartzManager.addJob("job2", new ReSetCronJob(), "0 0 8,18 * * ?");
            } else {
                quartzManager.addJob("job1", new MyJob(), cronBar);
                //quartzManager.addJob("job2", new ReSetCronJob(), "0 0 8,18 * * ?");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

