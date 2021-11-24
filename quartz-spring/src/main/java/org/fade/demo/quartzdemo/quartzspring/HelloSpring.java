package org.fade.demo.quartzdemo.quartzspring;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fade
 * @date 2021/11/24
 */
public class HelloSpring implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(HelloSpring.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOG.info("Hello Spring, I am Quartz!");
    }

    public void execute() {
        LOG.info("Hello Spring, I am Quartz!");
    }

}
