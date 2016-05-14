import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyTestJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("MyTestJob.execute" + DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
    }
}
