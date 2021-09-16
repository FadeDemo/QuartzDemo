package org.fade.demo.quartzdemo.persistannotationtest;

import org.quartz.*;

import java.util.Date;

/**
 * @author fade
 * @date 2021/09/13
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class BadJob implements Job {

    public BadJob() {
    }

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        int denominator = dataMap.getInt("denominator");
        System.err.println(Thread.currentThread().getName() + "---" + jobKey + " executing at " + new Date() + " with denominator " + denominator);

        denominator++;
        dataMap.put("denominator", denominator);
    }

}
