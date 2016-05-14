import com.clear.html.ClockUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by chengweisen on 2015/1/21.
 */
public class MyTestJob3 implements Job {

    private final static ClockQaurtz quartzManager = ClockQaurtz.instance();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String testCron = ClockUtils.getTestCron();
        System.out.println(testCron);
        quartzManager.modifyJob("job1", testCron);
    }
}
