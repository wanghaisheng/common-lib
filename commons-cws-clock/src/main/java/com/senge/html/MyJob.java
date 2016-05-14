package com.clear.html;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by chengweisen on 2015/1/6.
 */
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        MySina mySina = new MySina();
        mySina.mailLoginBySina();
    }
}
