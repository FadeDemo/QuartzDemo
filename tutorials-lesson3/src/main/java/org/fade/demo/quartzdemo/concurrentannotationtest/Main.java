package org.fade.demo.quartzdemo.concurrentannotationtest;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.nextGivenSecondDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author fade
 * @date 2021/09/13
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SchedulerException {
        log.info("------- Initializing -------------------");

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- Initialization Complete -----------");

        log.info("------- Scheduling Jobs -----------");

        // jobs can be scheduled before start() has been called

        // get a "nice round" time a few seconds in the future...
        Date startTime = nextGivenSecondDate(null, 15);

        // statefulJob1 will run every three seconds
        // (but it will delay for ten seconds)
        JobDetail job = newJob(StatefulDumbJob.class)
                .withIdentity("statefulJob1", "group1")
                .usingJobData(StatefulDumbJob.EXECUTION_DELAY, 10000L)
                .build();

        SimpleTrigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(startTime)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(3)
                        .repeatForever())
                .build();

        Date ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() +
                " will run at: " + ft +
                " and repeat: " + trigger.getRepeatCount() +
                " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

        log.info("------- Starting Scheduler ----------------");

        // jobs don't start firing until start() has been called...
        sched.start();

        log.info("------- Started Scheduler -----------------");

        try {
            // sleep for ten minutes for triggers to fire....
            Thread.sleep(600L * 1000L);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("------- Shutting Down ---------------------");

        sched.shutdown(true);

        log.info("------- Shutdown Complete -----------------");

        SchedulerMetaData metaData = sched.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

}
