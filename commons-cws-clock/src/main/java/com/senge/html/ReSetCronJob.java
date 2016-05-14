package com.clear.html;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chengweisen on 2015/1/21.
 */
public class ReSetCronJob implements Job {

    private final static Logger logger = LoggerFactory.getLogger(ReSetCronJob.class);

    private final static ClockQaurtz quartzManager = ClockQaurtz.instance();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String testCron = ClockUtils.getCron();
        logger.info("下次执行表达式为 ====>> " + testCron);
        quartzManager.modifyJob("job1", testCron);
    }
}
