import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;


public class ClockQaurtz {

    private static SchedulerFactory sf = new StdSchedulerFactory();

    private final static String JOB_GROUP_NAME = "defaultGroup_";

    private final static String TRIGGER_GROUP_NAME = "defaultTrigger_";

    private static ClockQaurtz ins;

    private ClockQaurtz() {
        super();
    }

    public static ClockQaurtz instance() {
        if (ins == null) {
            ins = new ClockQaurtz();
        }
        return ins;
    }

    public void addJob(String jobName, Job job, String time) {
        Scheduler sched = null;
        try {
            sched = sf.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        JobDetail jobDetail = JobBuilder.newJob(job.getClass())
                .withIdentity(jobName, JOB_GROUP_NAME)
                .build();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, TRIGGER_GROUP_NAME)
                .withSchedule(CronScheduleBuilder.cronSchedule(time))
                .build();
        try {
            sched.scheduleJob(jobDetail, cronTrigger);
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


    public void removeJob(String jobName) {
        Scheduler sched = null;
        try {
            sched = sf.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        TriggerKey tk = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
        try {
            sched.pauseTrigger(tk);
            sched.unscheduleJob(tk);
            JobKey jk = new JobKey(jobName, JOB_GROUP_NAME);
            sched.deleteJob(jk);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void modifyJob(String jobName, String time) {
        Scheduler sched = null;
        try {
            sched = sf.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        JobKey jk = new JobKey(jobName, JOB_GROUP_NAME);
        JobDetail jobDetail = null;
        try {
            jobDetail = sched.getJobDetail(jk);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        removeJob(jobName);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, TRIGGER_GROUP_NAME)
                .withSchedule(CronScheduleBuilder.cronSchedule(time))
                .build();

        try {
            sched.scheduleJob(jobDetail, cronTrigger);
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
