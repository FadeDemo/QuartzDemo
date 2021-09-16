package org.fade.demo.quartzdemo.tutorialslesson3;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fade
 * @date 2021/09/08
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class GetJobDataMapFromJobExecutionContext implements Job {

    private Logger logger = LoggerFactory.getLogger(GetJobDataMapFromJobExecutionContext.class);

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException
    {
        JobKey key = context.getJobDetail().getKey();

        JobDataMap dataMap = context.getMergedJobDataMap();

        String jobSays = dataMap.getString("jobSays");
        float myFloatValue = dataMap.getFloat("myFloatValue");
        dataMap.replace("jobSays", jobSays + "#" + Thread.currentThread().getName());
        dataMap.replace("myFloatValue", myFloatValue + 1);

        logger.info("Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);
    }

}
