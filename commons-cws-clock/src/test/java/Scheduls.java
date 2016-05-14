import com.clear.html.ClockUtils;

import java.util.TimerTask;

public class Scheduls {

    private final static ClockQaurtz quartzManager = ClockQaurtz.instance();

    public static void main(String[] args) {
        quartzManager.addJob("job1", new MyTestJob(),  "5 0/1 * * * ?");
        quartzManager.addJob("job2", new MyTestJob3(), "1 0/1 * * * ?");
    }

    private static class ReSchedul extends TimerTask {

        private String jobName;

        private ReSchedul(String jobName) {
            this.jobName = jobName;
        }

        @Override
        public void run() {
            String testCron = ClockUtils.getTestCron();
            System.out.println(testCron);
            quartzManager.modifyJob(jobName, testCron);
        }
    }
}
