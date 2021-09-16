package org.fade.demo.quartzdemo.concurrentannotationtest;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author fade
 * @date 2021/09/13
 */
//@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class StatefulDumbJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(StatefulDumbJob.class);

    public static final String NUM_EXECUTIONS = "NumExecutions";

    public static final String EXECUTION_DELAY = "ExecutionDelay";

    public StatefulDumbJob() {
    }

    @Override
    public void execute(JobExecutionContext context) {
        logger.info("---" + context.getJobDetail().getKey()
                + " executing.[" + new Date() + "]");

        JobDataMap map = context.getJobDetail().getJobDataMap();

        int executeCount = 0;
        if (map.containsKey(NUM_EXECUTIONS)) {
            executeCount = map.getInt(NUM_EXECUTIONS);
        }

        executeCount++;

        map.put(NUM_EXECUTIONS, executeCount);

        long delay = 5000L;
        if (map.containsKey(EXECUTION_DELAY)) {
            delay = map.getLong(EXECUTION_DELAY);
        }

        try {
            Thread.sleep(delay);
        } catch (Exception ignore) {
        }

        logger.info("  -" + context.getJobDetail().getKey()
                + " complete (" + executeCount + ").");

    }

}
