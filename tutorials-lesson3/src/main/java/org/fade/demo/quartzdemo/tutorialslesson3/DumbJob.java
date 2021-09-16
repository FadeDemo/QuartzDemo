package org.fade.demo.quartzdemo.tutorialslesson3;

import org.quartz.*;

/**
 * @author fade
 * @date 2021/09/08
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class DumbJob implements Job {

    public DumbJob() {
    }

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException
    {
        JobKey key = context.getJobDetail().getKey();

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String jobSays = dataMap.getString("jobSays");
        float myFloatValue = dataMap.getFloat("myFloatValue");

        dataMap.replace("jobSays", jobSays + "#" + Thread.currentThread().getName());
        dataMap.replace("myFloatValue", myFloatValue + 1);

        System.err.println(Thread.currentThread().getName() + "-------" + "Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);
    }

}
