package org.fade.demo.quartzdemo.tutorialslesson7;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fade
 * @date 2021/09/14
 */
public class MyJobListener implements JobListener {

    private static final Logger logger = LoggerFactory.getLogger(MyJobListener.class);

    @Override
    public String getName() {
        return "MyJobListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        logger.info("JobDetail " + context.getJobDetail().getKey() + " is about to be executed");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        logger.info("JobDetail "
                + context.getJobDetail().getKey()
                + " is about to be executed, but a TriggerListener vetoed it's execution.");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        logger.info("JobDetail " + context.getJobDetail().getKey() + " has been executed");
    }

}
