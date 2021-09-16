package org.fade.demo.quartzdemo.tutorialslesson7;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fade
 * @date 2021/09/14
 */
public class MyTriggerListener implements TriggerListener {

    private static final Logger logger = LoggerFactory.getLogger(MyTriggerListener.class);

    @Override
    public String getName() {
        return "MyTriggerListener";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        logger.info("Trigger " + trigger.getKey().toString()
                + " has fired, JobDetail " + trigger.getJobKey()
                + " is about to be executed");
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
//        return true;
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        logger.info("Trigger " + trigger.getKey().toString() + " has misfired");
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        logger.info("Trigger " + trigger.getKey().toString()
                + " has fired, JobDetail " + trigger.getJobKey()
                + " has been executed");
    }

}
