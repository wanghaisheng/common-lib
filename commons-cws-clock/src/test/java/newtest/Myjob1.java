package newtest;

import org.joda.time.DateTime;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by chengweisen on 2015/1/29.
 */
public class Myjob1 implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        StdSchedulerFactory std = new StdSchedulerFactory();
        try {
            Scheduler scheduler = std.getScheduler();
            String group_name = "clear";
            String job_name = "job1";
            String triggerKey = "triggerKey1";
            JobDetail build = JobBuilder.newJob(Myjob1.class).withIdentity(job_name, group_name).build();
            CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule("0 0/1 * * * ?");
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey, group_name)
                    .withSchedule(cronSchedule).build();
            scheduler.scheduleJob(build, cronTrigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
