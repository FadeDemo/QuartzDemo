package org.fade.demo.quartzdemo.tutorialslesson3;

import org.quartz.*;

/**
 * @author fade
 * @date 2021/09/08
 */
public class AutowiredJob implements Job {

    private String jobSays;

    private Float myFloatValue;

    public void setJobSays(String jobSays) {
        this.jobSays = jobSays;
    }

    public void setMyFloatValue(Float myFloatValue) {
        this.myFloatValue = myFloatValue;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();
        System.err.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);
    }

}
